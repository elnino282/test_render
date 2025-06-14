package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;

@Entity
public class Product 
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "productID") // mã sản phẩm
    private int productID;

    @Column(name = "productName", columnDefinition = "NVARCHAR(255)", nullable = false) // tên sản phẩm
    private String productName;
    @ManyToOne
    @JoinColumn(name = "unit", nullable = false) // đơn vị tính
    private Unit unit;
    @Column(name = "importPrice", nullable = false) // giá nhập
    private int importPrice;


    @Column(name = "exportPrice") // giá xuất   
    private int exportPrice;
    @Column(name = "inventoryQuantity")//số lượng tồn kho
    private int inventoryQuantity;

    public Product(int productID,int importPrice)
    {
        this.productID = productID;
        this.importPrice = importPrice;
    }
    public Product(String productName, Unit unit, int importPrice) 
    {
        this.productName = productName;
        this.unit = unit;
        this.importPrice = importPrice;
    }

    public Product() 
    {

    }

    public int getProductID() 
    {
        return productID;
    }

    public void setProductID(int productID) 
    {
        this.productID = productID;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public Unit getUnit() 
    {
        return unit;
    }

    public void setUnit(org.example.AgentManagementBE.Model.Unit unit) {
        this.unit = unit;
    }

    public int getImportPrice() 
    {
        return importPrice;
    }

    public void setImportPrice(int importPrice) 
    {
        this.importPrice = importPrice;
    }

    public int getExportPrice() 
    {
        return exportPrice;
    }

    public void setExportPrice(int exportPrice) 
    {
        this.exportPrice = exportPrice;
    }

    public int getInventoryQuantity() 
    {
        return inventoryQuantity;//so luong ton kho
    }

    public void setInventoryQuantity(int inventoryQuantity) 
    {
        this.inventoryQuantity = inventoryQuantity;
    }
}