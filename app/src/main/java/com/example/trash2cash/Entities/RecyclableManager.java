package com.example.trash2cash.Entities;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecyclableManager {

    private static Map<Integer, User> users;

    private static int requestId = 0;

    public User activeUser = null;

    private static RecyclableManager instance;

    private static HashMap<String, RecyclableMaterial> recyclableItems;

    private int recyclableItemId = 0;

    private RecyclableManager() {
        users = new HashMap<>();
        initilizeMaterials();
    }

    public static void initilizeMaterials() {
        recyclableItems = new HashMap<>();
        for (RecyclableMaterial material : new RecyclableMaterialTypes()) {
            recyclableItems.put(material.getType(), material);
        }
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public static RecyclableManager getRecyclableManager(){
        if(instance == null){
            instance = new RecyclableManager();
        }
        initilizeMaterials();
        return instance;
    }

    public Request addRequest(RecyclableItem recyclableItem, int user_id, RequestStatus status){
        Request request = null;
        try {
            Integer id = new OkHttpHandler().addRequest(user_id, recyclableItem.getId(), status);
            request = new Request(recyclableItem, status, id, user_id);
            User user = users.get(user_id);
            assert user != null;
            user.addRequest(request);
            Admin.getAdmin().updateRecyclableItemRequest();
            requestId++;
        } catch (NullPointerException e){
            System.err.println("This user with user id does not exist!");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        int amount = recyclableItem.getMaterial().getRecycleAmount();
        User user = users.get(user_id);
        assert user != null;

        user.fillMaterialAmounts();
        int material_amount = user.getMaterialAmount(recyclableItem.getMaterial().getType());

        if (material_amount % amount == 0) {
            float level = (float) recyclableItem.getMaterial().getExp() / (100* ((float) 1.25));
            user.addLevel(level);
            user.addRewardPoints(recyclableItem.getMaterial().getRewardAmount());
            new OkHttpHandler().updateUser(user);
        }
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
                image = R.drawable.card_board;
                break;
        }

        return new RecyclableItem(material, type, image, recyclableItemId++);
    }

    public RecyclableMaterial getRecyclableMaterial(String name) {
        return recyclableItems.get(name);
    }

    public User getUser() {
        return activeUser;
    }

    public void setActiveUser(User user){
        activeUser = user;
        users.put(user.getId(), user);
    }

    public User getUserById(int id){
        return users.get(id);
    }
}
