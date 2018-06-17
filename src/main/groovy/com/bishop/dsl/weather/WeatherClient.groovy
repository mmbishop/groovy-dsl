package com.bishop.dsl.weather

interface WeatherClient {

    enum WindDirectionType { N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW }

    double getTemperature()

    double getWindSpeed()

    WindDirectionType getWindDirection()

    double getHumidity()

    String getConditions()

}
