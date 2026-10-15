package com.personal.androidapp.data.model

import android.net.Uri

sealed class DownloadResult {
    data class Success(val fileName: String, val filePath: String, val uri: Uri?) : DownloadResult()
    data class Error(val message: String) : DownloadResult()
}
