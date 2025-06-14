package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Map<String, Object>> getProductById(@RequestParam int productId) {
        Map<String, Object> response = new HashMap<>();
        Product product = productService.getProductById(productId);
        if (product != null) {
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Tìm thấy sản phẩm thành công");
            response.put("data", product);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("status", "error");
            response.put("message", "Không tìm thấy sản phẩm");
            response.put("data", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            var productList = productService.getAllProduct();
            if (!productList.isEmpty()) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Lấy danh sách sản phẩm thành công");
                response.put("data", productList);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy sản phẩm nào");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Product newProduct) {
        Map<String, Object> response = new HashMap<>();
        Product temp = productService.createProduct(newProduct);
        if (temp != null) {
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Tạo sản phẩm mới thành công");
            response.put("data", temp);
            return ResponseEntity.status(201).body(response);
        } else {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Dữ liệu sản phẩm không hợp lệ hoặc không tìm thấy đơn vị");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/getInventoryQuantity")
    public ResponseEntity<Map<String, Object>> getInventoryQuantity(@RequestParam String productName) {
        Map<String, Object> response = new HashMap<>();
        int inventoryQuantity = productService.getInventoryQuantityByProductName(productName);
        if (inventoryQuantity != -1) {
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy số lượng tồn kho thành công");
            response.put("data", inventoryQuantity);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("status", "error");
            response.put("message", "Không tìm thấy sản phẩm");
            response.put("data", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/increaseInventory")
    public ResponseEntity<Map<String, Object>> increaseInventory(
            @RequestParam int productId,
            @RequestParam int quantity) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productService.getProductById(productId);
            if (product == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy sản phẩm");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            productService.upInventoryQuantity(product, quantity);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Tăng số lượng tồn kho thành công");
            response.put("data", productService.getProductById(productId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi tăng số lượng tồn kho: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/decreaseInventory")
    public ResponseEntity<Map<String, Object>> decreaseInventory(
            @RequestParam int productId,
            @RequestParam int quantity) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productService.getProductById(productId);
            if (product == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy sản phẩm");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            boolean success = productService.downInventoryQuantity(product, quantity);
            if (success) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Giảm số lượng tồn kho thành công");
                response.put("data", productService.getProductById(productId));
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Số lượng tồn kho không đủ");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi giảm số lượng tồn kho: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
