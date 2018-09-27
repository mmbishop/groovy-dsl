package com.bishop.dsl.stock

@Category(Number)
class CurrencyCategory {
    StockPrice getUsd() {
        new StockPrice(number: this, unit: "usd")
    }
    StockPrice getEu() {
        new StockPrice(number: this, unit: "eu")
    }
}
