package com.bishop.dsl.stock

class Stock {
    private String name
    private StockValue value
    private int shares

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    StockValue getValue() {
        return value
    }

    void setValue(StockValue value) {
        this.value = value
    }

    int getShares() {
        return shares
    }

    void setShares(int shares) {
        this.shares = shares
    }
}