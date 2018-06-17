package com.bishop.dsl.simulation

abstract class SimulationScript extends Script {

    def simscript(Closure closure) {
        Simulation.simscript(closure, this.binding)
    }

}
