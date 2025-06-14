package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.ExportReceipt;
import org.example.AgentManagementBE.Model.ExportDetail;
import org.example.AgentManagementBE.Model.Agent;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Service.ExportReceiptService;
import org.example.AgentManagementBE.Service.ExportDetailService;
import org.example.AgentManagementBE.Service.ProductService;
import org.example.AgentManagementBE.Repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

@RestController
@RequestMapping("/exportReceipt")
public class ExportReceiptController {
    private final ExportReceiptService exportReceiptService;
    private final ExportDetailService exportDetailService;
    private final ProductService productService;
    private final AgentRepository agentRepository;

    @Autowired
    public ExportReceiptController(ExportReceiptService exportReceiptService, 
                                 ExportDetailService exportDetailService,
                                 ProductService productService,
                                 AgentRepository agentRepository) {
        this.exportReceiptService = exportReceiptService;
        this.exportDetailService = exportDetailService;
        this.productService = productService;
        this.agentRepository = agentRepository;
    }

    @GetMapping("/getExportReceiptById")
    public ResponseEntity<Map<String, Object>> getExportReceiptById(@RequestParam int exportReceiptId) {
        Map<String, Object> response = new HashMap<>();
        try {
            ExportReceipt exportReceipt = exportReceiptService.getExportReceiptById(exportReceiptId);
            if (exportReceipt != null) {
                // Get export details for this receipt
                List<ExportDetail> exportDetails = exportDetailService.getExportDetailByExportReceiptID(exportReceiptId);
                
                if (exportDetails != null && !exportDetails.isEmpty()) {
                    // Get the first export detail (assuming one product per receipt for this response format)
                    ExportDetail exportDetail = exportDetails.get(0);
                    
                    // Create flattened response data
                    Map<String, Object> data = new HashMap<>();
                    data.put("exportReceiptID", exportReceipt.getExportReceiptID());
                    data.put("dateReceipt", exportReceipt.getDateReceipt().toString());
                    data.put("agentID", exportReceipt.getAgentID().getAgentID());
                    data.put("agentName", exportReceipt.getAgentID().getAgentName());
                    data.put("productID", exportDetail.getProductID().getProductID());
                    data.put("productName", exportDetail.getProductID().getProductName());
                    data.put("unitID", exportDetail.getProductID().getUnit().getUnitID());
                    data.put("unitName", exportDetail.getProductID().getUnit().getUnitName());
                    data.put("quantityExport", exportDetail.getQuantityExport());
                    data.put("exportPrice", exportDetail.getExportPrice());
                    data.put("intoMoney", exportDetail.getIntoMoney());
                    data.put("totalPrice", exportReceipt.getTotalMoney());
                    data.put("paymentAmount", exportReceipt.getPaymentAmount());
                    data.put("remainAmount", exportReceipt.getTotalMoney() - exportReceipt.getPaymentAmount());

                    response.put("code", 200);
                    response.put("status", "success");
                    response.put("message", "Lấy phiếu xuất hàng thành công");
                    response.put("data", data);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("code", 404);
                    response.put("status", "error");
                    response.put("message", "Không tìm thấy chi tiết phiếu xuất hàng");
                    response.put("data", null);
                    return ResponseEntity.status(404).body(response);
                }
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy phiếu xuất hàng");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi tìm phiếu xuất hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getExportReceiptByDate")
    public ResponseEntity<Map<String, Object>> getExportReceiptsByDate(@RequestParam String dateReceipt) {
        Map<String, Object> response = new HashMap<>();
        try {
            ExportReceipt exportReceipt = exportReceiptService.getAllExportReceiptByExportDate(dateReceipt);
            if (exportReceipt != null) {
                // Get export details for this receipt
                List<ExportDetail> exportDetails = exportDetailService.getExportDetailByExportReceiptID(exportReceipt.getExportReceiptID());
                
                if (exportDetails != null && !exportDetails.isEmpty()) {
                    // Get the first export detail (assuming one product per receipt for this response format)
                    ExportDetail exportDetail = exportDetails.get(0);
                    
                    // Create flattened response data
                    Map<String, Object> data = new HashMap<>();
                    data.put("exportReceiptID", exportReceipt.getExportReceiptID());
                    data.put("dateReceipt", exportReceipt.getDateReceipt().toString());
                    data.put("agentID", exportReceipt.getAgentID().getAgentID());
                    data.put("agentName", exportReceipt.getAgentID().getAgentName());
                    data.put("productID", exportDetail.getProductID().getProductID());
                    data.put("productName", exportDetail.getProductID().getProductName());
                    data.put("unitID", exportDetail.getProductID().getUnit().getUnitID());
                    data.put("unitName", exportDetail.getProductID().getUnit().getUnitName());
                    data.put("quantityExport", exportDetail.getQuantityExport());
                    data.put("exportPrice", exportDetail.getExportPrice());
                    data.put("intoMoney", exportDetail.getIntoMoney());
                    data.put("totalPrice", exportReceipt.getTotalMoney());
                    data.put("paymentAmount", exportReceipt.getPaymentAmount());
                    data.put("remainAmount", exportReceipt.getTotalMoney() - exportReceipt.getPaymentAmount());

                    response.put("code", 200);
                    response.put("status", "success");
                    response.put("message", "Lấy phiếu xuất hàng thành công");
                    response.put("data", data);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("code", 404);
                    response.put("status", "error");
                    response.put("message", "Không tìm thấy chi tiết phiếu xuất hàng");
                    response.put("data", null);
                    return ResponseEntity.status(404).body(response);
                }
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy phiếu xuất hàng theo ngày");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi tìm phiếu xuất hàng theo ngày: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addExportReceipt")
    public ResponseEntity<Map<String, Object>> createExportReceipt(@RequestBody List<Map<String, Object>> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (requestBody == null || requestBody.isEmpty()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Dữ liệu phiếu xuất hàng không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Get first (and only) item from the array
            Map<String, Object> requestData = requestBody.get(0);
            
            // Extract agent information
            Map<String, Object> agentData = (Map<String, Object>) requestData.get("agentID");
            if (agentData == null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Thiếu thông tin agentID");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            Integer agentID = (Integer) agentData.get("agentID");
            Agent agent = agentRepository.getAgentById(agentID);
            if (agent == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đại lý với ID: " + agentID);
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Extract product information
            Map<String, Object> productData = (Map<String, Object>) requestData.get("productID");
            if (productData == null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Thiếu thông tin productID");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            Integer productID = (Integer) productData.get("productID");
            Product product = productService.getProductById(productID);
            if (product == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy sản phẩm với ID: " + productID);
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Extract other fields
            Integer totalMoney = (Integer) requestData.get("totalMoney");
            Integer paymentAmount = (Integer) requestData.get("paymentAmount");
            Integer quantityExport = (Integer) requestData.get("quantityExport");
            Integer exportPrice = (Integer) requestData.get("exportPrice");

            if (totalMoney == null || paymentAmount == null || quantityExport == null || exportPrice == null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Thiếu thông tin bắt buộc");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Create ExportReceipt
            ExportReceipt newExportReceipt = new ExportReceipt();
            newExportReceipt.setAgentID(agent);
            newExportReceipt.setDateReceipt(new Date(System.currentTimeMillis())); // Current date
            newExportReceipt.setTotalMoney(totalMoney);
            newExportReceipt.setPaymentAmount(paymentAmount);
            
            int exportReceiptId = exportReceiptService.createExportReceipt(newExportReceipt);
            if (exportReceiptId == -1) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể tạo phiếu xuất hàng");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Get the saved ExportReceipt
            ExportReceipt savedExportReceipt = exportReceiptService.getExportReceiptById(exportReceiptId);

            // Create ExportDetail
            ExportDetail newExportDetail = new ExportDetail();
            newExportDetail.setExportReceiptID(savedExportReceipt);
            newExportDetail.setProductID(product);
            newExportDetail.setQuantityExport(quantityExport);
            newExportDetail.setExportPrice(exportPrice);
            newExportDetail.setIntoMoney(exportPrice * quantityExport);

            ExportDetail savedExportDetail = exportDetailService.createExportDetail(newExportDetail);
            if (savedExportDetail == null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể tạo chi tiết phiếu xuất hàng");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Update product inventory
            boolean inventoryUpdated = productService.downInventoryQuantity(product, quantityExport);
            if (!inventoryUpdated) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không đủ hàng trong kho");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Update total money in export receipt
            exportReceiptService.updateTotalMoney(exportReceiptId, savedExportDetail.getIntoMoney());
            
            // Get updated ExportReceipt
            ExportReceipt updatedExportReceipt = exportReceiptService.getExportReceiptById(exportReceiptId);

            // Create response data
            Map<String, Object> data = new HashMap<>();
            data.put("exportReceiptID", updatedExportReceipt.getExportReceiptID());
            data.put("dateReceipt", updatedExportReceipt.getDateReceipt().toString());
            data.put("productID", product.getProductID());
            data.put("productName", product.getProductName());
            data.put("unitID", product.getUnit().getUnitID());
            data.put("unitName", product.getUnit().getUnitName());
            data.put("agentId", agent.getAgentID());
            data.put("agentName", agent.getAgentName());
            data.put("quantityExport", savedExportDetail.getQuantityExport());
            data.put("exportPrice", savedExportDetail.getExportPrice());
            data.put("intoMoney", savedExportDetail.getIntoMoney());
            data.put("totalPrice", updatedExportReceipt.getTotalMoney());
            data.put("paymentAmount", updatedExportReceipt.getPaymentAmount());
            data.put("remainAmount", updatedExportReceipt.getTotalMoney() - updatedExportReceipt.getPaymentAmount());

            response.put("code", 201);
            response.put("status", "CREATED");
            response.put("message", "Tạo phiếu xuất hàng thành công");
            response.put("data", data);
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi tạo phiếu xuất hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
