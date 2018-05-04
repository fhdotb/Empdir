package com.gmail.otb.fhd.mobileappcoursework.model;

import android.os.Parcel;
import android.os.Parcelable;




public class EmployeeRole implements Parcelable {

    private String supervisor;

    private String jobTitle;

    public String getSupervisor ()
    {
        return supervisor;
    }

    public void setSupervisor (String Supervisor)
    {
        this.supervisor = Supervisor;
    }

    public String getJobTitle ()
    {
        return jobTitle;
    }

    public void setJobTitle (String JobTitle)
    {
        this.jobTitle = JobTitle;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Supervisor = "+supervisor+", JobTitle = "+jobTitle+"]";
    }



    // Constructor
    public EmployeeRole(String Supervisor, String JobTitle){
        this.supervisor = Supervisor;
        this.jobTitle = JobTitle;
    }


    // Parcelling part
    public EmployeeRole(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.supervisor = data[0];
        this.jobTitle = data[1];

    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[] {
                this.supervisor,
                this.jobTitle});
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
