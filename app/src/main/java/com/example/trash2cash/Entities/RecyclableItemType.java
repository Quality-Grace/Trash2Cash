package com.example.trash2cash.Entities;

public enum RecyclableItemType {
    BAG("BAG"),
    BOTTLE("BOTTLE"),
    CAN("CAN"),
    BOX("BOX"),
    CARD_BOARD("CARD_BOARD");

    private final String itemType;

    RecyclableItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }
}
