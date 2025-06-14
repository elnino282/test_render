package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.*;
import org.example.AgentManagementBE.Repository.DebtReportRepository;
import org.example.AgentManagementBE.Repository.SalesReportRepository;
import org.example.AgentManagementBE.Repository.ExportReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;

@Service
public class ExportReceiptService {
    private final ExportReceiptRepository exportReceiptRepository;
    private final AgentService agentService;
    private final DebtReportRepository debtReportRepository;
    private final SalesReportRepository salesReportRepository;
    private final Calendar calendar;

    @Autowired
    public ExportReceiptService(ExportReceiptRepository exportReceiptRepository, 
                              AgentService agentService, 
                              DebtReportRepository debtReportRepository, 
                              SalesReportRepository salesReportRepository) {
        this.exportReceiptRepository = exportReceiptRepository;
        this.agentService = agentService;
        this.debtReportRepository = debtReportRepository;
        this.salesReportRepository = salesReportRepository;
        this.calendar = Calendar.getInstance();
    }

    public ExportReceipt getExportReceiptById(int exportReceiptId) {
        try {
            return exportReceiptRepository.getExportReceiptById(exportReceiptId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving export receipt: " + e.getMessage());
        }
    }

    public ExportReceipt getAllExportReceiptByExportDate(String dateReceipt) {
        try {
            return exportReceiptRepository.getAllExportReceiptByDateReceipt(dateReceipt);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving export receipt by date: " + e.getMessage());
        }
    }

    public int getQuantityExportByMonthAndYearOfExportDate(int month, int year) {
        try {
            return exportReceiptRepository.getQuantityExportByMonthAndYearOfDateReceipt(month, year);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving export quantity: " + e.getMessage());
        }
    }
    
    public int createExportReceipt(ExportReceipt newExportReceipt) {
        try {
            System.out.println("\n=== Creating ExportReceipt ===");
            System.out.println("Agent ID: " + newExportReceipt.getAgentID().getAgentID());
            System.out.println("Date: " + newExportReceipt.getDateReceipt());
            System.out.println("Total Money: " + newExportReceipt.getTotalMoney());
            System.out.println("Payment Amount: " + newExportReceipt.getPaymentAmount());

            // Validate agent exists
            if (newExportReceipt.getAgentID() == null) {
                throw new IllegalArgumentException("Agent ID is required");
            }

            // Validate required fields
            if (newExportReceipt.getDateReceipt() == null) {
                throw new IllegalArgumentException("Date receipt is required");
            }

            if (newExportReceipt.getPaymentAmount() < 0) {
                throw new IllegalArgumentException("Payment amount cannot be negative");
            }

            if (newExportReceipt.getTotalMoney() < 0) {
                throw new IllegalArgumentException("Total money cannot be negative");
            }

            // Calculate remainAmount
            int remainAmount = newExportReceipt.getTotalMoney() - newExportReceipt.getPaymentAmount();
            System.out.println("Calculated Remain Amount: " + remainAmount);
            
            // Set remainAmount to 0 if it's negative (meaning payment amount exceeds total money)
            if (remainAmount < 0) {
                System.out.println("Warning: Payment amount exceeds total money. Setting remain amount to 0.");
                remainAmount = 0;
            }
            newExportReceipt.setRemainAmount(remainAmount);
            
            // Check if updating debt book was successful
            ResponseEntity<?> debtUpdateResponse = agentService.updateDebtBook(
                remainAmount, 
                newExportReceipt.getAgentID().getAgentID()
            );
            
            if (!debtUpdateResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to update debt book");
            }

            calendar.setTime(newExportReceipt.getDateReceipt());
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            // Update arisenDebt in debt report
            updateArisenDebt(month, year, remainAmount, newExportReceipt.getAgentID());
            
            ExportReceipt savedReceipt = exportReceiptRepository.save(newExportReceipt);
            System.out.println("Successfully saved ExportReceipt with ID: " + savedReceipt.getExportReceiptID());
            System.out.println("=== ExportReceipt Creation Completed ===\n");
            return savedReceipt.getExportReceiptID();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Error creating export receipt: " + e.getMessage());
            throw new RuntimeException("Error creating export receipt: " + e.getMessage());
        }
    }   

    private void updateArisenDebt(int month, int year, int remainAmount, Agent agent) {
        try {
            System.out.println("\n=== Updating Arisen Debt ===");
            System.out.println("Month: " + month + ", Year: " + year);
            System.out.println("Agent ID: " + agent.getAgentID());
            System.out.println("Remain Amount to add: " + remainAmount);

            DebtReport existingDebtReport = debtReportRepository.getDebtReportByAgent(month, year, agent.getAgentID());
            if (existingDebtReport != null) {
                int oldArisenDebt = existingDebtReport.getArisenDebt();
                int newArisenDebt = oldArisenDebt + remainAmount;
                existingDebtReport.setArisenDebt(newArisenDebt);
                existingDebtReport.setLastDebt(existingDebtReport.getFirstDebt() + newArisenDebt);
                debtReportRepository.save(existingDebtReport);
                System.out.println("Updated Arisen Debt: " + newArisenDebt);
            } else {
                DebtReport newDebtReport = new DebtReport(
                    new DebtReportID(month, year, agent), 
                    0, // firstDebt
                    remainAmount, // arisenDebt
                    remainAmount // lastDebt
                );
                debtReportRepository.save(newDebtReport);
                System.out.println("Created new DebtReport with Arisen Debt: " + remainAmount);
            }
            System.out.println("=== Arisen Debt Update Completed ===\n");
        } catch (Exception e) {
            System.out.println("Error updating arisen debt: " + e.getMessage());
            throw new RuntimeException("Error updating arisen debt: " + e.getMessage());
        }
    }

    public boolean updateTotalMoney(int exportReceiptId, int newTotalMoney) {
        try {
            ExportReceipt exportReceipt = exportReceiptRepository.getExportReceiptById(exportReceiptId);
            if (exportReceipt != null) {
                int oldTotalMoney = exportReceipt.getTotalMoney();
                int oldRemainAmount = oldTotalMoney - exportReceipt.getPaymentAmount();

                exportReceipt.setTotalMoney(newTotalMoney);
                exportReceiptRepository.save(exportReceipt);

                // Calculate the new remain amount and the change in remain amount
                int newRemainAmount = newTotalMoney - exportReceipt.getPaymentAmount();
                int remainAmountChange = newRemainAmount - oldRemainAmount;

                // Update the agent's debt money
                Agent agent = exportReceipt.getAgentID(); // Assuming Agent object is fully loaded or can be fetched
                if (agent != null) {
                    // Increase agent's debt by the change in remain amount
                    agent.setDebtMoney(agent.getDebtMoney() + remainAmountChange);
                    // Call updateDebtBook from AgentService to update the agent's debt in the database
                    // updateDebtBook handles saving the agent and related logic
                    agentService.updateDebtBook(remainAmountChange, agent.getAgentID());
                } else {
                    System.err.println("Error: Agent not found for export receipt ID: " + exportReceiptId);
                }

                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error updating total money and agent debt for export receipt: " + e.getMessage());
        }
    }
}
