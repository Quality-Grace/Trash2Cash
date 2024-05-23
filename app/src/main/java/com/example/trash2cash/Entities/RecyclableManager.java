package com.example.trash2cash.Entities;

import android.media.Image;

import com.example.trash2cash.R;

import java.util.HashMap;
import java.util.Map;

public class RecyclableManager {

    private Map<Integer, User> users;

    private static int requestId = 0;

    public User activeUser = null;

    private static RecyclableManager instance;

    private HashMap<String, RecyclableMaterial> recyclableItems;

    private int recyclableItemId = 0;

    private RecyclableManager() {
        RecyclableMaterial paper = new RecyclableMaterial(MaterialType.PAPER.getMaterialType(), 100, 10, R.drawable.paper_type);
        RecyclableMaterial plastic = new RecyclableMaterial(MaterialType.PLASTIC.getMaterialType(), 100, 10, R.drawable.plastic_type);
        RecyclableMaterial glass = new RecyclableMaterial(MaterialType.GLASS.getMaterialType(), 100, 10, R.drawable.glass_type);
        RecyclableMaterial metal = new RecyclableMaterial(MaterialType.ALUMINUM.getMaterialType(),  100, 10, R.drawable.metal_type);

        recyclableItems = new HashMap<>();
        recyclableItems.put("PAPER", paper);
        recyclableItems.put("PLASTIC", plastic);
        recyclableItems.put("GLASS", glass);
        recyclableItems.put("ALUMINUM", metal);

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

    public Request addRequest(RecyclableItem recyclableItem, int user_id, RequestStatus status){
        Request request = null;
        try {
            request = new Request(recyclableItem, status, requestId, user_id);
            User user = users.get(user_id);
            assert user != null;
            user.addRequest(request);
            addRecyclableItemRequest(request, user_id);
            requestId++;
        } catch (NullPointerException e){
            System.err.println("This user with user id does not exist!");
        }
        return request;
    }

    public void addUser(User user){
        users.put(user.getId(), user);
    }

    public void addRecyclableItemRequest(Request recyclableItem, int user_id){
        Admin.getAdmin().addRecyclableItemRequest(recyclableItem, user_id);
    }
    public void addPoints(RecyclableItem recyclableItem, int user_id) {
        float level = recyclableItem.getMaterial().getExp();
        User user = users.get(user_id);
        assert user != null;
        user.addLevel(level);
    }

    public void alterRequest(Request requestItem, int user_id, RequestStatus approved) {
        User user = users.get(user_id);
        assert user != null;
        user.alterStatus(requestItem, approved);
    }

    public RecyclableItem createRecyclableItem(RecyclableMaterial material, RecyclableItemType type) {
        int image = R.drawable.ic_launcher_foreground;
        switch (type){
            case BAG:
                image = R.drawable.bag;
                break;
            case BOX:
                image = R.drawable.box;
                break;
            case BOTTLE:
                image = R.drawable.bottle;
                break;
            case CAN:
                image = R.drawable.can;
                break;
            case CARD_BOARD:
                image = R.drawable.ic_launcher_foreground;
                break;
        }
        int id = recyclableItemId++;
        return new RecyclableItem(material, type, image, id);
    }

    public RecyclableMaterial getRecyclableMaterial(String name) {
        return recyclableItems.get(name);
    }

    public User getUser() {
        if(activeUser==null){
            activeUser = new User("User", 0, 0, 0);
            addUser(activeUser);
            return activeUser;
        }
        return activeUser;
    }

    public User getUserById(int id){
        return users.get(id);
    }
}
