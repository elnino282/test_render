package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.ImportReceipt;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Model.Unit;
import org.example.AgentManagementBE.Service.ImportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/importReceipt")
public class ImportReceiptController {
    @Autowired
    private final ImportReceiptService importReceiptService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ImportReceiptController(ImportReceiptService importReceiptService) {
        this.importReceiptService = importReceiptService;
    }

    @GetMapping("/importReceiptbyID")
    public ResponseEntity<Map<String, Object>> getImportReceiptById(@RequestParam int importReceiptID) {
        Map<String, Object> response = new HashMap<>();
        try {
            ImportReceipt importReceipt = ImportReceiptService.getImportReceiptById(importReceiptID);
            
            if (importReceipt != null) {
                // Get import details for this receipt
                List<org.example.AgentManagementBE.Model.ImportDetail> importDetails = ImportReceiptService.getImportDetailsByImportReceiptID(importReceiptID);
                
                if (importDetails != null && !importDetails.isEmpty()) {
                    // Get the first import detail (assuming one product per receipt for this response format)
                    org.example.AgentManagementBE.Model.ImportDetail importDetail = importDetails.get(0);
                    
                    // Create response data in the desired format
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("importReceiptID", importReceipt.getImportReceiptID());
                    
                    // Format dateReceipt to yyyy-MM-dd format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(importReceipt.getDateReceipt());
                    data.put("dateReceipt", formattedDate);
                    
                    // Product information
                    data.put("productID", importDetail.getProductID().getProductID());
                    data.put("productName", importDetail.getProductID().getProductName());
                    
                    // Unit information
                    data.put("unitID", importDetail.getProductID().getUnit().getUnitID());
                    data.put("unitName", importDetail.getProductID().getUnit().getUnitName());
                    
                    // Import detail information
                    data.put("quantityImport", importDetail.getQuantityImport());
                    data.put("importPrice", importDetail.getImportPrice());
                    data.put("intoMoney", importDetail.getIntoMoney());
                    data.put("totalPrice", importReceipt.getTotalPrice());
                    
                    response.put("code", 200);
                    response.put("status", "SUCCESS");
                    response.put("message", "Lấy phiếu nhập hàng thành công");
                    response.put("data", data);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("code", 404);
                    response.put("status", "NOT_FOUND");
                    response.put("message", "Không tìm thấy chi tiết nhập hàng cho phiếu nhập ID: " + importReceiptID);
                    response.put("data", null);
                    return ResponseEntity.status(404).body(response);
                }
            } else {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy phiếu nhập hàng với ID: " + importReceiptID);
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi lấy phiếu nhập hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/importReceiptbyImportDate")
    public ResponseEntity<Map<String, Object>> getAllImportReceiptByImportDate(@RequestParam String dateReceipt) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ImportReceipt> importReceipts = ImportReceiptService.getAllImportReceiptByImportDate(dateReceipt);
            
            if (importReceipts != null && !importReceipts.isEmpty()) {
                List<Map<String, Object>> dataList = new ArrayList<>();
                
                for (ImportReceipt importReceipt : importReceipts) {
                    // Get import details for this receipt
                    List<org.example.AgentManagementBE.Model.ImportDetail> importDetails = ImportReceiptService.getImportDetailsByImportReceiptID(importReceipt.getImportReceiptID());
                    
                    if (importDetails != null && !importDetails.isEmpty()) {
                        // Get the first import detail (assuming one product per receipt for this response format)
                        org.example.AgentManagementBE.Model.ImportDetail importDetail = importDetails.get(0);
                        
                        // Create response data in the desired format
                        Map<String, Object> data = new LinkedHashMap<>();
                        data.put("importReceiptID", importReceipt.getImportReceiptID());
                        
                        // Format dateReceipt to yyyy-MM-dd format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = dateFormat.format(importReceipt.getDateReceipt());
                        data.put("dateReceipt", formattedDate);
                        
                        // Product information
                        data.put("productID", importDetail.getProductID().getProductID());
                        data.put("productName", importDetail.getProductID().getProductName());
                        
                        // Unit information
                        data.put("unitID", importDetail.getProductID().getUnit().getUnitID());
                        data.put("unitName", importDetail.getProductID().getUnit().getUnitName());
                        
                        // Import detail information
                        data.put("quantityImport", importDetail.getQuantityImport());
                        data.put("importPrice", importDetail.getImportPrice());
                        data.put("intoMoney", importDetail.getIntoMoney());
                        data.put("totalPrice", importReceipt.getTotalPrice());
                        
                        dataList.add(data);
                    }
                }
                
                if (!dataList.isEmpty()) {
                    response.put("code", 200);
                    response.put("status", "SUCCESS");
                    response.put("message", "Lấy danh sách phiếu nhập hàng theo ngày thành công");
                    response.put("data", dataList);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("code", 404);
                    response.put("status", "NOT_FOUND");
                    response.put("message", "Không tìm thấy chi tiết nhập hàng cho ngày: " + dateReceipt);
                    response.put("data", null);
                    return ResponseEntity.status(404).body(response);
                }
            } else {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy phiếu nhập hàng cho ngày: " + dateReceipt);
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi lấy phiếu nhập hàng theo ngày: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addImportReceipt")
    public ResponseEntity<Map<String, Object>> createImportReceipt(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Extract data from request
            String dateReceiptStr = (String) requestData.get("dateReceipt");
            int productID = (Integer) requestData.get("productID");
            int unitID = (Integer) requestData.get("unitID");
            int quantityImport = (Integer) requestData.get("quantityImport");
            int importPrice = (Integer) requestData.get("importPrice");

            // Validate input
            if (dateReceiptStr == null || productID <= 0 || unitID <= 0 || quantityImport <= 0 || importPrice <= 0) {
                response.put("code", 400);
                response.put("status", "BAD_REQUEST");
                response.put("message", "Dữ liệu đầu vào không hợp lệ");
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }

            // Get product and unit information
            Product product = ImportReceiptService.getProductById(productID);
            Unit unit = ImportReceiptService.getUnitById(unitID);

            if (product == null) {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy sản phẩm với ID: " + productID);
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            if (unit == null) {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy đơn vị với ID: " + unitID);
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            // Create ImportReceipt
            ImportReceipt newImportReceipt = new ImportReceipt();
            Date dateReceipt = Date.valueOf(dateReceiptStr);
            newImportReceipt.setDateReceipt(dateReceipt);
            
            // Calculate intoMoney and totalPrice
            int intoMoney = quantityImport * importPrice;
            newImportReceipt.setTotalPrice(intoMoney);

            ImportReceipt savedImportReceipt = ImportReceiptService.createImportReceipt(newImportReceipt);

            if (savedImportReceipt != null) {
                // Create ImportDetail for this ImportReceipt
                org.example.AgentManagementBE.Model.ImportDetail newImportDetail = new org.example.AgentManagementBE.Model.ImportDetail();
                newImportDetail.setImportReceiptID(savedImportReceipt);
                newImportDetail.setProductID(product);
                newImportDetail.setQuantityImport(quantityImport);
                newImportDetail.setImportPrice(importPrice);
                newImportDetail.setIntoMoney(intoMoney);
                newImportDetail.setUnit(unit.getUnitID());

                // Save ImportDetail
                boolean importDetailSaved = ImportReceiptService.createImportDetail(newImportDetail);
                
                if (importDetailSaved) {
                    // Create response data in the desired format
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("importReceiptID", savedImportReceipt.getImportReceiptID());
                    data.put("dateReceipt", dateReceiptStr);
                    data.put("productID", product.getProductID());
                    data.put("productName", product.getProductName());
                    data.put("unitID", unit.getUnitID());
                    data.put("unitName", unit.getUnitName());
                    data.put("quantityImport", quantityImport);
                    data.put("importPrice", importPrice);
                    data.put("intoMoney", intoMoney);
                    data.put("totalPrice", savedImportReceipt.getTotalPrice());

                    response.put("code", 201);
                    response.put("status", "CREATED");
                    response.put("message", "Tạo phiếu nhập hàng thành công");
                    response.put("data", data);
                    return ResponseEntity.status(201).body(response);
                } else {
                    response.put("code", 400);
                    response.put("status", "BAD_REQUEST");
                    response.put("message", "Tạo chi tiết phiếu nhập hàng thất bại");
                    response.put("data", null);
                    return ResponseEntity.status(400).body(response);
                }
            } else {
                response.put("code", 400);
                response.put("status", "BAD_REQUEST");
                response.put("message", "Tạo phiếu nhập hàng thất bại");
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi tạo phiếu nhập hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}