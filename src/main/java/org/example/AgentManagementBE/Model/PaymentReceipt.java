package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PaymentReceipt 
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "paymentReceiptID") // mã phiếu thu tiền
    private int paymentReceiptID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "paymentDate",nullable = false) // ngày thu
    private Date paymentDate;

    @Column(name = "revenue",nullable = false) // tiền thu nợ
    private int revenue;

    @ManyToOne
    @JoinColumn(name = "agentID")
    private Agent agentID;

    public PaymentReceipt() 
    {
        
    }
    public PaymentReceipt(int revenue, Agent agentID) 
    {
        this.revenue = revenue;
        this.agentID = agentID;
    }

    public PaymentReceipt(int paymentReceiptID, Date paymentDate, int revenue, Agent agentID) 
    {
        this.paymentReceiptID = paymentReceiptID;
        this.paymentDate = paymentDate;
        this.revenue = revenue;
        this.agentID = agentID;
    }

    public int getPaymentReceiptID() 
    {
        return paymentReceiptID;
    }

    public void setPaymentReceiptID(int paymentReceiptID) 
    {
        this.paymentReceiptID = paymentReceiptID;
    }

    public Date getPaymentDate() 
    {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) 
    {
        this.paymentDate = paymentDate;
    }

    public int getRevenue() 
    {
        return revenue;
    }

    public void setRevenue(int revenue) 
    {
        this.revenue = revenue;
    }

    public Agent getAgentID() 
    {
        return agentID;
    }

    public void setAgentID(Agent agentID) 
    {
        this.agentID = agentID;
    }
}
