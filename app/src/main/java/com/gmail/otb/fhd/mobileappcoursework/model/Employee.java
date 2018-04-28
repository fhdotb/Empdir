package com.gmail.otb.fhd.mobileappcoursework.model;


import java.io.Serializable;

/**
 * Created by fahadalms3odi on 4/22/18.
 */

public class Employee implements Serializable {


    private String employeeID;

    private String Email;

    private String Password;

    private String MobileNamber;

    private EmployeeRole Role;

    private String FirstName;

    private String LastName;

    private String Photo;

    public String getEmployeeID ()
    {
        return employeeID;
    }

    public void setEmployeeID (String employeeID)
    {
        this.employeeID = employeeID;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    public String getMobileNamber ()
    {
        return MobileNamber;
    }

    public void setMobileNamber (String MobileNamber)
    {
        this.MobileNamber = MobileNamber;
    }

    public EmployeeRole getRole ()
    {
        return Role;
    }

    public void setRole (EmployeeRole Role)
    {
        this.Role = Role;
    }

    public String getFirstName ()
    {
        return FirstName;
    }

    public void setFirstName (String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getLastName ()
    {
        return LastName;
    }

    public void setLastName (String LastName)
    {
        this.LastName = LastName;
    }

    public String getPhoto ()
    {
        return Photo;
    }

    public void setPhoto (String Photo)
    {
        this.Photo = Photo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [employeeID = "+employeeID+", Email = "+Email+", Password = "+Password+", MobileNamber = "+MobileNamber+", Role = "+Role+", FirstName = "+FirstName+", LastName = "+LastName+", Photo = "+Photo+"]";
    }
}
