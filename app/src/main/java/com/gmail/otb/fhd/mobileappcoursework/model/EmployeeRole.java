package com.gmail.otb.fhd.mobileappcoursework.model;

/**
 * Created by fahadalms3odi on 4/22/18.
 */

public class EmployeeRole {

    private String Supervisor;

    private String JobTitle;

    public String getSupervisor ()
    {
        return Supervisor;
    }

    public void setSupervisor (String Supervisor)
    {
        this.Supervisor = Supervisor;
    }

    public String getJobTitle ()
    {
        return JobTitle;
    }

    public void setJobTitle (String JobTitle)
    {
        this.JobTitle = JobTitle;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Supervisor = "+Supervisor+", JobTitle = "+JobTitle+"]";
    }
}
