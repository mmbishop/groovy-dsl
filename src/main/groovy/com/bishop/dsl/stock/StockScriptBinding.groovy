package com.bishop.dsl.stock

class StockScriptBinding extends Binding {

    private Stock axp
    private Stock aapl
    private Stock ba
    private Stock dis

    public void initialize() {
        axp = createStock("American Express", 100)
        aapl = createStock("Apple", 50)
        ba = createStock("Bank of America", 40)
        dis = createStock("Disney", 30)
        setVariable("axp", axp)
        setVariable("aapl", aapl)
        setVariable("ba", ba)
        setVariable("dis", dis)
    }

    private Stock createStock(String name, int shares) {
        Stock stock = new Stock()
        stock.setName(name)
        stock.setShares(shares)
        return stock
    }

}
