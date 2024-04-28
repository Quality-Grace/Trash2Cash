package com.example.trash2cash;

import java.util.ArrayList;

public class RecyclableMaterialTypes extends ArrayList<RecyclableMaterial> {

    // TODO
    // Add a db instead
    public RecyclableMaterialTypes() {
        add(new RecyclableMaterial("Paper", 10, 1, R.drawable.paper_type));
        add(new RecyclableMaterial("Glass", 10, 1, R.drawable.glass_type));
        add(new RecyclableMaterial("Metal", 10, 1, R.drawable.metal_type));
        add(new RecyclableMaterial("Plastic", 10, 1, R.drawable.plastic_type));
        add(new RecyclableMaterial("Other", 10, 1, R.drawable.other_type));
    }
}
