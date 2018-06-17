package com.bishop.dsl.weather

class OpenWeatherMapClient implements WeatherClient {

    double getTemperature() {
        return 70
    }

    double getWindSpeed() {
        return 10
    }

    WindDirectionType getWindDirection() {
        return WindDirectionType.SE
    }

    double getHumidity() {
        return 60
    }

    String getConditions() {
        return "Cloudy"
    }

}
