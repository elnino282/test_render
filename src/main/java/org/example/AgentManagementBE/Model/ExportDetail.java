package org.example.AgentManagementBE.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ExportDetail.ExportDetailID.class)
public class ExportDetail 
{
    @Id
    @ManyToOne
    @JoinColumn(name = "exportReceiptID") // mã phiếu xuất
    private ExportReceipt exportReceiptID;

    @Id
    @ManyToOne
    @JoinColumn(name = "productID") // mã sản phẩm
    private Product productID;

    @Column(name = "quantityExport") // số lượng xuất
    private int quantityExport;

    @Column(name = "exportPrice") // giá xuất
    private int exportPrice;

    @Column(name = "intoMoney") // thành tiền
    private int intoMoney;


    public ExportDetail() 
    {

    }

    public ExportDetail(ExportReceipt exportReceiptID, Product productID, int quantityExport, int exportPrice, int intoMoney) 
    {
        this.exportReceiptID = exportReceiptID;
        this.productID = productID;
        this.quantityExport = quantityExport;
        this.exportPrice = exportPrice;
        this.intoMoney = intoMoney;
    }

    public ExportReceipt getExportReceiptID() 
    {
        return exportReceiptID;
    }

    public void setExportReceiptID(ExportReceipt exportReceiptID) 
    {
        this.exportReceiptID = exportReceiptID;
    }

    public Product getProductID() 
    {
        return productID;
    }

    public void setProductID(Product productID) 
    {
        this.productID = productID;
    }

    public int getQuantityExport() 
    {
        return quantityExport;
    }

    public void setQuantityExport(int quantityExport) 
    {
        this.quantityExport = quantityExport;
    }

    public int getExportPrice() 
    {
        return exportPrice;
    }

    public void setExportPrice(int exportPrice) 
    {
        this.exportPrice = exportPrice;
    }

    public int getIntoMoney() 
    {
        return intoMoney;
    }

    public void setIntoMoney(int intoMoney) 
    {
        this.intoMoney = intoMoney;
    }

    public static class ExportDetailID implements Serializable 
    {
        private int exportReceiptID;
        private int productID;

        public ExportDetailID() 
        {

        }

        public int getExportReceiptID() 
        {
            return exportReceiptID;
        }

        public void setExportReceiptID(int exportReceiptID) 
        {
            this.exportReceiptID = exportReceiptID;
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
