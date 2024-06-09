package com.example.trash2cash.ViewStats;

public class UserStatistics implements Comparable<UserStatistics>{
    private int id;
    private float level;
    private final float rewardPoints;

    private String name;

    public UserStatistics(int id, float level, float rewardPoints, String name) {
        this.id = id;
        this.level = level;
        this.rewardPoints = rewardPoints;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public int compareTo(UserStatistics o) {
        return Float.compare(this.rewardPoints, o.rewardPoints);
    }
}
