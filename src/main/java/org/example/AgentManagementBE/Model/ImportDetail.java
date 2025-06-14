package org.example.AgentManagementBE.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ImportDetail.ImportDetailID.class)
public class ImportDetail 
{
    @Id
    @ManyToOne
    @JoinColumn(name = "importReceiptID")
    private ImportReceipt importReceiptID;

    @Id
    @ManyToOne
    @JoinColumn(name = "productID") // mã sản phẩm
    private Product productID;

    @Column(name = "quantityImport") // số lượng nhập
    private int quantityImport;


    @Column(name = "importPrice") // giá nhập
    private int importPrice;

    @Column(name = "intoMoney") // thành tiền
    private int intoMoney;

    @Column(name = "unit") // đơn vị tính
    private int unit;

    public ImportDetail() 
    {

    }

    public ImportDetail(ImportReceipt importReceiptID, Product productID, int quantityImport, int importPrice, int intoMoney, int unit) 
    {
        this.importReceiptID = importReceiptID;
        this.productID = productID;
        this.quantityImport = quantityImport;
        this.importPrice = importPrice;
        this.intoMoney = intoMoney;
        this.unit = unit;
    }

    public ImportReceipt getImportReceiptID() 
    {
        return importReceiptID;
    }

    public void setImportReceiptID(ImportReceipt importReceiptID) 
    {
        this.importReceiptID = importReceiptID;
    }

    public Product getProductID() 
    {
        return productID;
    }

    public void setProductID(Product productID) 
    {
        this.productID = productID;
    }

    public int getQuantityImport() {
        return quantityImport;
    }

    public void setQuantityImport(int quantityImport) 
    {
        this.quantityImport = quantityImport;
    }

    public int getImportPrice() 
    {
        return importPrice;
    }

    public void setImportPrice(int importPrice) 
    {
        this.importPrice = importPrice;
    }

    public int getIntoMoney() 
    {
        return intoMoney;
    }

    public void setIntoMoney(int intoMoney) 
    {
        this.intoMoney = intoMoney;
    }

    public int getUnit() 
    {
        return unit;
    }

    public void setUnit(int unit) 
    {
        this.unit = unit;
    }

    public static class ImportDetailID implements Serializable 
    {
        private int importReceiptID;
        private int productID;

        public ImportDetailID() 
        {

        }

        public int getImportReceiptID() 
        {
            return importReceiptID;
        }

        public void setImportReceiptID(int importReceiptID) 
        {
            this.importReceiptID = importReceiptID;
        }

        public int getProductID() 
        {
            return productID;
        }

        public void setProductID(int productID) 
        {
            this.productID = productID;
        }
    }
}
