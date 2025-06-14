package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;

@Entity
public class SalesReport 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesReportID") // mã báo cáo doanh số
    private int salesReportID;

    @Column(name = "monthtime",nullable = false) // tháng
    private int month;

    @Column(name = "yeartime",nullable = false) // năm
    private int year;

    @Column(name = "totalRevenue") // tổng doanh số trong tháng
    private int totalRevenue;

    public SalesReport() 
    {
        
    }

    public SalesReport(int month, int year, int totalRevenue) 
    {
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    public SalesReport(int month, int year) 
    {
        this.month = month;
        this.year = year;
    }

    public int getSalesReportID() 
    {
        return salesReportID;
    }

    public void setSalesReportID(int salesReportID) 
    {
        this.salesReportID = salesReportID;
    }

    public int getMonth() 
    {
        return month;
    }

    public void setMonth(int month) 
    {
        this.month = month;
    }

    public int getYear() 
    {
        return year;
    }

    public void setYear(int year) 
    {
        this.year = year;
    }

    public int getTotalRevenue() 
    {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) 
    {
        this.totalRevenue = totalRevenue;
    }
}