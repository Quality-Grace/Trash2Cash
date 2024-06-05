package com.example.trash2cash.Entities;

import java.io.Serializable;

public enum RecyclableItemType implements Serializable {
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

    public static RecyclableItemType fromString(String text) {
        for (RecyclableItemType itemType : RecyclableItemType.values()) {
            if (itemType.itemType.equalsIgnoreCase(text)) {
                return itemType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
