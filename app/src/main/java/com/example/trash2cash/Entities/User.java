package com.example.trash2cash.Entities;

import com.example.trash2cash.DB.OkHttpHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String name;
    private int id;
    private float level;
    private float rewardPoints;
    private RewardList rewardList;
    private String image;

    //Approved Requests
    private ArrayList<Request> approvedRequest = new ArrayList<>();
    //Amount of materials, plastic, glass etc
    private HashMap<String, Integer> materials_amount;
    //Amount of specific items, plastic bag, aluminum box, glass bottle e.t.c
    private HashMap<String, HashMap<String,Integer>> items_amount;

    //Map with all the user's requests
    private Map<Integer, Request> userRequestList;

    public User(String email, String name, int id, float level, float rewardPoints, String image, RewardList rewardList) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.level = level;
        this.rewardPoints = rewardPoints;
        this.image = image;
        this.rewardList = rewardList;

        materials_amount = new HashMap<>();
        items_amount = new HashMap<>();

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

    public void setRewardPoints(int p) {rewardPoints = p;}

    public String getImage() {
        return image;
    }

    //Method to find only the approved requests
    public void storeApprovedRequests() {
        approvedRequest.clear();
        for(int key: userRequestList.keySet()) {
            Request req = userRequestList.get(key);
            RequestStatus status = req.getRequestStatus();
            if(status.equals(RequestStatus.APPROVED)) {
                approvedRequest.add(req);
            }
        }
    }

    public void fillMaterialAmounts() {
        storeApprovedRequests();
        materials_amount.clear();
        for (Request req : approvedRequest) {
            RecyclableItem recItem = req.getRequestItem();
            RecyclableMaterial material = recItem.getMaterial();

            //store the materials and their amounts after the calculation.
            if (materials_amount.containsKey(material.getType())) {
                materials_amount.put(material.getType(), materials_amount.get(material.getType()) + 1);
            } else {
                materials_amount.put(material.getType(), 1);
            }

        }
    }

    //Method to isolate the amounts of materials and specific items
    public void storeMaterialsAndItemsAmounts() {
        materials_amount.clear();
        items_amount.clear();

        //temp maps for items and their amount only
        HashMap<String, Integer> plasticMap = new HashMap<>();
        HashMap<String, Integer> paperMap = new HashMap<>();
        HashMap<String, Integer> aluminumMap = new HashMap<>();
        HashMap<String, Integer> glassMap = new HashMap<>();
        HashMap<String, Integer> otherMap = new HashMap<>();

        for(Request req: approvedRequest) {
            RecyclableItem recitem = req.getRequestItem();
            RecyclableMaterial material = recitem.getMaterial();
            RecyclableItemType itemType = recitem.getType();


            //store the materials and their amounts after the calculation.
            if(materials_amount.containsKey(material.getType())){
                materials_amount.put(material.getType(), materials_amount.get(material.getType()) + 1);
            } else {
                materials_amount.put(material.getType(), 1);
            }

            //Seperate the items of the materials, calculate their amount and store them in temp hashMaps
            switch (material.getType()) {
                case "Plastic":
                    if(plasticMap.containsKey(itemType.getItemType())){
                        plasticMap.put(itemType.getItemType(), plasticMap.get(itemType.getItemType()) + 1);
                    } else {
                        plasticMap.put(itemType.getItemType(), 1);
                    }
                    break;
                case "Glass":
                    if(glassMap.containsKey(itemType.getItemType())){
                        glassMap.put(itemType.getItemType(), glassMap.get(itemType.getItemType()) + 1);
                    } else {
                        glassMap.put(itemType.getItemType(), 1);
                    }
                    break;
                case "Paper":
                    if(paperMap.containsKey(itemType.getItemType())){
                        paperMap.put(itemType.getItemType(), paperMap.get(itemType.getItemType()) + 1);
                    } else {
                        paperMap.put(itemType.getItemType(), 1);
                    }
                    break;
                case "Aluminium":
                    if(aluminumMap.containsKey(itemType.getItemType())){
                        aluminumMap.put(itemType.getItemType(), aluminumMap.get(itemType.getItemType()) + 1);
                    } else {
                        aluminumMap.put(itemType.getItemType(), 1);
                    }
                    break;
                case "Other":
                    if(otherMap.containsKey(itemType.getItemType())){
                        otherMap.put(itemType.getItemType(), otherMap.get(itemType.getItemType()) + 1);
                    } else {
                        otherMap.put(itemType.getItemType(), 1);
                    }
                    break;
            }
        }

        //store the materials and their items with their amounts in the items_amounts hashMap
        items_amount.put("Paper",paperMap);
        items_amount.put("Glass", glassMap);
        items_amount.put("Plastic", plasticMap);
        items_amount.put("Aluminium",aluminumMap);
        items_amount.put("Other", otherMap);
    }


    public int getMaterialAmount(String material) {
        return materials_amount.get(material);
    }

    public HashMap<String, Integer> getMaterials_and_Amounts() {
        return materials_amount;
    }

    //Method to calculate the total amount of materials
    public int calculateTotalMaterialsAmount() {
        int totalAmount = 0;

        for (String key : materials_amount.keySet()) {
            Integer value = materials_amount.get(key);
            totalAmount += value;
        }

        return totalAmount;
    }

    //Method to calculate the percentage of each material
    public float calculateMaterialAmountPercentage(String mat) {
        int totalAmount = calculateTotalMaterialsAmount();

        float perA = (getMaterialAmount(mat) / (float)totalAmount) * 100;
        float perB = Math.round(perA);

        return (float)perB;
    }

    public int getItemAmount(String material, String item) {
        return items_amount.get(material).get(item);
    }

    public HashMap<String, HashMap<String,Integer>>getItems_and_Amounts() {
        return items_amount;
    }

    //Method to calculate the percentage of each item
    public float calculateItemAmountPercentage(String material,String item) {
        int totalAmount = getMaterialAmount(material);

        float perA = (getItemAmount(material,item) / (float)totalAmount) * 100;
        float perB = Math.round(perA);

        return perB;
    }

    //Method to find the points and levels required for the next reward
    public int[] getRemainingValues() {
        int[] remainingValues = {0, 0};
        //list sorting to find the first reward that the user does not have
        RewardList availableRewards = new RewardList(OkHttpHandler.getPATH()+"populateRewards.php");
        Collections.sort(availableRewards);

        boolean done = false;
        for (Reward reward : availableRewards) {
            if (!hasReward(reward)) {
                if (reward.getCost() > getRewardPoints()) {
                    remainingValues[0] = reward.getCost();
                    done = true;
                }
                if (reward.getLevel() > getLevel()) {
                    remainingValues[1] = reward.getLevel();
                    done = true;
                }
                if(done) break;
            }
        }

        return remainingValues;
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

    public void addReward(Reward reward) {
        float rewardCost = reward.getCost();
        if(rewardCost <= rewardPoints){
            this.rewardPoints -= rewardCost;
            this.rewardList.add(reward);
        }
    }

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
