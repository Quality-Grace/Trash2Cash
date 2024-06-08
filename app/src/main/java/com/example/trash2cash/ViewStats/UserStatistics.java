package com.example.trash2cash.ViewStats;

public class UserStatistics {
    private String userName;
    private int totalRecycledItems;
    private float userLevel;
    private float rewardPoints;

    public UserStatistics(String userName, int totalRecycledItems, float userLevel, float rewardPoints) {
        this.userName = userName;
        this.totalRecycledItems = totalRecycledItems;
        this.userLevel = userLevel;
        this.rewardPoints = rewardPoints;
    }

    public String getUserName() {
        return userName;
    }

    public int getTotalRecycledItems() {
        return totalRecycledItems;
    }

    public float getUserLevel() {
        return userLevel;
    }

    public float getRewardPoints() {
        return rewardPoints;
    }
}
