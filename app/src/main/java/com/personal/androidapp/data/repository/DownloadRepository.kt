package com.personal.androidapp.data.repository

import android.content.Context
import android.util.Log
import android.webkit.MimeTypeMap
import com.personal.androidapp.data.model.DownloadResult
import com.personal.androidapp.data.storage.StorageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class DownloadRepository(
    context: Context,
    private val storageManager: StorageManager = StorageManager(context.applicationContext)
) {

    companion object {
        private const val TAG = "DownloadRepository"
        private const val JSON_DATE_PATTERN = "yyyyMMdd_HHmmss"
        private val FILENAME_REGEX = Pattern.compile("filename\\*?=['\"]?(?:UTF-8'')?([^;'\"]+)", Pattern.CASE_INSENSITIVE)
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun downloadJson(url: String): DownloadResult = withContext(Dispatchers.IO) {
        if (!isValidUrl(url)) {
            return@withContext DownloadResult.Error("Invalid URL format. URL must start with http:// or https://")
        }

        try {
            val request = Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .get()
                .build()

            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext DownloadResult.Error("HTTP request failed with code ${response.code}: ${response.message}")
                }

                val body = response.body
                    ?: return@withContext DownloadResult.Error("Response body is empty")

                val timeStamp = SimpleDateFormat(JSON_DATE_PATTERN, Locale.US).format(Date())
                val fileName = "$timeStamp.json"

                body.byteStream().use { inputStream ->
                    storageManager.saveDownload(
                        inputStream = inputStream,
                        fileName = fileName,
                        mimeType = "application/json",
                        relativeSubDir = StorageManager.SUBDIR_TEST1_JSONS
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception downloading JSON from $url", e)
            DownloadResult.Error(e.localizedMessage ?: "Unknown error downloading JSON")
        }
    }

    suspend fun downloadImage(url: String): DownloadResult = withContext(Dispatchers.IO) {
        if (!isValidUrl(url)) {
            return@withContext DownloadResult.Error("Invalid URL format. URL must start with http:// or https://")
        }

        try {
            val request = Request.Builder()
                .url(url)
                .header("Accept", "image/*")
                .get()
                .build()

            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext DownloadResult.Error("HTTP request failed with code ${response.code}: ${response.message}")
                }

                val body = response.body
                    ?: return@withContext DownloadResult.Error("Response body is empty")

                val contentTypeHeader = response.header("Content-Type") ?: "image/jpeg"
                val rawMimeType = contentTypeHeader.split(";").firstOrNull()?.trim() ?: "image/jpeg"
                val contentDisposition = response.header("Content-Disposition")

                val resolvedFileName = resolveImageFileName(
                    url = url,
                    contentDisposition = contentDisposition,
                    mimeType = rawMimeType
                )

                body.byteStream().use { inputStream ->
                    storageManager.saveDownload(
                        inputStream = inputStream,
                        fileName = resolvedFileName,
                        mimeType = rawMimeType,
                        relativeSubDir = StorageManager.SUBDIR_TEST2_IMAGES
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception downloading Image from $url", e)
            DownloadResult.Error(e.localizedMessage ?: "Unknown error downloading image")
        }
    }

    private fun resolveImageFileName(
        url: String,
        contentDisposition: String?,
        mimeType: String
    ): String {
        // 1. Check Content-Disposition header
        if (!contentDisposition.isNullOrBlank()) {
            val matcher = FILENAME_REGEX.matcher(contentDisposition)
            if (matcher.find()) {
                val extracted = matcher.group(1)?.trim()
                if (!extracted.isNullOrBlank()) {
                    return sanitizeFileName(extracted)
                }
            }
        }

        // 2. Derive extension from MIME type
        val extensionFromMime = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            ?: when (mimeType.lowercase()) {
                "image/jpeg", "image/jpg" -> "jpg"
                "image/png" -> "png"
                "image/webp" -> "webp"
                "image/gif" -> "gif"
                "image/svg+xml" -> "svg"
                else -> "jpg"
            }

        // 3. Check URL path segment
        val lastPathSegment = try {
            okhttp3.HttpUrl.get(url).pathSegments.lastOrNull { it.isNotEmpty() }
        } catch (e: Exception) {
            null
        }

        if (!lastPathSegment.isNullOrBlank()) {
            val sanitized = sanitizeFileName(lastPathSegment)
            if (sanitized.contains(".")) {
                return sanitized
            } else {
                return "$sanitized.$extensionFromMime"
            }
        }

        // 4. Fallback timestamped filename
        val timeStamp = SimpleDateFormat(JSON_DATE_PATTERN, Locale.US).format(Date())
        return "image_$timeStamp.$extensionFromMime"
    }

    private fun sanitizeFileName(name: String): String {
        return name.replace(Regex("[^a-zA-Z0-9._-]"), "_")
    }

    private fun isValidUrl(url: String): Boolean {
        val trimmed = url.trim()
        return trimmed.startsWith("http://", ignoreCase = true) ||
                trimmed.startsWith("https://", ignoreCase = true)
    }
}
