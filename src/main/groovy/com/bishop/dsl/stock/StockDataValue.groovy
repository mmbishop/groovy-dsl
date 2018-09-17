package com.bishop.dsl.stock

class StockDataValue {

    private String stockAbbr
    private Number value

    String getStockAbbr() {
        return stockAbbr
    }

    void setStockAbbr(String stockAbbr) {
        this.stockAbbr = stockAbbr
    }

    Number getValue() {
        return value
    }

    void setValue(Number value) {
        this.value = value
    }

}
