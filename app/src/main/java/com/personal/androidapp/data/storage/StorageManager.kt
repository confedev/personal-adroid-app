package com.personal.androidapp.data.storage

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.personal.androidapp.data.model.DownloadResult
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class StorageManager(private val context: Context) {

    companion object {
        private const val TAG = "StorageManager"
        const val SUBDIR_TEST1_JSONS = "Download/test1/jsons"
        const val SUBDIR_TEST2_IMAGES = "Download/test2/images"
    }

    /**
     * Saves data into the target download directory (e.g., Download/test1/jsons or Download/test2/images).
     */
    fun saveDownload(
        inputStream: InputStream,
        fileName: String,
        mimeType: String,
        relativeSubDir: String
    ): DownloadResult {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveUsingMediaStore(inputStream, fileName, mimeType, relativeSubDir)
            } else {
                saveUsingLegacyStorage(inputStream, fileName, relativeSubDir)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save file: $fileName", e)
            DownloadResult.Error(e.localizedMessage ?: "Unknown storage error")
        }
    }

    private fun saveUsingMediaStore(
        inputStream: InputStream,
        fileName: String,
        mimeType: String,
        relativeSubDir: String
    ): DownloadResult {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, mimeType)
            put(MediaStore.Downloads.RELATIVE_PATH, relativeSubDir)
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val targetUri: Uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            ?: return DownloadResult.Error("Failed to create MediaStore entry for $fileName")

        try {
            resolver.openOutputStream(targetUri)?.use { outputStream ->
                inputStream.copyTo(outputStream)
            } ?: return DownloadResult.Error("Cannot open output stream for URI: $targetUri")

            contentValues.clear()
            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
            resolver.update(targetUri, contentValues, null, null)

            val displayPath = "$relativeSubDir/$fileName"
            Log.d(TAG, "Successfully saved file to $displayPath (URI: $targetUri)")
            return DownloadResult.Success(fileName = fileName, filePath = displayPath, uri = targetUri)
        } catch (e: Exception) {
            resolver.delete(targetUri, null, null)
            throw e
        }
    }

    private fun saveUsingLegacyStorage(
        inputStream: InputStream,
        fileName: String,
        relativeSubDir: String
    ): DownloadResult {
        val baseDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val relativeSubPath = relativeSubDir.removePrefix("Download/").removePrefix("Download")
        val targetDirectory = File(baseDownloads, relativeSubPath)

        if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
            // Fallback to app-specific external directory if permissions prevent direct access
            val fallbackDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), relativeSubPath)
            fallbackDir.mkdirs()
            return saveToFile(inputStream, File(fallbackDir, fileName))
        }

        return saveToFile(inputStream, File(targetDirectory, fileName))
    }

    private fun saveToFile(inputStream: InputStream, targetFile: File): DownloadResult {
        FileOutputStream(targetFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        val uri = Uri.fromFile(targetFile)
        Log.d(TAG, "Saved legacy file at: ${targetFile.absolutePath}")
        return DownloadResult.Success(
            fileName = targetFile.name,
            filePath = targetFile.absolutePath,
            uri = uri
        )
    }
}
