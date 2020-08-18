package com.example.MyShoppingApp.model;

import java.util.List;

public class QuestionPOJO {
    private String id;
    private String question;
    private String type;
    private Boolean required;
    private  String parent_question;
    private String parent_option;
    private int subcategory;
    private String subcategory_name;
    private List<OptionsPOJO> options;

    public List<OptionsPOJO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsPOJO> options) {
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getParent_question() {
        return parent_question;
    }

    public void setParent_question(String parent_question) {
        this.parent_question = parent_question;
    }

    public String getParent_option() {
        return parent_option;
    }

    public void setParent_option(String parent_option) {
        this.parent_option = parent_option;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public int getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(int subcategory) {
        this.subcategory = subcategory;
    }
}
