package com.example.trash2cash;

import java.util.ArrayList;
import java.util.HashMap;

public class User{
    private String name, id;
    private int points, level;
    private String image;

    //anti gia String mallon MaterialType
    //Amount of materials, plastic, glass,
    private ArrayList<String> approvedItems = new ArrayList<>();
    private HashMap<String, Integer> materials_amount;

    //Amout of specific items, plastic bag, plastic box, glass bottle e.t.c
    private HashMap<String, HashMap<String,Integer>> items_amount;

    //Constructor
    public User(String name, String id, int level, String image) {
        this.name = name;
        this.id = id;
        this.image = image;
        materials_amount = new HashMap<>();
        items_amount = new HashMap<>();
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int p) {points = p;}

    public int getLevel() {
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

}
