package com.example.trash2cash;

public class Reward {
    private int cost;
    private int level;
    private int icon;

    public Reward(int cost, int level, int icon){
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

    public int getIcon() {
        return icon;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setLevel(int level) { this.level = level; }

    public void setIcon(int icon) { this.icon = icon; }
}
