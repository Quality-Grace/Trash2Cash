package com.example.trash2cash.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Admin {

    private final String name = "Admin";

    private static Admin instance;

    private final Map<Integer, List<Request>> recyclableRequests;

    public Map<Integer, List<Request>> getRecyclableRequests() {
        return recyclableRequests;
    }

    private Admin() {
        this.recyclableRequests = new HashMap<>();
    }

    public static Admin getAdmin(){
        if(instance==null){
            instance = new Admin();
            return instance;
        }
        return instance;
    }

    public void approve(int user_id, Request request) {
        try {
            Objects.requireNonNull(recyclableRequests.get(user_id)).remove(request);
            RecyclableManager.getRecyclableManager().alterRequest(request, user_id, RequestStatus.APPROVED);
            RecyclableManager.getRecyclableManager().addPoints(request.getRequestItem(), user_id);
        } catch (NullPointerException e){
            System.err.println("This user or request doesn't exist");
        }
    }

    public void reject(int user_id, Request request) {
        try {
            Objects.requireNonNull(recyclableRequests.get(user_id)).remove(request);
            RecyclableManager.getRecyclableManager().alterRequest(request, user_id, RequestStatus.REJECTED);
        } catch (NullPointerException e){
            System.err.println("This user or recyclableItem doesn't exist");
            e.printStackTrace();
        }
    }

    public void addRecyclableItemRequest(Request request, int user_id) {
        if(recyclableRequests.containsKey(user_id))
            Objects.requireNonNull(recyclableRequests.get(user_id)).add(request);
        else {
            List<Request> recyclableItems = new ArrayList<>();
            recyclableItems.add(request);
            recyclableRequests.put(user_id, recyclableItems);
        }
    }

}
