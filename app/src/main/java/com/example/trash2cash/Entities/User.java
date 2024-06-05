package com.example.trash2cash.Entities;

import com.example.trash2cash.DB.OkHttpHandler;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String email;
    private String name;
    private int id;
    private float level;
    private float rewardPoints;
    private Map<Integer, Request> userRequestList;
    private RewardList rewardList;
    private String image;
    public User(String email, String name, int id, float level, float rewardPoints, String image, RewardList rewardList) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.level = level;
        this.rewardPoints = rewardPoints;
        this.image = image;
        this.rewardList = rewardList;

        userRequestList = new HashMap<>();
        for (Request r : new OkHttpHandler().takeRequestsByUserId(id)){
            userRequestList.put(r.getId(), r);
        }
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
        for (Request r : new OkHttpHandler().takeRequestsByUserId(id)){
            userRequestList.put(r.getId(), r);
        }
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
