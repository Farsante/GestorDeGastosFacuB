package com.example.gestordegastosfacub.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String name;
    private boolean needsNumberOfItemsInExpenses;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isNeedsNumberOfItemsInExpenses() {
        return needsNumberOfItemsInExpenses;
    }
}
