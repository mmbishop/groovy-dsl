package com.bishop.dsl.stock

import groovy.json.JsonSlurper
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

class StockScriptRunner {

    void runScript(String scriptFileName, String dataFileName) {
        def script = new File(scriptFileName)
        def binding = new StockScriptBinding()
        binding.initialize()
        def configuration = new CompilerConfiguration()
        def importCustomizer = new ImportCustomizer()
        importCustomizer.addStaticStars("com.bishop.dsl.stock.StockScriptLanguage")
        configuration.addCompilationCustomizers(importCustomizer)
        configuration.scriptBaseClass = Script.class.name
        def shell = new GroovyShell(binding, configuration)
        List<List<StockDataValue>> stockData = readStockData(dataFileName)
        stockData.each { stockList ->
            stockList.each { stock ->
                binding.updateStock(stock.stockAbbr, new StockValue(number: stock.value, unit: "usd"))
            }
            shell.evaluate(script)
            println "----------"
        }
    }

    private List<List<StockDataValue>> readStockData(String dataFileName) {
        List<List<StockDataValue>> stockData = new ArrayList<>()
        String data = getClass().getResource(dataFileName).text
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(data)
        object.each { record ->
            List<StockDataValue> stockValueList = new ArrayList<>()
            record.each { stock ->
                StockDataValue sdv = new StockDataValue()
                sdv.setStockAbbr(stock["name"])
                sdv.setValue(stock["value"])
                stockValueList << sdv
            }
            stockData << stockValueList
        }
        return stockData
    }

    def static void main(String[] args) {
        new StockScriptRunner().runScript(args[0], args[1])
    }

}
