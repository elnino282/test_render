package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.ExportDetail;
import org.example.AgentManagementBE.Repository.ExportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExportDetailService {
    private final ExportDetailRepository exportDetailRepository;
    private final ProductService productService;

    @Autowired
    public ExportDetailService(ExportDetailRepository exportDetailRepository, ProductService productService) {
        this.exportDetailRepository = exportDetailRepository;
        this.productService = productService;
    }

    public List<ExportDetail> getExportDetailByExportReceiptID(int exportReceiptId) {
        try {
            return exportDetailRepository.getExportDetailByExportReceiptID(exportReceiptId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving export details by receipt ID: " + e.getMessage());
        }
    }

    public List<ExportDetail> getExportDetailByProductID(int productId) {
        try {
            return exportDetailRepository.getExportDetailByProductID(productId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving export details by product ID: " + e.getMessage());
        }
    }

    public ExportDetail getExportDetailByExportReceiptIDAndProductID(int exportReceiptId, int productId) {
        try {
            return exportDetailRepository.getExportDetailByExportReceiptIDAndProductID(exportReceiptId, productId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving export detail: " + e.getMessage());
        }
    }

    public ExportDetail createExportDetail(ExportDetail newExportDetail) {
        try {
            System.out.println("\n=== Creating ExportDetail ===");
            System.out.println("Product ID: " + newExportDetail.getProductID().getProductID());
            System.out.println("Export Receipt ID: " + newExportDetail.getExportReceiptID().getExportReceiptID());
            System.out.println("Quantity: " + newExportDetail.getQuantityExport());
            System.out.println("Export Price: " + newExportDetail.getExportPrice());
            System.out.println("Into Money: " + newExportDetail.getIntoMoney());

            if (newExportDetail == null) {
                System.out.println("Error: Export detail is null");
                throw new IllegalArgumentException("Export detail cannot be null");
            }

            if (newExportDetail.getProductID() == null) {
                System.out.println("Error: Product ID is null");
                throw new IllegalArgumentException("Product ID is required");
            }

            if (newExportDetail.getQuantityExport() <= 0) {
                System.out.println("Error: Invalid quantity: " + newExportDetail.getQuantityExport());
                throw new IllegalArgumentException("Export quantity must be greater than 0");
            }

            ExportDetail savedDetail = exportDetailRepository.save(newExportDetail);
            System.out.println("Successfully saved ExportDetail");
            System.out.println("=== ExportDetail Creation Completed ===\n");
            return savedDetail;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Error creating export detail: " + e.getMessage());
            throw new RuntimeException("Error creating export detail: " + e.getMessage());
        }
    }

    private boolean updateProduct(ExportDetail exportDetail) {
        try {
            System.out.println("\n=== Updating Product Inventory ===");
            System.out.println("Product ID: " + exportDetail.getProductID().getProductID());
            System.out.println("Quantity to reduce: " + exportDetail.getQuantityExport());
            
            boolean result = productService.downInventoryQuantity(exportDetail.getProductID(), exportDetail.getQuantityExport());
            System.out.println("Inventory Update Result: " + (result ? "Success" : "Failed"));
            System.out.println("=== Product Inventory Update Completed ===\n");
            return result;
        } catch (Exception e) {
            System.out.println("Error updating product inventory: " + e.getMessage());
            throw new RuntimeException("Error updating product inventory: " + e.getMessage());
        }
    }
}