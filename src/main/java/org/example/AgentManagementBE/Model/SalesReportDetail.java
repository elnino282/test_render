package org.example.AgentManagementBE.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@IdClass(SalesReportDetail.SalesReportDetailID.class)
public class SalesReportDetail 
{
    @Id
    @ManyToOne
    @JoinColumn(name = "agentID") // mã đại lý
    private Agent agentID;

    @Id
    @ManyToOne
    @JoinColumn(name = "salesReportID") // mã báo cáo doanh số
    private SalesReport salesReportID;

    @Column(name = "quantityExport")//số phiếu xuất
    private int quantityExport;

    @Column(name = "totalValue")//tổng giá trị
    private int totalValue;

    @Column(name = "proportion")//tỷ lệ
    private double proportion;

    public SalesReportDetail() 
    {

    }

    public SalesReportDetail(Agent agentID, SalesReport salesReportID, int quantityExport, int totalValue, double proportion) 
    {
        this.agentID = agentID;
        this.salesReportID = salesReportID;
        this.quantityExport = quantityExport;
        this.totalValue = totalValue;
        this.proportion = proportion;
    }

    public Agent getAgentID() 
    {
        return agentID;
    }

    public void setAgentID(Agent agentID) 
    {
        this.agentID = agentID;
    }

    public SalesReport getSalesReportID() 
    {
        return salesReportID;
    }

    public void setSalesReportID(SalesReport salesReportID) 
    {
        this.salesReportID = salesReportID;
    }

    public int getQuantityExport() 
    {
        return quantityExport;
    }

    public void setQuantityExport(int quantityExport) 
    {
        this.quantityExport = quantityExport;
    }

    public int getTotalValue() 
    {
        return totalValue;
    }

    public void setTotalValue(int totalValue) 
    {
        this.totalValue = totalValue;
    }

    public double getProportion() 
    {
        return proportion;
    }

    public void setProportion(double proportion) 
    {
        this.proportion = proportion;
    }

    public static class SalesReportDetailID implements Serializable 
    {
        private int agentID;
        private int salesReportID;

        public SalesReportDetailID() 
        {

        }

        public SalesReportDetailID(int agentID, int salesReportID) 
        {
            this.agentID = agentID;
            this.salesReportID = salesReportID;
        }

        public int getAgentID() 
        {
            return agentID;
        }

        public void setAgentID(int agentID) 
        {
            this.agentID = agentID;
        }

        public int getSalesReportID() 
        {
            return salesReportID;
        }

        public void setSalesReportID(int salesReportID) 
        {
            this.salesReportID = salesReportID;
        }
    }
}
