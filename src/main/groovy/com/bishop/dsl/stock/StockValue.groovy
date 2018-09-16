package com.bishop.dsl.stock

class StockValue {
    def number
    def unit

    def getValue() {
        return number
    }

    def getUnit() {
        return unit
    }

    String toString() {
        switch (unit) {
            case "usd":
                return "\$$number"
            case "eu":
                return "\u20ac$number"
        }
    }
}
