package com.example.trash2cash.Entities;

public enum MaterialType {
    PAPER("Paper"),
    PLASTIC("Plastic"),
    METAL("Metal"),
    GLASS("Glass");

    private final String itemType;

    MaterialType(String itemType) {
        this.itemType = itemType;
    }

    public String getMaterialType() {
        return itemType;
    }

}
