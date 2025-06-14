package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;

@Entity(name = "Unit")
public class Unit 
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "unitID") // mã đơn vị tính
    private int unitID;
    @Column(name = "unitName",nullable = false,columnDefinition = "NVARCHAR(255)") // tên đơn vị tính
    private String unitName;

    public Unit() 
    {

    }

    public Unit(String unitName) 
    {
        this.unitName = unitName;
    }

    public int getUnitID() 
    {
        return unitID;
    }

    public void setUnitID(int unitID) 
    {
        this.unitID = unitID;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }
}
