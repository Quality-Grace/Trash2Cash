package com.example.trash2cash;

import java.util.ArrayList;
import java.util.Objects;

public class RewardList extends ArrayList<Reward> {
    public RewardList(){
        add(new Reward(10, 10, "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.glass_type));
        add(new Reward(10, 10, "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.glass_type));
        add(new Reward(10, 10, "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.glass_type));
        add(new Reward(10, 10, "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.glass_type));
    }
}
