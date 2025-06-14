package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.ImportDetail;
import org.example.AgentManagementBE.Model.ImportReceipt;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Repository.ImportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

@Service
public class ImportDetailService {
    private static ImportDetailRepository importDetailRepository;
    private static ProductService productService;

    @Autowired
    public ImportDetailService(ImportDetailRepository importDetailRepository, ProductService productService) {
        this.importDetailRepository = importDetailRepository;
        this.productService = productService;
    }

    public static Map<String, Object> getImportDetailByImportReceiptID(int importReceiptID) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ImportDetail> importDetails = importDetailRepository.getImportDetailByImportReceiptID(importReceiptID);
            if (!importDetails.isEmpty()) {
                List<Map<String, Object>> formattedData = new ArrayList<>();
                for (ImportDetail detail : importDetails) {
                    Map<String, Object> formattedDetail = new HashMap<>();
                    formattedDetail.put("importReceiptID", detail.getImportReceiptID().getImportReceiptID());
                    formattedDetail.put("productID", detail.getProductID().getProductID());
                    formattedDetail.put("productName", detail.getProductID().getProductName());
                    formattedDetail.put("unitID", detail.getProductID().getUnit().getUnitID());
                    formattedDetail.put("unitName", detail.getProductID().getUnit().getUnitName());
                    formattedDetail.put("quantityImport", detail.getQuantityImport());
                    formattedDetail.put("importPrice", detail.getImportPrice());
                    formattedDetail.put("intoMoney", detail.getIntoMoney());
                    formattedData.add(formattedDetail);
                }
                
                response.put("code", 200);
                response.put("status", "SUCCESS");
                response.put("message", "Lấy chi tiết nhập hàng thành công");
                response.put("data", formattedData);
            } else {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy chi tiết nhập hàng cho phiếu nhập ID: " + importReceiptID);
                response.put("data", null);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi lấy chi tiết nhập hàng: " + e.getMessage());
            response.put("data", null);
        }
        return response;
    }

    public static Map<String, Object> getImportDetailByProductID(int productID) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ImportDetail> importDetails = importDetailRepository.getImportDetailByProductID(productID);
            if (!importDetails.isEmpty()) {
                List<Map<String, Object>> formattedData = new ArrayList<>();
                for (ImportDetail detail : importDetails) {
                    Map<String, Object> formattedDetail = new HashMap<>();
                    formattedDetail.put("importReceiptID", detail.getImportReceiptID().getImportReceiptID());
                    formattedDetail.put("productID", detail.getProductID().getProductID());
                    formattedDetail.put("productName", detail.getProductID().getProductName());
                    formattedDetail.put("unitID", detail.getProductID().getUnit().getUnitID());
                    formattedDetail.put("unitName", detail.getProductID().getUnit().getUnitName());
                    formattedDetail.put("quantityImport", detail.getQuantityImport());
                    formattedDetail.put("importPrice", detail.getImportPrice());
                    formattedDetail.put("intoMoney", detail.getIntoMoney());
                    formattedData.add(formattedDetail);
                }
                
                response.put("code", 200);
                response.put("status", "SUCCESS");
                response.put("message", "Lấy chi tiết nhập hàng thành công");
                response.put("data", formattedData);
            } else {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy chi tiết nhập hàng cho sản phẩm ID: " + productID);
                response.put("data", null);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi lấy chi tiết nhập hàng: " + e.getMessage());
            response.put("data", null);
        }
        return response;
    }

    public static Map<String, Object> getImportDetailByImportReceiptIDAndProductID(int importReceiptID, int productID) {
        Map<String, Object> response = new HashMap<>();
        try {
            ImportDetail importDetail = importDetailRepository.getImportDetailByImportReceiptIDAndProductID(importReceiptID, productID);
            if (importDetail != null) {
                Map<String, Object> formattedDetail = new HashMap<>();
                formattedDetail.put("importReceiptID", importDetail.getImportReceiptID().getImportReceiptID());
                formattedDetail.put("productID", importDetail.getProductID().getProductID());
                formattedDetail.put("productName", importDetail.getProductID().getProductName());
                formattedDetail.put("unitID", importDetail.getProductID().getUnit().getUnitID());
                formattedDetail.put("unitName", importDetail.getProductID().getUnit().getUnitName());
                formattedDetail.put("quantityImport", importDetail.getQuantityImport());
                formattedDetail.put("importPrice", importDetail.getImportPrice());
                formattedDetail.put("intoMoney", importDetail.getIntoMoney());
                
                List<Map<String, Object>> dataList = new ArrayList<>();
                dataList.add(formattedDetail);
                
                response.put("code", 200);
                response.put("status", "SUCCESS");
                response.put("message", "Lấy chi tiết nhập hàng thành công");
                response.put("data", dataList);
            } else {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy chi tiết nhập hàng cho phiếu nhập ID: " + importReceiptID + " và sản phẩm ID: " + productID);
                response.put("data", null);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi lấy chi tiết nhập hàng: " + e.getMessage());
            response.put("data", null);
        }
        return response;
    }
    
    public static Map<String, Object> createImportDetail(ImportDetail newImportDetail) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Verify import receipt exists
            ImportReceipt existingImportReceipt = ImportReceiptService.getImportReceiptById(newImportDetail.getImportReceiptID().getImportReceiptID());
            if (existingImportReceipt != null) {
                // Save the import detail with complete information
                ImportDetail savedImportDetail = importDetailRepository.save(newImportDetail);
                
                // Update product inventory
                updateProduct(newImportDetail);
                
                // Create formatted response data
                Map<String, Object> formattedDetail = new HashMap<>();
                formattedDetail.put("importReceiptID", savedImportDetail.getImportReceiptID().getImportReceiptID());
                formattedDetail.put("productID", savedImportDetail.getProductID().getProductID());
                formattedDetail.put("productName", savedImportDetail.getProductID().getProductName());
                formattedDetail.put("unitID", savedImportDetail.getProductID().getUnit().getUnitID());
                formattedDetail.put("unitName", savedImportDetail.getProductID().getUnit().getUnitName());
                formattedDetail.put("quantityImport", savedImportDetail.getQuantityImport());
                formattedDetail.put("importPrice", savedImportDetail.getImportPrice());
                formattedDetail.put("intoMoney", savedImportDetail.getIntoMoney());
                
                List<Map<String, Object>> dataList = new ArrayList<>();
                dataList.add(formattedDetail);
                
                response.put("code", 201);
                response.put("status", "CREATED");
                response.put("message", "Thêm chi tiết nhập hàng thành công");
                response.put("data", dataList);
            } else {
                response.put("code", 404);
                response.put("status", "NOT_FOUND");
                response.put("message", "Không tìm thấy phiếu nhập hàng với ID: " + newImportDetail.getImportReceiptID().getImportReceiptID());
                response.put("data", null);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi tạo chi tiết nhập hàng: " + e.getMessage());
            response.put("data", null);
        }
        return response;
    }
    
    private static void updateProduct(ImportDetail importDetail) {
        productService.upInventoryQuantity(importDetail.getProductID(), importDetail.getQuantityImport());
    }
}