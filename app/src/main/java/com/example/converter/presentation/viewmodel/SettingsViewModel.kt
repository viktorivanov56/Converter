package com.example.converter.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.appcompat.app.AppCompatDelegate

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // Получаем SharedPreferences для хранения настроек
    private val preferences = application.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // LiveData для отслеживания состояния темы
    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    init {
        // При инициализации ViewModel загружаем текущее состояние темы из SharedPreferences
        val isDark = preferences.getBoolean("isDarkTheme", false)
        _isDarkTheme.value = isDark
        setAppTheme(isDark)
    }

    // Метод для переключения темы
    fun toggleTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark // Обновляем значение LiveData
        preferences.edit().putBoolean("isDarkTheme", isDark).apply() // Сохраняем состояние в SharedPreferences
        setAppTheme(isDark) // Применяем тему
    }

    // Метод для установки темы приложения
    private fun setAppTheme(isDark: Boolean) {
        val mode = if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode) // Устанавливаем тему
    }
}
