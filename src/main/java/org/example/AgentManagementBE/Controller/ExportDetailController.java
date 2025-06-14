package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.ExportDetail;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Model.ExportReceipt;
import org.example.AgentManagementBE.Service.ExportDetailService;
import org.example.AgentManagementBE.Service.ProductService;
import org.example.AgentManagementBE.Service.ExportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exportDetail")
public class ExportDetailController {
    @Autowired
    private final ExportDetailService exportDetailService;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final ExportReceiptService exportReceiptService;

    public ExportDetailController(ExportDetailService exportDetailService, ProductService productService, ExportReceiptService exportReceiptService) {
        this.exportDetailService = exportDetailService;
        this.productService = productService;
        this.exportReceiptService = exportReceiptService;
    }

    @GetMapping("/getExportDetailByReceiptId")
    public ResponseEntity<Map<String, Object>> getExportDetailsByReceiptId(@RequestParam int exportReceiptId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ExportDetail> exportDetailList = exportDetailService.getExportDetailByExportReceiptID(exportReceiptId);
            if (!exportDetailList.isEmpty()) {
                List<Map<String, Object>> dataList = new ArrayList<>();
                
                for (ExportDetail detail : exportDetailList) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("exportReceiptID", detail.getExportReceiptID().getExportReceiptID());
                    data.put("productID", detail.getProductID().getProductID());
                    data.put("productName", detail.getProductID().getProductName());
                    data.put("unitID", detail.getProductID().getUnit().getUnitID());
                    data.put("unitName", detail.getProductID().getUnit().getUnitName());
                    data.put("quantityExport", detail.getQuantityExport());
                    data.put("exportPrice", detail.getExportPrice());
                    data.put("intoMoney", detail.getIntoMoney());
                    dataList.add(data);
                }
                
                response.put("code", 200);
                response.put("status", "SUCCESS");
                response.put("message", "Lấy chi tiết xuất hàng thành công");
                response.put("data", dataList);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy chi tiết phiếu xuất hàng");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách chi tiết phiếu xuất hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getExportDetailByProductId")
    public ResponseEntity<Map<String, Object>> getExportDetailsByProductId(@RequestParam int productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ExportDetail> exportDetailList = exportDetailService.getExportDetailByProductID(productId);
            if (!exportDetailList.isEmpty()) {
                List<Map<String, Object>> dataList = new ArrayList<>();
                
                for (ExportDetail detail : exportDetailList) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("exportReceiptID", detail.getExportReceiptID().getExportReceiptID());
                    data.put("productID", detail.getProductID().getProductID());
                    data.put("productName", detail.getProductID().getProductName());
                    data.put("unitID", detail.getProductID().getUnit().getUnitID());
                    data.put("unitName", detail.getProductID().getUnit().getUnitName());
                    data.put("quantityExport", detail.getQuantityExport());
                    data.put("exportPrice", detail.getExportPrice());
                    data.put("intoMoney", detail.getIntoMoney());
                    dataList.add(data);
                }
                
                response.put("code", 200);
                response.put("status", "SUCCESS");
                response.put("message", "Lấy chi tiết xuất hàng thành công");
                response.put("data", dataList);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy chi tiết phiếu xuất hàng theo sản phẩm");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách chi tiết phiếu xuất hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getExportDetailByReceiptAndProduct")
    public ResponseEntity<Map<String, Object>> getExportDetailByReceiptAndProduct(
            @RequestParam int exportReceiptId,
            @RequestParam int productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            ExportDetail exportDetail = exportDetailService.getExportDetailByExportReceiptIDAndProductID(exportReceiptId, productId);
            if (exportDetail != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("exportReceiptID", exportDetail.getExportReceiptID().getExportReceiptID());
                data.put("productID", exportDetail.getProductID().getProductID());
                data.put("productName", exportDetail.getProductID().getProductName());
                data.put("unitID", exportDetail.getProductID().getUnit().getUnitID());
                data.put("unitName", exportDetail.getProductID().getUnit().getUnitName());
                data.put("quantityExport", exportDetail.getQuantityExport());
                data.put("exportPrice", exportDetail.getExportPrice());
                data.put("intoMoney", exportDetail.getIntoMoney());
                
                List<Map<String, Object>> dataList = new ArrayList<>();
                dataList.add(data);
                
                response.put("code", 200);
                response.put("status", "SUCCESS");
                response.put("message", "Lấy chi tiết xuất hàng thành công");
                response.put("data", dataList);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy chi tiết phiếu xuất hàng");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi tìm chi tiết phiếu xuất hàng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addExportDetail")
    public ResponseEntity<Map<String, Object>> createExportDetails(@RequestBody List<ExportDetail> newExportDetailList) {
        Map<String, Object> response = new LinkedHashMap<>();

        System.out.println("=== Starting ExportDetail Creation Process ===");

        if (newExportDetailList.isEmpty()) {
            System.out.println("Error: Empty export detail list");
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Danh sách chi tiết phiếu xuất không được để trống");
            response.put("data", null);
            return ResponseEntity.status(400).body(response);
        }

        // Process only the first export detail for the simplified response
        ExportDetail detail = newExportDetailList.get(0);
        
        try {
            System.out.println("\nProcessing ExportDetail for Product ID: " + detail.getProductID().getProductID());
            
            // Fetch complete Product information
            Product product = productService.getProductById(detail.getProductID().getProductID());
            if (product == null) {
                System.out.println("Error: Product not found with ID: " + detail.getProductID().getProductID());
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không tìm thấy sản phẩm với ID: " + detail.getProductID().getProductID());
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }

            // Fetch complete ExportReceipt information
            ExportReceipt receipt = exportReceiptService.getExportReceiptById(detail.getExportReceiptID().getExportReceiptID());
            if (receipt == null) {
                System.out.println("Error: Export receipt not found with ID: " + detail.getExportReceiptID().getExportReceiptID());
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không tìm thấy phiếu xuất với ID: " + detail.getExportReceiptID().getExportReceiptID());
                response.put("data", null);
                return ResponseEntity.status(400).body(response);
            }

            // Set complete objects and calculated fields
            detail.setProductID(product);
            detail.setExportReceiptID(receipt);
            detail.setExportPrice(product.getExportPrice());
            detail.setIntoMoney(detail.getExportPrice() * detail.getQuantityExport());

            System.out.println("Calculated values:");
            System.out.println("- Export Price: " + detail.getExportPrice());
            System.out.println("- Quantity: " + detail.getQuantityExport());
            System.out.println("- Into Money: " + detail.getIntoMoney());

            // Create the export detail via service
            ExportDetail savedDetail = exportDetailService.createExportDetail(detail);

            if (savedDetail != null) {
                System.out.println("Successfully saved ExportDetail");
                
                // Update product inventory quantity
                boolean inventoryUpdated = productService.downInventoryQuantity(savedDetail.getProductID(), savedDetail.getQuantityExport());
                if (!inventoryUpdated) {
                    System.out.println("Warning: Failed to update inventory for product ID: " + savedDetail.getProductID().getProductID());
                }

                // Create flattened response data
                Map<String, Object> data = new HashMap<>();
                data.put("exportReceiptID", savedDetail.getExportReceiptID().getExportReceiptID());
                data.put("productID", savedDetail.getProductID().getProductID());
                data.put("productName", savedDetail.getProductID().getProductName());
                data.put("unitID", savedDetail.getProductID().getUnit().getUnitID());
                data.put("unitName", savedDetail.getProductID().getUnit().getUnitName());
                data.put("quantityExport", savedDetail.getQuantityExport());
                data.put("exportPrice", savedDetail.getExportPrice());
                data.put("intoMoney", savedDetail.getIntoMoney());

                System.out.println("=== ExportDetail Creation Process Completed ===");
                response.put("code", 201);
                response.put("status", "CREATED");
                response.put("message", "Thêm chi tiết xuất hàng thành công");
                response.put("data", data);
                return ResponseEntity.status(201).body(response);
            } else {
                System.out.println("Error: Failed to save ExportDetail");
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể tạo chi tiết phiếu xuất hàng");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            System.out.println("Error processing ExportDetail: " + e.getMessage());
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi xử lý chi tiết phiếu xuất: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
