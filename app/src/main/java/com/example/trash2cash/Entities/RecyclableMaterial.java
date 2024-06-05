package com.example.trash2cash.Entities;


import android.graphics.Color;

import java.io.Serializable;

public class RecyclableMaterial implements Serializable {
    private final String type;
    private int exp;
    private int rewardAmount;
    private final String image;
    private final int colour;

    public RecyclableMaterial(String type, int exp, int rewardAmount, String image, String colour) {
        this.type = type;
        this.exp = exp;
        this.rewardAmount = rewardAmount;
        this.image = image;
        this.colour = Color.parseColor(colour);
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public int getExp() {
        return this.exp;
    }

    public int getColour() { return this.colour; }

    public int getRewardAmount(){
        return this.rewardAmount;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setRewardAmount(int rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public boolean isOfType(String type) {
        return this.type.equals(type);
    }
}
