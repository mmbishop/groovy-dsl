package com.bishop.dsl.simulation

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

class SimScriptRunner {

    void runScript(String fileName) {
        def script = new File(fileName)
        def binding = new SimulationScriptBinding()
        binding.initialize()
        def configuration = new CompilerConfiguration()
        def importCustomizer = new ImportCustomizer()
        importCustomizer.addImports("com.bishop.dsl.simulation.Simulation")
        configuration.addCompilationCustomizers(importCustomizer)
        configuration.scriptBaseClass = SimulationScript.class.name
        def shell = new GroovyShell(binding, configuration)
        while (binding.altitude <= 40000) {
            shell.evaluate(script)
            try {
                Thread.sleep(1000)
            }
            catch (InterruptedException e) {
                // Empty catch block
            }
            finally {
                println "altitude = ${binding.getVariable("altitude")}, sv01 = ${binding.getVariable("sv01")}, " +
                        "sv02 = ${binding.getVariable("sv02")}, sv03 = ${binding.getVariable("sv03")}"
                binding.update()
            }
        }
    }

    def static void main(String[] args) {
        new SimScriptRunner().runScript(args[0])
    }

}
