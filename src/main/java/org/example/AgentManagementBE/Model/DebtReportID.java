package org.example.AgentManagementBE.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class DebtReportID implements Serializable 
{
    @Column(name = "monthTime") // tháng
    private int month;
    @Column(name = "yearTime") // năm
    private int year;
    @ManyToOne
    @JoinColumn(name = "agentID") // mã đại lý
    private Agent agentID;


    public DebtReportID() 
    {

    }

    public DebtReportID(int month, int year, Agent agentID) 
    {
        this.month = month;
        this.year = year;
        this.agentID = agentID;
    }

    public int getMonth() 
    {
        return month;
    }

    public int getYear() 
    {
        return year;
    }

    public Agent getAgentID() 
    {
        return agentID;
    }
}
