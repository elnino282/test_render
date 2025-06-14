package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class ImportReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "importReceiptID") // mã phiếu nhập
    private int importReceiptID;
    @Column(name = "dateReceipt") // ngày lập phiếu
    private Date dateReceipt;

    @Column(name = "totalPrice",nullable = false) // tổng giá
    private int totalPrice;

    public ImportReceipt() {        
    }



    public ImportReceipt(int importReceiptID,int totalPrice) {
        this.importReceiptID = importReceiptID;
        this.totalPrice = totalPrice;
    }

    public ImportReceipt(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ImportReceipt(int importReceiptID, Date dateReceipt, int totalPrice) {
        this.importReceiptID = importReceiptID;
        this.dateReceipt = dateReceipt;
        this.totalPrice = totalPrice;
    }

    public ImportReceipt(Date dateReceipt, int totalPrice) {
        this.dateReceipt = dateReceipt;
        this.totalPrice = totalPrice;
    }

    public int getImportReceiptID() {
        return importReceiptID;
    }

    public Date getDateReceipt() {
        return dateReceipt;
    }
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setDateReceipt(Date dateReceipt) {
        this.dateReceipt = dateReceipt;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}