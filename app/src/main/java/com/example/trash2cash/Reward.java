package com.example.trash2cash;

public class Reward {
    private int cost;
    private int level;
    private String icon;

    public Reward(int cost, int level, String icon){
        this.cost = cost;
        this.level = level;
        this.icon = icon;
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

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setLevel(int level) { this.level = level; }

    public void setIcon(String icon) { this.icon = icon; }
}
