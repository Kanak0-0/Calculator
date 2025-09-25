# Not Your Basic Calculator App

## Description

A versatile calculator application for Android, offering standard arithmetic operations, scientific calculations, and unit conversions. The app also supports voice input for hands-free operation and customizable themes to personalize the user experience.

## Features

*   **Standard Calculator**: For basic arithmetic operations (+, -, ×, ÷).
*   **Scientific Mode**:
    *   Trigonometric functions (sin, cos, tan - accepts input in degrees).
    *   Logarithmic functions (log base 10, natural log).
    *   Square root (√).
    *   Power functions (x², x³ - via appending ^2, ^3).
    *   Pi (π) constant.
*   **Unit Conversion Mode**:
    *   Length: Meters (m) ↔ Feet (ft)
    *   Mass: Kilograms (kg) ↔ Pounds (lb)
    *   Temperature: Celsius (°C) ↔ Fahrenheit (°F)
    *   Volume: Liters (L) ↔ Gallons (gal)
*   **Voice Input**: Speak calculations directly into the app (e.g., "25 plus 17", "what is 5 times 8").
*   **Themes**: Multiple themes to choose from (Default, Dark, Light, Blue, Green).
*   **Input Controls**:
    *   Backspace (⌫) to correct entries.
    *   All Clear (AC) to reset calculations.
*   **Dynamic Keypads**: The keypad layout changes based on the selected mode.

## Usage

1.  **Mode Selection**:
    *   Tap the "Scientific" button to switch to Scientific Mode.
    *   Tap the "Units" button to switch to Unit Conversion Mode.
    *   Tapping the active mode button again will revert to the standard calculator.
2.  **Theme Customization**:
    *   Tap the theme icon (looks like a settings/manage icon) to open the theme menu.
    *   Select your preferred theme. The app will refresh to apply the new theme.
3.  **Voice Input**:
    *   Tap the microphone icon.
    *   When prompted, speak your calculation.
    *   The app will attempt to convert your speech to an expression and calculate the result.
    *   Ensure microphone permission is granted.
4.  **Unit Conversion Input Format**:
    *   When in Unit Conversion mode, tap a conversion button (e.g., "m → ft").
    *   The input field will be pre-filled (e.g., "0 m ft").
    *   Replace the "0" with the value you want to convert.
    *   Press "=".
    *   *Example*: To convert 10 meters to feet: tap "m → ft", edit input to "10 m ft", press "=".
5.  **Scientific Input Format**:
    *   For functions like `sin`, `cos`, `tan`, `log`, `ln`, `sqrt`: tap the function button (e.g., "sin"), then enter the number (e.g., "30"), then press "=". The input will look like "sin 30".
    *   For `x²`: enter the number, tap "x²" (which appends "^2 "), then press "=".

## Technologies Used

*   **Kotlin**: Primary programming language.
*   **Android SDK**: For building the Android application.
*   **ViewBinding**: To interact with UI elements efficiently and safely.

## Building the Project

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Let Gradle sync and build the project.
4.  Run on an Android device or emulator.
