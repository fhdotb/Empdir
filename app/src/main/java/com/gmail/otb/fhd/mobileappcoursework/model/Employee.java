package com.gmail.otb.fhd.mobileappcoursework.model;


import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by fahadalms3odi on 4/22/18.
 */

public class Employee implements Parcelable {


    private String employeeID;

    private String email;

    private String password;

    private String mobileNamber;

    private EmployeeRole role;

    private String firstName;

    private String lastName;

    private String photo;

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
        return email;
    }

    public void setEmail (String Email)
    {
        this.email = Email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String Password)
    {
        this.password = Password;
    }

    public String getMobileNamber ()
    {
        return mobileNamber;
    }

    public void setMobileNamber (String MobileNamber)
    {
        this.mobileNamber = MobileNamber;
    }

    public EmployeeRole getRole ()
    {
        return role;
    }

    public void setRole (EmployeeRole Role)
    {
        this.role = Role;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String FirstName)
    {
        this.firstName = FirstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String LastName)
    {
        this.lastName = LastName;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String Photo)
    {
        this.photo = Photo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [employeeID = "+employeeID+", Email = "+email+", Password = "+password+", MobileNamber = "+mobileNamber+", Role = "+role+", FirstName = "+firstName+", LastName = "+lastName+", Photo = "+photo+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(employeeID);
        out.writeString(email);
        out.writeString(password);
        out.writeString(mobileNamber);
        out.writeString(firstName);
        out.writeString(lastName);
       // out.writeParcelable(Role,flags);
        out.writeString(photo);

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
        email = in.readString();
        password = in.readString();
        mobileNamber = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        photo = in.readString();
       // Role = (EmployeeRole)in.readParcelable(getClass().getClassLoader());

    }


    public Employee()
    {

    }

}
