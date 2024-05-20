package com.example.trash2cash.Entities;

import android.media.Image;

public class RecyclableItem {

    private final RecyclableMaterial material;

    private final int id;

    private final RecyclableItemType type;

    private final Image image;

    public RecyclableItem(RecyclableMaterial material, RecyclableItemType type, Image image, int id) {
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

    public Image getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}
