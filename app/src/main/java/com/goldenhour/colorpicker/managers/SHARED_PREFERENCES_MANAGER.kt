package com.goldenhour.colorpicker.managers

import android.content.SharedPreferences


class SHARED_PREFERENCES_MANAGER {
    companion object {
        val shared = SHARED_PREFERENCES_MANAGER()
    }

    lateinit var preferences: SharedPreferences

    // TODO Properly init preferences to avoid launch crash
    fun initializePreferences(currentPreferences: SharedPreferences) {
        preferences = currentPreferences

        val editor = preferences.edit()
        editor.putStringSet("saved_colors", getSavedColorSet())
        editor.apply()
    }
}

fun SHARED_PREFERENCES_MANAGER.getSavedColorSet(): MutableSet<String> {
    return preferences.getStringSet("saved_colors", mutableSetOf("")) ?: mutableSetOf("")
}

fun SHARED_PREFERENCES_MANAGER.setSavedColorSet(newColorSet: Set<String>) {
    preferences.edit().putStringSet("saved_colors", newColorSet).apply()
}