package com.personal.androidapp.ui.test2

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.androidapp.data.model.DownloadResult
import com.personal.androidapp.data.repository.DownloadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Test2UiState {
    data object Idle : Test2UiState()
    data object Loading : Test2UiState()
    data class Success(val fileName: String, val filePath: String, val imageUri: Uri?) : Test2UiState()
    data class Error(val message: String) : Test2UiState()
}

class Test2ViewModel(private val repository: DownloadRepository) : ViewModel() {

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> = _url.asStateFlow()

    private val _uiState = MutableStateFlow<Test2UiState>(Test2UiState.Idle)
    val uiState: StateFlow<Test2UiState> = _uiState.asStateFlow()

    fun updateUrl(newUrl: String) {
        _url.value = newUrl
    }

    fun downloadImage() {
        val currentUrl = _url.value.trim()
        if (currentUrl.isBlank()) {
            _uiState.value = Test2UiState.Error("URL cannot be empty")
            return
        }

        _uiState.value = Test2UiState.Loading

        viewModelScope.launch {
            when (val result = repository.downloadImage(currentUrl)) {
                is DownloadResult.Success -> {
                    _uiState.value = Test2UiState.Success(
                        fileName = result.fileName,
                        filePath = result.filePath,
                        imageUri = result.uri
                    )
                }
                is DownloadResult.Error -> {
                    _uiState.value = Test2UiState.Error(result.message)
                }
            }
        }
    }
}
