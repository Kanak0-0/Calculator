package com.example.calculator0_0

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class ThemeManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ThemePrefs", AppCompatActivity.MODE_PRIVATE)
    
    companion object {
        const val THEME_DEFAULT = 0
        const val THEME_DARK = 1
        const val THEME_LIGHT = 2
        const val THEME_BLUE = 3
        const val THEME_GREEN = 4
    }

    fun setTheme(themeId: Int) {
        prefs.edit().putInt("current_theme", themeId).apply()
    }

    fun getCurrentTheme(): Int {
        return prefs.getInt("current_theme", THEME_DEFAULT)
    }

    fun applyTheme() {
        when (getCurrentTheme()) {
            THEME_DARK -> context.setTheme(R.style.Theme_Calculator_Dark)
            THEME_LIGHT -> context.setTheme(R.style.Theme_Calculator_Light)
            THEME_BLUE -> context.setTheme(R.style.Theme_Calculator_Blue)
            THEME_GREEN -> context.setTheme(R.style.Theme_Calculator_Green)
            else -> context.setTheme(R.style.Theme_Calculator)
        }
    }
}