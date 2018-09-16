package com.bishop.dsl.stock

@Category(Number)
class CurrencyCategory {
    StockValue getUsd() {
        new StockValue(number: this, unit: "usd")
    }
    StockValue getEu() {
        new StockValue(number: this, unit: "eu")
    }
}
