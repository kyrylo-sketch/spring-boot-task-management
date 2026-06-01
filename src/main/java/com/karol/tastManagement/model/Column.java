package com.karol.tastManagement.model;

import java.util.UUID;

public class Column {
    private String id;
    private String name;
    private int order;

    public Column() {}

    public Column(String name, int order) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
