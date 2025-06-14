package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;

@Entity
public class DebtReport 
{
    @EmbeddedId
    private DebtReportID debtReportID;

    @Column(name = "firstDebt") // số tiền nợ đầu kỳ
    private int firstDebt;
    @Column(name = "lastDebt") // số tiền nợ cuối kỳ
    private int lastDebt;
    @Column(name = "arisenDebt") // số tiền nợ phát sinh
    private int arisenDebt;

    public DebtReport() 
    {

    }

    public DebtReport(DebtReportID debtReportID, int firstDebt, int lastDebt, int arisenDebt) 
    {
        this.debtReportID = debtReportID;
        this.firstDebt = firstDebt;
        this.lastDebt = lastDebt;
        this.arisenDebt = arisenDebt;
    }

    public DebtReportID getDebtReportID() 
    {
        return debtReportID;
    }

    public void setDebtReportID(org.example.AgentManagementBE.Model.DebtReportID debtReportID) 
    {
        this.debtReportID = debtReportID;
    }

    public void setFirstDebt(int firstDebt) 
    {
        this.firstDebt = firstDebt;
    }

    public void setLastDebt(int lastDebt) 
    {
        this.lastDebt = lastDebt;
    }

    public void setArisenDebt(int arisenDebt) 
    {
        this.arisenDebt = arisenDebt;
    }

    public int getLastDebt() 
    {
        return lastDebt;
    }

    public int getFirstDebt() 
    {
        return firstDebt;
    }

    public int getArisenDebt() 
    {
        return arisenDebt;
    }
}
