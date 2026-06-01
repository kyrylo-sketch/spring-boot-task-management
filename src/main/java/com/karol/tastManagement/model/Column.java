package com.karol.tastManagement.model;

import java.time.LocalDateTime;

public class Column {
    private String name;
    private int order;

    public Column() {}

    public Column(String name, int order) {
        this.name = name;
        this.order = order;
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
