package com.bishop.dsl.stock

import static com.bishop.dsl.stock.StockScriptLanguage.*

class SLExample {

    public static void main(String[] args) {
        use(CurrencyCategory) {
            def axp = new Stock(name: "American Express", value: 108.66.usd, shares: 100)
            def aapl = new Stock(name: "Apple", value: 226.41.usd, shares: 50)
            def ba = new Stock(name: "Bank of America", value: 355.46.usd, shares: 40)
            def dis = new Stock(name: "Disney", value: 110.07.eu, shares: 30)
            show price of dis
            show price of axp
            show shares of axp
            buy 20 shares axp
            show shares of axp
            sell 30 shares axp
            show shares of axp
            when aapl under 300 then {
                buy 50 shares aapl
                show shares of aapl
            }
            when ba over 300 then {
                def sharesOfBA = total shares of ba
                sell (sharesOfBA / 2) shares ba
                show shares of ba
                show value of ba
            }
        }

    }

}
