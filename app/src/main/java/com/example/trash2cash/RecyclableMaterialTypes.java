package com.example.trash2cash;

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
}
