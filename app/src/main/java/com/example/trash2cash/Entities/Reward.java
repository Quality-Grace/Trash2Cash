package com.example.trash2cash.Entities;

import java.util.UUID;

public class Reward {
    private int cost;
    private int level;
    private final String icon;
    private String title;
    private final String code;

    public Reward(String title, int cost, int level, String icon, String code){
        this.cost = cost;
        this.level = level;
        this.icon = icon;
        this.title = title;
        this.code = code;
    }

    public Reward(String title, int cost, int level, String icon) {
        this.cost = cost;
        this.level = level;
        this.icon = icon;
        this.title = title;
        this.code = UUID.randomUUID().toString();
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

    public void setTitle(String title) { this.title = title; }

    public boolean equals(Reward r) {
        return this.cost == r.cost &&
                this.level == r.level &&
                this.title.equals(r.title) &&
                this.icon.equals(r.icon) &&
                this.code.equals(r.code);
    }

    public String getCode() {
        return this.code;
    }
}
