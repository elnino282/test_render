package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.Unit;
import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Repository.UnitRepository;
import org.example.AgentManagementBE.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;
    private final ParameterService parameterService;

    @Autowired
    public ProductService(ProductRepository productRepository, UnitRepository unitRepository, ParameterService parameterService) {
        this.productRepository = productRepository;
        this.unitRepository = unitRepository;
        this.parameterService = parameterService;
    }

    public Product getProductById(int productID) {
        return productRepository.getProductById(productID);
    }

    public int getInventoryQuantityByProductName(String productName) {
        int inventoryQuantity = productRepository.getInventoryQuantityByProductName(productName);
        if (inventoryQuantity != 0) {
            return productRepository.getInventoryQuantityByProductName(productName);
        }
        return -1;
    }

    public Product createProduct(Product newProduct) {
        Unit existingUnit = unitRepository.findByUnitName(newProduct.getUnit().getUnitName());
        if (existingUnit == null) {
            return null;
        }
        newProduct.setUnit(existingUnit);
        
        // Save the product
        Product savedProduct = productRepository.save(newProduct);

        return savedProduct;
    }

    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    public void upInventoryQuantity(Product product, int quantity) {
        Product temp = productRepository.getProductById(product.getProductID());
        temp.setInventoryQuantity(temp.getInventoryQuantity() + quantity);
        productRepository.save(temp);
    }

    public Boolean downInventoryQuantity(Product product, int quantity) {
        Product temp = productRepository.getProductById(product.getProductID());
        if(temp.getInventoryQuantity() < quantity) {
            return false;
        }
        temp.setInventoryQuantity(temp.getInventoryQuantity() - quantity);
        productRepository.save(temp);
        return true;
    }
}
