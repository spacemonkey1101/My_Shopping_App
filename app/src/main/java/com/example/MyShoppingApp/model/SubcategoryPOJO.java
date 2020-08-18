package com.example.MyShoppingApp.model;

public class SubcategoryPOJO {
     String id;
     String category;
     String subcategory;
     String icon;
     Boolean status;

    public SubcategoryPOJO(String id, String category, String subcategory, String icon, Boolean status) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.icon = icon;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
