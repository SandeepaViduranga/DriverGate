package com.example.drivergate.Modles;

public class User {
    String ID;
    String UserName;
    String Email;
    String Mobile;
    String City;
    String Password;

    public User(String ID, String userName, String email, String mobile, String city, String password) {
        this.ID = ID;
        UserName = userName;
        Email = email;
        Mobile = mobile;
        City = city;
        Password = password;
    }

    public User(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}