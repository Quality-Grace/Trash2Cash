package com.example.trash2cash.Entities;

import com.example.trash2cash.DB.OkHttpHandler;

import java.util.ArrayList;

public class RecyclableMaterialTypes extends ArrayList<RecyclableMaterial> {
    public RecyclableMaterialTypes() {
        String url= OkHttpHandler.getPATH()+"populateRecyclableMaterialTypes.php";
        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            this.addAll(okHttpHandler.populateRecyclableMaterialTypes(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTypes(){
        ArrayList<String> typesList = new ArrayList<>();
        for(RecyclableMaterial material : this){
            typesList.add(material.getType());
        }

        return typesList;
    }

    public RecyclableMaterial getRecyclableMaterial(String type) {
        for(RecyclableMaterial material : this){
            if(material.isOfType(type)) return material;
        }
        return null;
    }
}
