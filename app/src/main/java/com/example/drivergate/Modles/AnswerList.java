package com.example.drivergate.Modles;

public class AnswerList {

    private String UserID, question, answer, selectedAnswer, week;

    public AnswerList(String UserID, String question, String answer, String selectedAnswer, String week) {
        this.UserID = UserID;
        this.question = question;
        this.answer = answer;
        this.selectedAnswer = selectedAnswer;
        this.week = week;
    }

    public AnswerList() {

    }

    public String getUserID() {
        return UserID;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public String getWeek() {
        return week;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
