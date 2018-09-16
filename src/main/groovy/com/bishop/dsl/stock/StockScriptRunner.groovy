package com.bishop.dsl.stock

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

class StockScriptRunner {

    void runScript(String fileName) {
        def script = new File(fileName)
        def binding = new StockScriptBinding()
        binding.initialize()
        def configuration = new CompilerConfiguration()
        def importCustomizer = new ImportCustomizer()
        importCustomizer.addStaticStars("com.bishop.dsl.stock.StockScriptLanguage")
        configuration.addCompilationCustomizers(importCustomizer)
        configuration.scriptBaseClass = Script.class.name
        def shell = new GroovyShell(binding, configuration)
        shell.evaluate(script)
    }

    def static void main(String[] args) {
        new StockScriptRunner().runScript(args[0])
    }

}
