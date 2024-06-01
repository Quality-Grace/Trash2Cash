package com.example.trash2cash.Entities;

public class Reward {
    private int cost;
    private int level;
    private String icon;
    private String title;

    public Reward(String title, int cost, int level, String icon){
        this.cost = cost;
        this.level = level;
        this.icon = icon;
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setLevel(int level) { this.level = level; }

    public void setIcon(String icon) { this.icon = icon; }

    public void setTitle(String title) { this.title = title; }
}
