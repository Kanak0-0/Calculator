package com.example.calculator0_0

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import java.util.*

class SpeechCalculator(private val activity: Activity) {
    
    companion object {
        const val SPEECH_REQUEST_CODE = 0
    }

    fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your calculation")
        }

        try {
            activity.startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(activity, 
                "Speech recognition not available on this device", 
                Toast.LENGTH_SHORT).show()
        }
    }

    fun processResult(spokenText: String): String {
        // Convert spoken words to mathematical expression
        return try {
            val expression = convertSpokenWordsToExpression(spokenText)
            expression
        } catch (e: Exception) {
            "Error: Could not process speech"
        }
    }

    private fun convertSpokenWordsToExpression(spokenText: String): String {
        // Convert words like "two plus three" to "2 + 3"
        var expression = spokenText.toLowerCase(Locale.getDefault())
            .replace("plus", "+")
            .replace("minus", "-")
            .replace("times", "*")
            .replace("multiplied by", "*")
            .replace("divided by", "/")
            .replace("equals", "=")
            
        // Convert number words to digits
        expression = expression
            .replace("zero", "0")
            .replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
            
        return expression
    }
}