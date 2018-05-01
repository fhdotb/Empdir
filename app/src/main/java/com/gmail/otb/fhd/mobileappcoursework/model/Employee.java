package com.gmail.otb.fhd.mobileappcoursework.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fahadalms3odi on 4/22/18.
 */

public class Employee implements Parcelable {


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(employeeID);
        out.writeString(Email);
        out.writeString(Password);
        out.writeString(MobileNamber);
        out.writeString(FirstName);
        out.writeString(LastName);
       // out.writeParcelable(Role,flags);
        out.writeString(Photo);

    }

    public static final Parcelable.Creator<Employee> CREATOR
            = new Parcelable.Creator<Employee>() {
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    private Employee(Parcel in) {
        employeeID = in.readString();
        Email = in.readString();
        Password = in.readString();
        MobileNamber = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        Photo = in.readString();
       // Role = (EmployeeRole)in.readParcelable(getClass().getClassLoader());

    }

}
