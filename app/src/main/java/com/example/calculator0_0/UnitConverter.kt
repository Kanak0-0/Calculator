package com.example.calculator0_0

class UnitConverter {
    companion object {
        // Length conversions
        fun metersToFeet(meters: Double): Double = meters * 3.28084
        fun feetToMeters(feet: Double): Double = feet / 3.28084
        fun kilometersToMiles(km: Double): Double = km * 0.621371
        fun milesToKilometers(miles: Double): Double = miles / 0.621371

        // Weight conversions
        fun kilogramsToPounds(kg: Double): Double = kg * 2.20462
        fun poundsToKilograms(pounds: Double): Double = pounds / 2.20462
        fun gramsToOunces(grams: Double): Double = grams * 0.035274
        fun ouncesToGrams(ounces: Double): Double = ounces / 0.035274

        // Temperature conversions
        fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9/5) + 32
        fun fahrenheitToCelsius(fahrenheit: Double): Double = (fahrenheit - 32) * 5/9
        fun celsiusToKelvin(celsius: Double): Double = celsius + 273.15
        fun kelvinToCelsius(kelvin: Double): Double = kelvin - 273.15

        // Area conversions
        fun squareMetersToSquareFeet(sqMeters: Double): Double = sqMeters * 10.7639
        fun squareFeetToSquareMeters(sqFeet: Double): Double = sqFeet / 10.7639

        // Volume conversions
        fun litersToGallons(liters: Double): Double = liters * 0.264172
        fun gallonsToLiters(gallons: Double): Double = gallons / 0.264172
        fun millilitersToFluidOunces(ml: Double): Double = ml * 0.033814
        fun fluidOuncesToMilliliters(flOz: Double): Double = flOz / 0.033814
    }
}