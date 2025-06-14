package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.ImportReceipt;
import org.example.AgentManagementBE.Model.ImportDetail;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Model.Unit;
import org.example.AgentManagementBE.Repository.ImportReceiptRepository;
import org.example.AgentManagementBE.Repository.ImportDetailRepository;
import org.example.AgentManagementBE.Repository.ProductRepository;
import org.example.AgentManagementBE.Repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.Date;

@Service
public class ImportReceiptService {
    private static ImportReceiptRepository importReceiptRepository;
    private static ImportDetailRepository importDetailRepository;
    private static ProductRepository productRepository;
    private static UnitRepository unitRepository;

    @Autowired
    public ImportReceiptService(ImportReceiptRepository importReceiptRepository, 
                              ImportDetailRepository importDetailRepository,
                              ProductRepository productRepository,
                              UnitRepository unitRepository) {
        this.importReceiptRepository = importReceiptRepository;
        this.importDetailRepository = importDetailRepository;
        this.productRepository = productRepository;
        this.unitRepository = unitRepository;
    }

    public static ImportReceipt getImportReceiptById(int importReceiptID) {
        return importReceiptRepository.getImportReceiptById(importReceiptID);
    }

    public static List<ImportDetail> getImportDetailsByImportReceiptID(int importReceiptID) {
        try {
            return importDetailRepository.getImportDetailByImportReceiptID(importReceiptID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<ImportReceipt> getAllImportReceiptByImportDate(String dateReceipt) {
        try {
            Date date = Date.valueOf(dateReceipt);
            return importReceiptRepository.getAllImportReceiptByDateReceipt(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImportReceipt createImportReceipt(ImportReceipt newImportReceipt) {
        try {
            ImportReceipt savedImportReceipt = importReceiptRepository.save(newImportReceipt);
            return savedImportReceipt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Product getProductById(int productID) {
        try {
            return productRepository.getProductById(productID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Unit getUnitById(int unitID) {
        try {
            return unitRepository.findById(unitID).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateTotalPrice(int importReceiptID, int totalPrice) {
        try {
            ImportReceipt importReceipt = importReceiptRepository.getImportReceiptById(importReceiptID);
            if (importReceipt != null) {
                importReceipt.setTotalPrice(totalPrice);
                importReceiptRepository.save(importReceipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createImportDetail(ImportDetail newImportDetail) {
        try {
            importDetailRepository.save(newImportDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ImportReceipt updateImportReceipt(ImportReceipt importReceipt) {
        try {
            return importReceiptRepository.save(importReceipt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}