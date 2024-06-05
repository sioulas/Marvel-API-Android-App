package com.example.uxmapp2.ui.settings

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
    private val _canNavigateToSettings = MutableLiveData<Boolean>()

    val canNavigateToSettings: LiveData<Boolean>
        get() = _canNavigateToSettings

    init {
        _canNavigateToSettings.value = true
        Log.d("SettingsViewModel", "Initial canNavigateToSettings: ${_canNavigateToSettings.value}")
    }

    fun allowSettingsNavigation() {
        _canNavigateToSettings.value = true
        Log.d("SettingsViewModel", "allowSettingsNavigation: ${_canNavigateToSettings.value}")
    }

    fun resetSettingsNavigation() {
        _canNavigateToSettings.value = false
        Log.d("SettingsViewModel", "resetSettingsNavigation: ${_canNavigateToSettings.value}")
    }
}





