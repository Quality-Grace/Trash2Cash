package com.example.trash2cash.Entities;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class User{
    private String name, image;
    private int id, rewardPoints;
    private float level;

    //Approved Requests
    private ArrayList<Request> approvedRequest = new ArrayList<>();
    //Amount of materials, plastic, glass etc
    private HashMap<String, Integer> materials_amount;
    //Amount of specific items, plastic bag, aluminum box, glass bottle e.t.c
    private HashMap<String, HashMap<String,Integer>> items_amount;

    //Map with all the user's requests
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

    //Method to find only the approved requests
    public void storeApprovedRequests() {
        for(int key: userRequestList.keySet()) {
            Request req = userRequestList.get(key);
            RequestStatus status = req.getRequestStatus();
            if(status.getRequestStatus().equals("APPROVED")) {
                approvedRequest.add(req);
            }
        }
    }

    //Method to isolate the amounts of materials and specific items
    public void storeMaterialsAndItemsAmounts() {

        //temp maps for items and their amount only
        HashMap<String, Integer> plasticmap = new HashMap<>();
        HashMap<String, Integer> papermap = new HashMap<>();
        HashMap<String, Integer> aluminummap = new HashMap<>();
        HashMap<String, Integer> glassmap = new HashMap<>();


        for(Request req: approvedRequest) {
            RecyclableItem recitem = req.getRequestItem();
            RecyclableMaterial material = recitem.getMaterial();
            RecyclableItemType itemType = recitem.getType();

            //store the materials and their amounts after the calculation.
            materials_amount.put(material.getType(), materials_amount.getOrDefault(material.getType(), 0) + 1);

            //Seperate the items of the materials, calculate their amount and store them in temp hashMaps
            if(material.getType().equals("PLASTIC")) {
                plasticmap.put(itemType.getItemType(),plasticmap.getOrDefault(itemType.getItemType(),0)+1);
            } else if (material.getType().equals("GLASS")) {
                glassmap.put(itemType.getItemType(),glassmap.getOrDefault(itemType.getItemType(),0)+1);
            }else if(material.getType().equals("PAPER")) {
                papermap.put(itemType.getItemType(),papermap.getOrDefault(itemType.getItemType(),0)+1);
            } else {
                aluminummap.put(itemType.getItemType(),aluminummap.getOrDefault(itemType.getItemType(),0)+1);
            }
        }

        //store the materials and their items with their amounts in the items_amounts hashMap
        items_amount.put("PAPER",papermap);
        items_amount.put("GLASS", glassmap);
        items_amount.put("PLASTIC", plasticmap);
        items_amount.put("ALUMINUM",aluminummap);
    }


    public int getMaterialAmount(String material) {
        return materials_amount.get(material);
    }

    //temp method
    public void putMaterials_and_Amounts(String mat, int amount) {
        materials_amount.put(mat, amount);
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

    //temp method
    public void putItems_and_Amounts(String material, HashMap<String,Integer> m) {
        items_amount.put(material,m);

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

