package com.example.drivergate.Modles;

public class Instructor {
    String ID;
    String insName;
    String insEmail;
    String insExperience;
    String insCity;
    String insMobile;
    String insPassword;

    public Instructor(String ID, String inName, String inEmail, String inExperience, String inCity, String inMobile, String inPassword) {
        this.ID = ID;
        insName = inName;
        insEmail = inEmail;
        insExperience = inExperience;
        insCity = inCity;
        insMobile = inMobile;
        insPassword = inPassword;
    }

    public Instructor(){

    }

    public String getID() {
        return ID;
    }

    public String getInsName() {
        return insName;
    }

    public String getInsEmail() {
        return insEmail;
    }

    public String getInsExperience() {
        return insExperience;
    }

    public String getInsCity() {
        return insCity;
    }

    public String getInsMobile() {
        return insMobile;
    }

    public String getInsPassword() {
        return insPassword;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public void setInsEmail(String insEmail) {
        this.insEmail = insEmail;
    }

    public void setInsExperience(String insExperience) {
        this.insExperience = insExperience;
    }

    public void setInsCity(String insCity) {
        this.insCity = insCity;
    }

    public void setInsMobile(String insMobile) {
        this.insMobile = insMobile;
    }

    public void setInsPassword(String insPassword) {
        this.insPassword = insPassword;
    }

}
