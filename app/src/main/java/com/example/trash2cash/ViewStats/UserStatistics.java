package com.example.trash2cash.ViewStats;

public class UserStatistics {
    private int id;
    private float level;
    private float rewardPoints;

    public UserStatistics(int id, float level, float rewardPoints) {
        this.id = id;
        this.level = level;
        this.rewardPoints = rewardPoints;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public float getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(float rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
