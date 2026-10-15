package com.personal.androidapp.ui.menu

import androidx.lifecycle.ViewModel
import com.personal.androidapp.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel : ViewModel() {

    private val _appVersion = MutableStateFlow(BuildConfig.VERSION_NAME)
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()
}
