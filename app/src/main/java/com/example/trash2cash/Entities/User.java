package com.example.trash2cash.Entities;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class User{
    private String name;
    private int id, rewardPoints;
    private float level;
    private String image;

    //Approved Requests
    private ArrayList<String> approvedRequest = new ArrayList<>();
    //Amount of materials, plastic, glass etc
    private HashMap<String, Integer> materials_amount;

    //Amount of specific items, plastic bag, plastic box, glass bottle e.t.c
    private HashMap<String, HashMap<String,Integer>> items_amount;
    private Map<Integer, Request> userRequestList;

    //Constructor
    public User(String name, int id, float level, int rewardPoints/*, String image*/) {
        this.name = name;
        this.id = id;
        // this.image = image;
        materials_amount = new HashMap<>();
        items_amount = new HashMap<>();
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

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void addRewardPoints(float rewardPoints){
        this.rewardPoints += rewardPoints;
    }

    public void setRewardPoints(int p) {rewardPoints = p;}

    public float getLevel() {
        return level;
    }

    public String getImage() {
        return image;
    }

    public void storeMaterialsItemsAmounts() {

    }

    public int getMaterialAmount(String material) {
        return materials_amount.get(material);
    }

    public void putMaterials_and_Amounts(String mat, int amount) {
        materials_amount.put(mat, amount);
    }

    public HashMap<String, Integer> getMaterials_and_Amounts() {
        return materials_amount;
    }

    public int calculateTotalMaterialsAmount() {
        int totalAmount = 0;

        for (String key : materials_amount.keySet()) {
            Integer value = materials_amount.get(key);
            totalAmount += value;
        }

        return totalAmount;
    }

    public float calculateMaterialAmountPercentage(String mat, int totalAmount) {
        float perA = (getMaterialAmount(mat) / (float)totalAmount) * 100;
        float perB = Math.round(perA);

        return (float)perB;
    }

    public int getItemAmount(String material, String item) {
        return items_amount.get(material).get(item);
    }

    public void putItems_and_Amounts(String material, HashMap<String,Integer> m) {
        items_amount.put(material,m);

    }

    public HashMap<String, HashMap<String,Integer>>getItems_and_Amounts() {
        return items_amount;
    }


    public float calculateItemAmountPercentage(String material,String item, int totalAmount) {
        float perA = (getItemAmount(material,item) / (float)totalAmount) * 100;
        float perB = Math.round(perA);

        return perB;
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

