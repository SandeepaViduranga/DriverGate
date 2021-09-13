package com.example.drivergate.Modles;

public class Questions {

    private String ds_question,ds_answer1,ds_answer2,ds_answer3,ds_answer4,ds_correct;

    public Questions(String ds_question, String ds_answer1, String ds_answer2, String ds_answer3, String ds_answer4, String ds_correct) {
        this.ds_question = ds_question;
        this.ds_answer1 = ds_answer1;
        this.ds_answer2 = ds_answer2;
        this.ds_answer3 = ds_answer3;
        this.ds_answer4 = ds_answer4;
        this.ds_correct = ds_correct;
    }

    public String getDs_question() {
        return ds_question;
    }

    public String getDs_answer1() {
        return ds_answer1;
    }

    public String getDs_answer2() {
        return ds_answer2;
    }

    public String getDs_answer3() {
        return ds_answer3;
    }

    public String getDs_answer4() {
        return ds_answer4;
    }

    public String getDs_correct() {
        return ds_correct;
    }

    public void setDs_question(String ds_question) {
        this.ds_question = ds_question;
    }

    public void setDs_answer1(String ds_answer1) {
        this.ds_answer1 = ds_answer1;
    }

    public void setDs_answer2(String ds_answer2) {
        this.ds_answer2 = ds_answer2;
    }

    public void setDs_answer3(String ds_answer3) {
        this.ds_answer3 = ds_answer3;
    }

    public void setDs_answer4(String ds_answer4) {
        this.ds_answer4 = ds_answer4;
    }

    public void setDs_correct(String ds_correct) {
        this.ds_correct = ds_correct;
    }

}
