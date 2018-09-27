package com.bishop.dsl.stock

class Stock {
    private String name
    private StockPrice price
    private int shares

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    StockPrice getPrice() {
        return price
    }

    void setPrice(StockPrice price) {
        this.price = price
    }

    int getShares() {
        return shares
    }

    void setShares(int shares) {
        this.shares = shares
    }
}