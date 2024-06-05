package com.example.trash2cash.Entities;

import java.io.Serializable;

public class RecyclableItem implements Serializable {

    private final RecyclableMaterial material;

    private final int id;

    private final RecyclableItemType type;

    private final int image;

    public RecyclableItem(RecyclableMaterial material, RecyclableItemType type, int image, int id) {
        this.material = material;
        this.type = type;
        this.image = image;
        this.id = id;
    }

    public RecyclableMaterial getMaterial() {
        return material;
    }

    public RecyclableItemType getType() {
        return type;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}
