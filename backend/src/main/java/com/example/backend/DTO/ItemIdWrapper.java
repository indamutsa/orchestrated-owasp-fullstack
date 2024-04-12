package com.example.backend.DTO;

import java.util.UUID;

public class ItemIdWrapper {
    private UUID itemId;

    public ItemIdWrapper() {
    }

    public ItemIdWrapper(UUID itemId) {
        this.itemId = itemId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "ItemIdWrapper{" +
                "itemId=" + itemId +
                '}';
    }
}