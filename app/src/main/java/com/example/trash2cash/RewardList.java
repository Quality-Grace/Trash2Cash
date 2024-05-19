package com.example.trash2cash;

import com.example.trash2cash.DB.OkHttpHandler;

import java.util.ArrayList;

public class RewardList extends ArrayList<Reward> {
    public RewardList(){
        String url= OkHttpHandler.getPATH()+"populateRewards.php";
        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            this.addAll(okHttpHandler.populateRewards(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
