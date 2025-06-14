package org.example.AgentManagementBE.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Parameter 
{
    @Id
    @Column(name = "parameterName",columnDefinition = "NVARCHAR(255)") // tên tham số
    private String parameterName;
    @Getter
    @Column(name = "parameterValue",nullable = false) // giá trị tham số
    private int parameterValue;

    public Parameter() 
    {

    }

    public Parameter(String parameterName, int parameterValue) 
    {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterName() 
    {
        return parameterName;
    }

    public void setParameterName(String parameterName) 
    {
        this.parameterName = parameterName;
    }

    public void setParameterValue(int parameterValue) 
    {
        this.parameterValue = parameterValue;
    }

	public int getParameterValue() 
    {
		return this.parameterValue;
	}
}