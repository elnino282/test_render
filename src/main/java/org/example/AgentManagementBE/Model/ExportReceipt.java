package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExportReceipt 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exportReceiptID") // mã phiếu xuất
    private int exportReceiptID;

    @Column(name = "dateReceipt", nullable = false) // ngày lập phiếu
    private Date dateReceipt;

    @ManyToOne
    @JoinColumn(name = "agentID", nullable = false) // mã đại lý
    private Agent agentID;

    @Column(name = "totalMoney") // tổng tiền
    private int totalMoney;

    @Column(name = "paymentAmount") // số tiền thanh toán
    private int paymentAmount;

    @Column(name = "remainAmount") // số tiền còn lại
    private int remainAmount;



    public ExportReceipt() 
    {
    }

    public ExportReceipt(int exportReceiptID) 
    {
        this.exportReceiptID = exportReceiptID;
    }

    public ExportReceipt(Agent agentID, int totalMoney, int paymentAmount, int remainAmount) 
    {
        this.agentID = agentID;
        this.totalMoney = totalMoney;
        this.paymentAmount = paymentAmount;
        this.remainAmount = remainAmount;
    }

    public int getExportReceiptID() 
    {
        return exportReceiptID;
    }

    public void setExportReceiptID(int exportReceiptID) 
    {
        this.exportReceiptID = exportReceiptID;
    }

    public Date getDateReceipt() 
    {
        return dateReceipt;
    }

    public void setDateReceipt(Date dateReceipt) 
    {
        this.dateReceipt = dateReceipt;
    }

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) 
    {
        this.agentID = agentID;
    }

    public int getTotalMoney() 
    {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) 
    {
        this.totalMoney = totalMoney;
    }

    public int getPaymentAmount() 
    {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public int getRemainAmount() 
    {
        return remainAmount;
    }

    public void setRemainAmount(int remainAmount) 
    {
        this.remainAmount = remainAmount;
    }

  
}
