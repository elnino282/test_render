package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.ImportDetail;
import org.example.AgentManagementBE.Model.ImportReceipt;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Service.ImportDetailService;
import org.example.AgentManagementBE.Service.ImportReceiptService;
import org.example.AgentManagementBE.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/importDetail")
public class ImportDetailController {
    @Autowired
    private final ImportDetailService importDetailService;

    @Autowired
    private final ProductService productService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ImportDetailController(ImportDetailService importDetailService, ProductService productService) {
        this.importDetailService = importDetailService;
        this.productService = productService;
    }

    @GetMapping("/importDetailbyImportReceiptID")
    public ResponseEntity<Map<String, Object>> getImportDetailByImportReceiptID(@RequestParam int importReceiptID) {
        Map<String, Object> response = ImportDetailService.getImportDetailByImportReceiptID(importReceiptID);
        return ResponseEntity.status((Integer) response.get("code")).body(response);
    }

    @GetMapping("/importDetailbyProductID")
    public ResponseEntity<Map<String, Object>> getImportDetailByProductID(@RequestParam int productID) {
        Map<String, Object> response = ImportDetailService.getImportDetailByProductID(productID);
        return ResponseEntity.status((Integer) response.get("code")).body(response);
    }

    @GetMapping("/importDetailbyImportReceiptIDandProductID")
    public ResponseEntity<Map<String, Object>> getImportDetailByImportReceiptIDAndProductID(
            @RequestParam int importReceiptID, 
            @RequestParam int productID) {
        Map<String, Object> response = ImportDetailService.getImportDetailByImportReceiptIDAndProductID(importReceiptID, productID);
        return ResponseEntity.status((Integer) response.get("code")).body(response);
    }

    @PostMapping("/addImportDetail")
    public ResponseEntity<Map<String, Object>> createImportDetail(@RequestBody Object requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Handle both array and single object
            List<Map<String, Object>> importDetailDataList = new ArrayList<>();
            
            if (requestBody instanceof List) {
                // If array is sent, get the first element
                List<?> bodyList = (List<?>) requestBody;
                if (!bodyList.isEmpty() && bodyList.get(0) instanceof Map) {
                    importDetailDataList.add((Map<String, Object>) bodyList.get(0));
                } else {
                    response.put("code", 400);
                    response.put("status", "BAD_REQUEST");
                    response.put("message", "Dữ liệu không hợp lệ");
                    response.put("data", null);
                    return ResponseEntity.status(400).body(response);
                }
            } else if (requestBody instanceof Map) {
                importDetailDataList.add((Map<String, Object>) requestBody);
            } else {
                response.put("code", 400);
                response.put("status", "BAD_REQUEST");
                response.put("message", "Dữ liệu không hợp lệ");
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }
            
            // Process the import detail data
            Map<String, Object> importDetailData = importDetailDataList.get(0);
            
            // Extract IDs from nested objects
            Map<String, Object> importReceiptData = (Map<String, Object>) importDetailData.get("importReceiptID");
            Map<String, Object> productData = (Map<String, Object>) importDetailData.get("productID");
            
            if (importReceiptData == null || productData == null) {
                response.put("code", 400);
                response.put("status", "BAD_REQUEST");
                response.put("message", "Thiếu thông tin importReceiptID hoặc productID");
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }
            
            int importReceiptID = (Integer) importReceiptData.get("importReceiptID");
            int productID = (Integer) productData.get("productID");
            int quantityImport = (Integer) importDetailData.get("quantityImport");
            
            // Get import price from request or use default from product
            Integer requestImportPrice = (Integer) importDetailData.get("importPrice");
            
            // Fetch complete product information
            Product p = productService.getProductById(productID);
            if (p == null) {
                response.put("code", 400);
                response.put("status", "BAD_REQUEST");
                response.put("message", "Không tìm thấy sản phẩm với ID: " + productID);
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }
            
            // Fetch complete import receipt information
            ImportReceipt importReceipt = ImportReceiptService.getImportReceiptById(importReceiptID);
            if (importReceipt == null) {
                response.put("code", 400);
                response.put("status", "BAD_REQUEST");
                response.put("message", "Không tìm thấy phiếu nhập hàng với ID: " + importReceiptID);
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }

            // Set current date if not set
            if (importReceipt.getDateReceipt() == null) {
                importReceipt.setDateReceipt(new java.sql.Date(System.currentTimeMillis()));
                ImportReceiptService.updateImportReceipt(importReceipt);
            }
            
            // Create new ImportDetail object
            ImportDetail newImportDetail = new ImportDetail();
            newImportDetail.setImportReceiptID(importReceipt);
            newImportDetail.setProductID(p);
            newImportDetail.setQuantityImport(quantityImport);
            
            // Use import price from request if provided, otherwise use product's import price
            int finalImportPrice = (requestImportPrice != null) ? requestImportPrice : p.getImportPrice();
            newImportDetail.setImportPrice(finalImportPrice);
            newImportDetail.setIntoMoney(finalImportPrice * quantityImport);
            newImportDetail.setUnit(p.getUnit().getUnitID());
            
            // Create import detail and get response
            Map<String, Object> createResponse = ImportDetailService.createImportDetail(newImportDetail);
            
            if ((Integer) createResponse.get("code") == 201) {
                // Update total price of import receipt
                int newTotalPrice = importReceipt.getTotalPrice() + newImportDetail.getIntoMoney();
                ImportReceiptService.updateTotalPrice(importReceipt.getImportReceiptID(), newTotalPrice);
                
                return ResponseEntity.status(201).body(createResponse);
            } else {
                return ResponseEntity.status((Integer) createResponse.get("code")).body(createResponse);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi tạo chi tiết nhập hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}