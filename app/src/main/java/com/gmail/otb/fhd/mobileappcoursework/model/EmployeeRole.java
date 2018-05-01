package com.gmail.otb.fhd.mobileappcoursework.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fahadalms3odi on 4/22/18.
 */

public class EmployeeRole implements Parcelable {

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



    // Constructor
    public EmployeeRole(String Supervisor, String JobTitle){
        this.Supervisor = Supervisor;
        this.JobTitle = JobTitle;
    }


    // Parcelling part
    public EmployeeRole(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.Supervisor = data[0];
        this.JobTitle = data[1];

    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[] {
                this.Supervisor,
                this.JobTitle});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public EmployeeRole createFromParcel(Parcel in) {
            return new EmployeeRole(in);
        }

        public EmployeeRole[] newArray(int size) {
            return new EmployeeRole[size];
        }
    };
}
