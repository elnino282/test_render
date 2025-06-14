package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;

@Entity
public class District 
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "districtID") // mã quận
    private int districtID;
    @Column(name = "districtName",nullable = false,columnDefinition = "NVARCHAR(255)") // tên quận
    private String districtName;

    public District(int districtID) 
    {
        this.districtID = districtID;
    }

    public District(String districtName) 
    {

        this.districtName = districtName;
    }

    public District() 
    {

    }

    public int getDistrictNO() 
    {
        return districtID;
    }

    public void setDistrictNO(Integer districtNO) 
    {
        this.districtID = districtNO;
    }

    public String getDistrictName() 
    {
        return districtName;
    }

    public void setDistrictName(String districtName) 
    {
        this.districtName = districtName;
    }
}
