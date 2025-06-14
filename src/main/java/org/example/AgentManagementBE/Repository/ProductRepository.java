package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("SELECT product FROM Product product WHERE product.productID = :productID")
    Product getProductById(@Param("productID") int productID);

    @Query("SELECT product.importPrice FROM Product product WHERE product.productID = :productID")
    Integer getImportPriceByProductID(@Param("productID") int productID);

    @Query("SELECT product.exportPrice FROM Product product WHERE product.productID = :productID")
    Integer getExportPriceByProductID(@Param("productID")int productID);

    @Query("SELECT product FROM Product product")
    List<Product> getAllProduct();

    @Query("SELECT product.inventoryQuantity FROM Product product WHERE product.productName = :productName")
    int getInventoryQuantityByProductName(@Param("productName")String productName);
}
