package com.example.drivergate.Modles;

public class DrivingSchool {
    String ID;
    String DrivingScool;
    String DSEmail;
    String DSCity;
    String DSRate2020;
    String DSRate2019;
    String DSRate2018;
    String DSRate2017;
    String DSRate2016;
    String DSMobile;
    String DSPassword;

    public DrivingSchool(String ID, String drivingScool, String dSEmail, String dsCity, String dsRate2020, String dsRate2019, String dsRate2018, String dsRate2017, String dsRate2016, String dsMobile, String dsPassword) {
        this.ID = ID;
        DrivingScool = drivingScool;
        DSEmail = dSEmail;
        DSCity = dsCity;
        DSRate2020 = dsRate2020;
        DSRate2019 = dsRate2019;
        DSRate2018 = dsRate2018;
        DSRate2017 = dsRate2017;
        DSRate2016 = dsRate2016;
        DSMobile = dsMobile;
        DSPassword = dsPassword;
    }

    public DrivingSchool(){

    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDrivingScool(String drivingScool) {
        this.DrivingScool = drivingScool;
    }

    public void setDSEmail(String DSEmail) {
        this.DSEmail = DSEmail;
    }

    public void setDSCity(String DSCity) {
        this.DSCity = DSCity;
    }

    public void setDSRate2020(String DSRate2020) {
        this.DSRate2020 = DSRate2020;
    }

    public void setDSRate2019(String DSRate2019) {
        this.DSRate2019 = DSRate2019;
    }

    public void setDSRate2018(String DSRate2018) {
        this.DSRate2018 = DSRate2018;
    }

    public void setDSRate2017(String DSRate2017) {
        this.DSRate2017 = DSRate2017;
    }

    public void setDSRate2016(String DSRate2016) {
        this.DSRate2016 = DSRate2016;
    }

    public void setDSMobile(String DSMobile) {
        this.DSMobile = DSMobile;
    }

    public void setDSPassword(String DSPassword) {
        this.DSPassword = DSPassword;
    }

    public String getID() {
        return ID;
    }

    public String getDrivingScool() {
        return DrivingScool;
    }

    public String getDSEmail() {
        return DSEmail;
    }

    public String getDSCity() {
        return DSCity;
    }

    public String getDSRate2020() {
        return DSRate2020;
    }

    public String getDSRate2019() {
        return DSRate2019;
    }

    public String getDSRate2018() {
        return DSRate2018;
    }

    public String getDSRate2017() {
        return DSRate2017;
    }

    public String getDSRate2016() {
        return DSRate2016;
    }

    public String getDSMobile() {
        return DSMobile;
    }

    public String getDSPassword() {
        return DSPassword;
    }
}
