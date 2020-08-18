package com.example.MyShoppingApp.model;

public class CategoryPOJO {

    private int id;
    private String category;
    private String icon;
    private String wallpaper;
    private boolean status;

    public CategoryPOJO(int id, String category, String icon, String wallpaper, boolean status) {
        this.id = id;
        this.category = category;
        this.icon = icon;
        this.wallpaper = wallpaper;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
