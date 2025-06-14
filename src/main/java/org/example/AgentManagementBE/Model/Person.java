package org.example.AgentManagementBE.Model;

import jakarta.persistence.*;   

@Entity
public class Person 
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "personID") // mã người dùng
    private int personID;
    @Column(name = "personLastName",nullable = false,columnDefinition = "NVARCHAR(255)") // họ
    private String personLastName;
    @Column(name = "personName",nullable = false,columnDefinition = "NVARCHAR(255)") // tên
    private String personName;
    @Column(name = "personSDT",nullable = false) // số điện thoại
    private String personSDT;
    @Column(name = "personEmail",nullable = false,unique = true) // email
    private String personEmail;
    @Column(name = "personPassword",nullable = false) // mật khẩu   
    private String personPassword;

    public Person()
    {

    }

    public Person(String personLastName, String personName, String personSDT, String personEmail, String personPassword) 
    {
        this.personLastName = personLastName;
        this.personName = personName;
        this.personSDT = personSDT;
        this.personEmail = personEmail;
        this.personPassword = personPassword;
    }

    public int getPersonID() 
    {
        return personID;
    }

    public String getPersonLastName() 
    {
        return personLastName;
    }

    public String getPersonName() 
    {
        return personName;
    }

    public String getPersonSDT() 
    {
        return personSDT;
    }

    public String getPersonEmail() 
    {
        return personEmail;
    }

    public String getPersonPassword() 
    {
        return personPassword;
    }

    public void setPersonLastName(String personLastName) 
    {
        this.personLastName = personLastName;
    }

    public void setPersonName(String personName) 
    {
        this.personName = personName;
    }

    public void setPersonSDT(String personSDT) 
    {
        this.personSDT = personSDT;
    }

    public void setPersonEmail(String personEmail) 
    {
        this.personEmail = personEmail;
    }

    public void setPersonPassword(String personPassword) 
    {
        this.personPassword = personPassword;
    }
}
