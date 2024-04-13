package com.example.backend.DTO;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;

public class ItemIdWrapper implements Converter<String, ItemIdWrapper> {
    private UUID id;

    public ItemIdWrapper() {
    }

    public ItemIdWrapper(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdWrapper{" +
                "id=" + id +
                '}';
    }

    @Override
    public ItemIdWrapper convert(String source) {
        return new ItemIdWrapper(UUID.fromString(source));
    }
}