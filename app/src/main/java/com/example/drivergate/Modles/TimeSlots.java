package com.example.drivergate.Modles;

public class TimeSlots {

    private String ID, ts_Instructor,ts_Student,ts_Date,ts_Time,ts_Week,ts_Result, ts_Status;

    public TimeSlots(String ts_instructor, String ts_student, String ts_date, String ts_time, String ts_week, String ts_result, String ts_status) {
        this.ts_Instructor = ts_instructor;
        this.ts_Student = ts_student;
        this.ts_Date = ts_date;
        this.ts_Time = ts_time;
        this.ts_Week = ts_week;
        this.ts_Result = ts_result;
        this.ts_Status = ts_status;
    }

    public TimeSlots(){

    }

    public String getID() {
        return ID;
    }

    public String getTS_Instructor() {
        return ts_Instructor;
    }

    public String getTS_Student() {
        return ts_Student;
    }

    public String getTS_Date() {
        return ts_Date;
    }

    public String getTS_Time() {
        return ts_Time;
    }

    public String getTS_Week() {
        return ts_Week;
    }

    public String getTS_Result() {
        return ts_Result;
    }

    public String getTS_Status() {
        return ts_Status;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTS_Instructor(String ts_instructor) {
        this.ts_Instructor = ts_instructor;
    }

    public void setTS_Student(String ts_student) {
        this.ts_Student = ts_student;
    }

    public void setTS_Date(String ts_date) {
        this.ts_Date = ts_date;
    }

    public void setTS_Time(String ts_time) {
        this.ts_Time = ts_time;
    }

    public void setTS_Week(String ts_week) {
        this.ts_Week = ts_week;
    }

    public void setTS_Result(String ts_result) {
        this.ts_Result = ts_result;
    }

    public void setTS_Status(String ts_status) {
        this.ts_Status = ts_status;
    }

}

