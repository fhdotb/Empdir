package com.gmail.otb.fhd.mobileappcoursework.model;

import java.util.List;

/**
 * Created by fahadalms3odi on 4/22/18.
 */

public class EmployeeOffice {

    private String OfficePhone;

    private String LocationName;

    private String OfficeID;

    private String DepartmentName;

    private Employee[] employees;

    public String getOfficePhone ()
    {
        return OfficePhone;
    }

    public void setOfficePhone (String OfficePhone)
    {
        this.OfficePhone = OfficePhone;
    }

    public String getLocationName ()
    {
        return LocationName;
    }

    public void setLocationName (String LocationName)
    {
        this.LocationName = LocationName;
    }

    public String getOfficeID ()
    {
        return OfficeID;
    }

    public void setOfficeID (String OfficeID)
    {
        this.OfficeID = OfficeID;
    }

    public String getDepartmentName ()
    {
        return DepartmentName;
    }

    public void setDepartmentName (String DepartmentName)
    {
        this.DepartmentName = DepartmentName;
    }

    public Employee[] getEmployees ()
    {
        return employees;
    }

    public void setEmployees (Employee[] employees)
    {
        this.employees = employees;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OfficePhone = "+OfficePhone+", LocationName = "+LocationName+", OfficeID = "+OfficeID+", DepartmentName = "+DepartmentName+", employees = "+employees+"]";
    }

}
