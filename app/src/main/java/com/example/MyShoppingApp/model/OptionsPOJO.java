package com.example.MyShoppingApp.model;

public class OptionsPOJO {
    private int id;
    private int childQuestionId;
    private String choice;
    private int questionId;

    public OptionsPOJO(int childQuestionId, String choice, int questionId) {
        this.childQuestionId = childQuestionId;
        this.choice = choice;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChildQuestionId() {
        return childQuestionId;
    }

    public void setChildQuestionId(int childQuestionId) {
        this.childQuestionId = childQuestionId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
