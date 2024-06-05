package com.example.trash2cash.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String name;
    private int id;
    private float level;
    private float rewardPoints;
    private Map<Integer, Request> userRequestList;
    private RewardList rewardList;

    public User(String name, int id, float level, float rewardPoints) {
        this.name = name;
        this.id = id;
        this.level = level;
        this.rewardPoints = rewardPoints;
        userRequestList = new HashMap<>();
        this.rewardList = new RewardList();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public float getLevel() {
        return level;
    }

    public float getRewardPoints(){
        return rewardPoints;
    }

    public void addRewardPoints(float rewardPoints){
        this.rewardPoints += rewardPoints;
    }

    public Map<Integer, Request> getRecyclableItemList() {
        return userRequestList;
    }

    public void addRequest(Request request){
        userRequestList.put(request.getId(), request);
    }

    public void alterStatus(Request request, RequestStatus status){
        userRequestList.get(request.getId()).setRequestStatus(status);
    }

    public void addLevel(float level){
        this.level += level;
    }

    public void addReward(Reward reward) { this.rewardList.add(reward); }

    public RewardList getRewardList() {
        return this.rewardList;
    }

    public boolean hasReward(Reward reward) {
        for(Reward r : this.rewardList){
            if(reward.equals(r)) return true;
        }
        return false;
    }
}
