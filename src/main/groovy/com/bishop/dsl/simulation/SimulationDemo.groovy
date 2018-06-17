package com.bishop.dsl.simulation

import static com.bishop.dsl.simulation.Simulation.simscript

def mySimScript = simscript {

    double altitude
    double sv01
    double sv02
    double sv03
    double sv04
    double sv05

    when (altitude >= 10000) {
        sv01 = 0
    }

    whenever (sv02 == 1) until (altitude >= 20000) {
        sv03 = 0
    }

    println "altitude = $altitude, sv01 = $sv01, sv02 = $sv02, sv03 = $sv03"

}

mySimScript.run()