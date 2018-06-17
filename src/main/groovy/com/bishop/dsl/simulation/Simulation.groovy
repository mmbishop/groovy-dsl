package com.bishop.dsl.simulation

class Simulation {

    static checkingStoppedForWhenever = [:]
    static whenHasFired = [:]
    static whenCounter = 0
    static wheneverCounter = 0

    static Binding binding

    static Simulation simscript(@DelegatesTo(Simulation) Closure closure, Binding scriptBinding) {
        binding = scriptBinding
        Simulation s = new Simulation()
        closure.delegate = s
        whenCounter = 0
        wheneverCounter = 0
        closure()
        return s
    }

    def when(boolean cond, @DelegatesTo(Simulation) Closure closure) {
        if (! whenHasFired[whenCounter] && cond) {
            closure.delegate = this
            closure()
            whenHasFired[whenCounter] = true
        }
        whenCounter++
    }

    def whenever(boolean cond) {
        [until: { untilCond, closure ->
            if (! checkingStoppedForWhenever[wheneverCounter]) {
                if (cond && !untilCond) {
                    closure.delegate = this
                    closure()
                }
                else if (untilCond) {
                    checkingStoppedForWhenever[wheneverCounter] = true
                }
            }
            wheneverCounter++
        }]
    }

    def malf(String varName) {
        ["offset": { offset ->
            Malfunction malf = (Malfunction) binding.getVariable(varName + "Malf")
            malf.offset = offset
            malf.active = true
        }]
    }

    def restore(String varName) {
        binding.getVariable(varName + "Malf").active = false
    }

}

