package com.example.trash2cash.Entities;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String name;
    private int id;
    private float level;

    private float rewardPoints;

    private Map<Integer, Request> userRequestList;



    public User(String name, int id, float level, float rewardPoints) {
        this.name = name;
        this.id = id;
        this.level = level;
        this.rewardPoints = rewardPoints;
        userRequestList = new HashMap<>();
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

}
