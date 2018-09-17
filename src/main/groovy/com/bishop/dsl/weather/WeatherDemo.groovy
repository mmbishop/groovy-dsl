package com.bishop.dsl.weather

class WeatherUnit {
    def number
    def unit
    String toString() {
        "$number$unit"
    }
}

@Category(Number)
class UnitCategory {
    WeatherUnit getDegF() {
        new WeatherUnit(number: this, unit: "F")
    }
    WeatherUnit getDegC() {
        new WeatherUnit(number: this, unit: "C")
    }
    WeatherUnit getMph() {
        new WeatherUnit(number: this, unit: "mph")
    }
    WeatherUnit getKph() {
        new WeatherUnit(number: this, unit: "kph")
    }
}

use (UnitCategory) {
    def temperatureValues = ["Austin": 68.degF, "Houston": 70.degF, "Tokyo": 15.degC]
    def windSpeedValues = ["Austin": 4.mph, "Houston": 7.mph, "Tokyo": 10.kph]
    def windDirectionValues = ["Austin": "SW", "Houston": "SE", "Tokyo": "NW"]
    def conditionValues = ["Austin": "Rain", "Houston": "Cloudy", "Tokyo": "Sunny"]
    temperature = { place -> temperatureValues[place] }
    wind = { place -> windSpeedValues[place] }
    direction = { place -> windDirectionValues[place] }
    conditions = { place -> conditionValues[place] }
}

is = {}
are = {}

def static what(linkingVerb) {
    [the: { thing ->
        [at: { place -> println "the $thing at $place is ${thing(place)}" }]
    }]
}

def static the(thing) {
    [at: { place -> thing(place)}]
}

what is the temperature at "Austin"
what is the wind at "Houston"
what are the conditions at "Tokyo"

println the temperature at "Houston"
