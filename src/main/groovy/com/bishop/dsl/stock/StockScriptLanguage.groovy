package com.bishop.dsl.stock

class StockScriptLanguage {

    def static shares = {}
    def static price = {}
    def static value = {}
    def static of = {}

    def static sell(shareCount) {
        [shares: { stock ->
            println "Selling $shareCount shares of ${stock.name}"
            stock.shares -= shareCount
        }]
    }

    def static buy(shareCount) {
        [shares: { stock ->
            println "Buying $shareCount shares of ${stock.name}"
            stock.shares += shareCount
        }]
    }

    def static show(what) {
        if (what == shares) {
            [of: { stock -> println "You have ${stock.shares} shares of ${stock.name}" }]
        }
        else if (what == price) {
            [of: { stock -> println "The price of ${stock.name} is ${stock.price}" }]
        }
        else if (what == value) {
            [of: { stock -> println "The total price of your shares of ${stock.name} is ${new StockPrice(number: stock.shares * stock.price.value, unit: stock.price.unit)}"}]
        }
    }

    def static when(stock) {
        [under: { Number price ->
            [then: { closure ->
                if (stock.price.value < price) {
                    closure.delegate = this
                    closure()
                }
            }]
        },
         over: { Number price ->
             [then: { closure ->
                 if (stock.price.value > price) {
                     closure.delegate = this
                     closure()
                 }
             }]
         }]
    }

    def static total(what) {
        [of: { stock -> stock.shares }]
    }

}
