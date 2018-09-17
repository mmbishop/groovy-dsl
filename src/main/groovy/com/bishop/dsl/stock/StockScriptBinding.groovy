package com.bishop.dsl.stock

class StockScriptBinding extends Binding {

    private Stock axp
    private Stock aapl
    private Stock ba
    private Stock dis
    private Map<String, Stock> stockMap

    public void initialize() {
        axp = createStock("American Express", 100)
        aapl = createStock("Apple", 50)
        ba = createStock("Bank of America", 40)
        dis = createStock("Disney", 30)
        setVariable("axp", axp)
        setVariable("aapl", aapl)
        setVariable("ba", ba)
        setVariable("dis", dis)
        stockMap = ["axp": axp, "aapl": aapl, "ba": ba, "dis": dis]
    }

    public void updateStock(String abbr, StockValue value) {
        stockMap[abbr].value = value
    }

    private Stock createStock(String name, int shares) {
        Stock stock = new Stock()
        stock.setName(name)
        stock.setShares(shares)
        return stock
    }

}
