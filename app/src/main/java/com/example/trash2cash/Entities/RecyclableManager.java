package com.example.trash2cash.Entities;

import java.util.HashMap;
import java.util.Map;

public class RecyclableManager {

    private Map<Integer, User> users;

    private static int requestId = 0;

    private static RecyclableManager instance;

    private RecyclableManager() {
        users = new HashMap<>();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public static RecyclableManager getRecyclableManager(){
        if(instance == null){
            instance = new RecyclableManager();
        }
        return instance;
    }

    public void addRequest(RecyclableItem recyclableItem, int user_id, RequestStatus status){
        try {
            users.get(user_id).addRequest(new Request(recyclableItem, status, requestId));
            requestId++;
        } catch (NullPointerException e){
            System.err.println("This user with user id does not exist!");
        }
    }

    public void addUser(User user){
        users.put(user.getId(), user);
    }

    public void addRecyclableItemRequest(Request recyclableItem, int user_id){
        Admin.getAdmin().addRecyclableItemRequest(recyclableItem, user_id);
    }
    public void addPoints(RecyclableItem recyclableItem, int user_id) {
        float level = recyclableItem.getMaterial().getExp();
        users.get(user_id).addLevel(level);
    }

    public void alterRequest(Request requestItem, int user_id, RequestStatus approved) {
        users.get(user_id).alterStatus(requestItem, approved);
    }
}
