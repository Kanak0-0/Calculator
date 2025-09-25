package com.example.calculator0_0

import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.calculator0_0.databinding.ActivityMainBinding // Added ViewBinding import
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val RECORD_AUDIO_PERMISSION_CODE = 1
    private val SPEECH_REQUEST_CODE = 2
    private var canAddOperation = false
    private var canAddDecimal = true
    private var isScientificMode = false
    private var isUnitConversionMode = false

    private lateinit var themeManager: ThemeManager
    private lateinit var scientificKeypad: View
    private lateinit var unitConversionKeypad: View
    private lateinit var binding: ActivityMainBinding // Added ViewBinding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        themeManager = ThemeManager(this)
        themeManager.applyTheme()
        
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate binding
        setContentView(binding.root) // Set content view with binding root
        
        // Initialize keypads
        scientificKeypad = findViewById(R.id.scientificKeypad) // Assuming this ID is in a different layout included by activity_main
        unitConversionKeypad = findViewById(R.id.unitConversionKeypad) // Assuming this ID is in a different layout
        updateKeypadVisibility()
        
        // Request microphone permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE)
        }
    }

    fun numberAction(view: View)
    {
        if(view is Button)
        {
            if(view.text == ".")
            {
                if(canAddDecimal)
                    binding.workingsTV.append(view.text)

                canAddDecimal = false
            }
            else
                binding.workingsTV.append(view.text)

            canAddOperation = true
        }
    }

    fun operationAction(view: View)
    {
        if(view is Button && canAddOperation)
        {
            binding.workingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View)
    {
        binding.workingsTV.text = ""
        binding.resultsTV.text = ""
    }

    fun backSpaceAction(view: View)
    {
        val length = binding.workingsTV.length()
        if(length > 0)
            binding.workingsTV.text = binding.workingsTV.text.subSequence(0, length - 1)
    }

    fun equalsAction(view: View) {
        when {
            isUnitConversionMode -> binding.resultsTV.text = calculateUnitConversion()
            isScientificMode -> binding.resultsTV.text = calculateScientific()
            else -> binding.resultsTV.text = calculateResults()
        }
    }

    private fun calculateUnitConversion(): String {
        val input = binding.workingsTV.text.toString()
        if (input.isEmpty()) return ""

        try {
            val parts = input.split(" ")
            if (parts.size < 3) return "Invalid format"
            
            val value = parts[0].toDouble()
            val from = parts[1]
            val to = parts[2]

            return when {
                from == "m" && to == "ft" -> UnitConverter.metersToFeet(value).toString()
                from == "ft" && to == "m" -> UnitConverter.feetToMeters(value).toString()
                from == "kg" && to == "lb" -> UnitConverter.kilogramsToPounds(value).toString()
                from == "lb" && to == "kg" -> UnitConverter.poundsToKilograms(value).toString()
                from == "C" && to == "F" -> UnitConverter.celsiusToFahrenheit(value).toString()
                from == "F" && to == "C" -> UnitConverter.fahrenheitToCelsius(value).toString()
                from == "L" && to == "gal" -> UnitConverter.litersToGallons(value).toString()
                from == "gal" && to == "L" -> UnitConverter.gallonsToLiters(value).toString()
                else -> "Unsupported conversion"
            }
        } catch (e: Exception) {
            return "Error"
        }
    }

    fun showThemeMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.theme_menu, popup.menu)
        
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.theme_default -> themeManager.setTheme(ThemeManager.THEME_DEFAULT)
                R.id.theme_dark -> themeManager.setTheme(ThemeManager.THEME_DARK)
                R.id.theme_light -> themeManager.setTheme(ThemeManager.THEME_LIGHT)
                R.id.theme_blue -> themeManager.setTheme(ThemeManager.THEME_BLUE)
                R.id.theme_green -> themeManager.setTheme(ThemeManager.THEME_GREEN)
            }
            recreate()
            true
        }
        popup.show()
    }

    fun toggleScientificMode(view: View) {
        isScientificMode = !isScientificMode
        if (isScientificMode) {
            isUnitConversionMode = false
        }
        updateKeypadVisibility()
        clearCalculator()
    }

    fun toggleUnitConversionMode(view: View) {
        isUnitConversionMode = !isUnitConversionMode
        if (isUnitConversionMode) {
            isScientificMode = false
        }
        updateKeypadVisibility()
        clearCalculator()
    }

    private fun updateKeypadVisibility() {
        scientificKeypad.visibility = if (isScientificMode) View.VISIBLE else View.GONE
        unitConversionKeypad.visibility = if (isUnitConversionMode) View.VISIBLE else View.GONE
        
        // Update button states
        findViewById<Button>(R.id.btnScientificMode).isSelected = isScientificMode
        findViewById<Button>(R.id.btnUnitMode).isSelected = isUnitConversionMode
    }

    private fun clearCalculator() {
        binding.workingsTV.text = ""
        binding.resultsTV.text = when {
            isScientificMode -> "Scientific Mode"
            isUnitConversionMode -> "Unit Conversion Mode"
            else -> ""
        }
    }

    fun scientificAction(view: View) {
        if (view is Button) {
            when (view.text.toString()) {
                "sin" -> binding.workingsTV.append("sin ")
                "cos" -> binding.workingsTV.append("cos ")
                "tan" -> binding.workingsTV.append("tan ")
                "π" -> binding.workingsTV.append(ScientificCalculator.PI.toString())
                "log" -> binding.workingsTV.append("log ")
                "ln" -> binding.workingsTV.append("ln ")
                "√" -> binding.workingsTV.append("sqrt ")
                "x²" -> binding.workingsTV.append("^2 ")
            }
        }
    }

    fun conversionAction(view: View) {
        if (view is Button) {
            binding.workingsTV.text = ""  // Clear current input
            when (view.text.toString()) {
                "m → ft" -> binding.workingsTV.append("0 m ft")
                "ft → m" -> binding.workingsTV.append("0 ft m")
                "kg → lb" -> binding.workingsTV.append("0 kg lb")
                "lb → kg" -> binding.workingsTV.append("0 lb kg")
                "°C → °F" -> binding.workingsTV.append("0 C F")
                "°F → °C" -> binding.workingsTV.append("0 F C")
                "L → gal" -> binding.workingsTV.append("0 L gal")
                "gal → L" -> binding.workingsTV.append("0 gal L")
            }
        }
    }

    private fun calculateScientific(): String {
        val input = binding.workingsTV.text.toString()
        if (input.isEmpty()) return ""

        try {
            if (input.startsWith("sin ")) {
                val angle = input.substring(4).toDouble()
                return ScientificCalculator.sin(ScientificCalculator.degreesToRadians(angle)).toString()
            }
            if (input.startsWith("cos ")) {
                val angle = input.substring(4).toDouble()
                return ScientificCalculator.cos(ScientificCalculator.degreesToRadians(angle)).toString()
            }
            if (input.startsWith("tan ")) {
                val angle = input.substring(4).toDouble()
                return ScientificCalculator.tan(ScientificCalculator.degreesToRadians(angle)).toString()
            }
            if (input.startsWith("log ")) {
                val number = input.substring(4).toDouble()
                return ScientificCalculator.log10(number).toString()
            }
            if (input.startsWith("ln ")) {
                val number = input.substring(3).toDouble()
                return ScientificCalculator.ln(number).toString()
            }
            if (input.startsWith("sqrt ")) {
                val number = input.substring(5).toDouble()
                return ScientificCalculator.sqrt(number).toString()
            }
            if (input.startsWith("^2 ")) {
                val number = input.substring(3).toDouble()
                return ScientificCalculator.power(number, 2.0).toString()
            }
            if (input.startsWith("^3 ")) {
                val number = input.substring(3).toDouble()
                return ScientificCalculator.power(number, 3.0).toString()
            }
            return "Invalid input"
        } catch (e: Exception) {
            return "Error"
        }
    }

    // Theme and mode related functions are moved to the top of the file

    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in binding.workingsTV.text.toString()) // Used binding here
        {
            if(character.isDigit() || character == '.')
                currentDigit += character
            else {
                if(currentDigit.isNotEmpty()) {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                }
                if(character in listOf('+', '-', '/', 'x'))
                    list.add(character)
            }
        }

        if(currentDigit.isNotEmpty())
            list.add(currentDigit.toFloat())

        return list
    }

    fun startVoiceInput(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED) {
            
            // Start animation
            view.isActivated = true
            
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your calculation")
            }
            
            try {
                startActivityForResult(intent, SPEECH_REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(this, "Speech recognition not available", 
                    Toast.LENGTH_SHORT).show()
            }
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        findViewById<ImageButton>(R.id.btnMic).isActivated = false

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                    results[0]
                }

            spokenText?.let {
                try {
                    // Convert spoken text to mathematical expression
                    val expression = convertSpokenTextToExpression(it)
                    binding.workingsTV.text = expression // Used binding here
                    
                    // Trigger calculation
                    equalsAction(View(this)) // Consider creating a dedicated view or null if not used by layout
                } catch (e: Exception) {
                    Toast.makeText(this, "Couldn't understand the calculation", 
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun convertSpokenTextToExpression(spokenText: String): String {
        // Convert words to mathematical expression
        return spokenText
            .lowercase()
            .replace("plus", "+")
            .replace("minus", "-")
            .replace("times", "*")
            .replace("multiply by", "*")
            .replace("multiplied by", "*")
            .replace("divided by", "/")
            .replace("divide by", "/")
            .replace(" ", "") // Remove spaces
            .replace("x", "*") // Common multiplication symbol
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted, try voice input again", 
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Removed the stray code block that was here
}
