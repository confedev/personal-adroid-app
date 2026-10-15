package com.personal.androidapp.ui.test1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.androidapp.data.model.DownloadResult
import com.personal.androidapp.data.repository.DownloadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Test1UiState {
    data object Idle : Test1UiState()
    data object Loading : Test1UiState()
    data class Success(val fileName: String, val filePath: String) : Test1UiState()
    data class Error(val message: String) : Test1UiState()
}

class Test1ViewModel(private val repository: DownloadRepository) : ViewModel() {

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> = _url.asStateFlow()

    private val _uiState = MutableStateFlow<Test1UiState>(Test1UiState.Idle)
    val uiState: StateFlow<Test1UiState> = _uiState.asStateFlow()

    fun updateUrl(newUrl: String) {
        _url.value = newUrl
    }

    fun downloadJson() {
        val currentUrl = _url.value.trim()
        if (currentUrl.isBlank()) {
            _uiState.value = Test1UiState.Error("URL cannot be empty")
            return
        }

        _uiState.value = Test1UiState.Loading

        viewModelScope.launch {
            when (val result = repository.downloadJson(currentUrl)) {
                is DownloadResult.Success -> {
                    _uiState.value = Test1UiState.Success(
                        fileName = result.fileName,
                        filePath = result.filePath
                    )
                }
                is DownloadResult.Error -> {
                    _uiState.value = Test1UiState.Error(result.message)
                }
            }
        }
    }
}
