package com.example.calculator0_0

import kotlin.math.*

class ScientificCalculator {
    companion object {
        // Trigonometric functions (input in radians)
        fun sin(x: Double): Double = sin(x)
        fun cos(x: Double): Double = cos(x)
        fun tan(x: Double): Double = tan(x)
        
        // Inverse trigonometric functions
        fun asin(x: Double): Double = asin(x)
        fun acos(x: Double): Double = acos(x)
        fun atan(x: Double): Double = atan(x)
        
        // Hyperbolic functions
        fun sinh(x: Double): Double = sinh(x)
        fun cosh(x: Double): Double = cosh(x)
        fun tanh(x: Double): Double = tanh(x)
        
        // Logarithmic functions
        fun log10(x: Double): Double = log10(x)
        fun ln(x: Double): Double = ln(x)
        fun logBase(x: Double, base: Double): Double = ln(x) / ln(base)
        
        // Power functions
        fun power(base: Double, exponent: Double): Double = base.pow(exponent)
        fun sqrt(x: Double): Double = sqrt(x)
        fun cbrt(x: Double): Double = power(x, 1.0/3.0)
        
        // Other functions
        fun factorial(n: Int): Long {
            if (n < 0) throw IllegalArgumentException("Factorial not defined for negative numbers")
            return if (n <= 1) 1 else n * factorial(n - 1)
        }
        
        fun absolute(x: Double): Double = abs(x)
        
        // Angle conversions
        fun degreesToRadians(degrees: Double): Double = degrees * PI / 180.0
        fun radiansToDegrees(radians: Double): Double = radians * 180.0 / PI
        
        // Constants
        const val PI = kotlin.math.PI
        const val E = kotlin.math.E
    }
}