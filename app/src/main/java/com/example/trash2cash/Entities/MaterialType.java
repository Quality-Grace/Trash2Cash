package com.example.trash2cash.Entities;

public enum MaterialType {
    PAPER("PAPER"),
    PLASTIC("PLASTIC"),
    ALUMINUM("ALUMINUM"),
    GLASS("GLASS");

    private final String itemType;

    MaterialType(String itemType) {
        this.itemType = itemType;
    }

    public String getMaterialType() {
        return itemType;
    }
}
