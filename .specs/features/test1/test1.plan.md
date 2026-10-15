# Implementation Plan: Test 1 - Get JSON

## 1. Goal & Scope
Implement the end-to-end functionality of downloading arbitrary JSON payloads from user-provided URLs and persisting them locally under `Downloads/test1/jsons/` using timestamped filenames.

---

## 2. Technical Architecture & Component Mapping

| Layer | Component | File Path |
| :--- | :--- | :--- |
| **Domain Model** | `DownloadResult` | `app/src/main/java/com/personal/androidapp/data/model/DownloadResult.kt` |
| **Data / Storage** | `StorageManager` | `app/src/main/java/com/personal/androidapp/data/storage/StorageManager.kt` |
| **Repository** | `DownloadRepository` | `app/src/main/java/com/personal/androidapp/data/repository/DownloadRepository.kt` |
| **State / ViewModel** | `Test1ViewModel` | `app/src/main/java/com/personal/androidapp/ui/test1/Test1ViewModel.kt` |
| **UI Presentation** | `Test1Screen` | `app/src/main/java/com/personal/androidapp/ui/test1/Test1Screen.kt` |
| **Navigation** | `AppNavHost`, `Screen` | `app/src/main/java/com/personal/androidapp/ui/navigation/AppNavHost.kt` |

---

## 3. Step-by-Step Execution Plan

### Step 1: Storage Layer Implementation (`StorageManager`)
- Implement `saveDownload(inputStream, fileName, mimeType, relativeSubDir)` using `MediaStore.Downloads`.
- For API 29+ (`Build.VERSION_CODES.Q`), populate `MediaStore.MediaColumns.RELATIVE_PATH = "Download/$relativeSubDir"` and set `IS_PENDING = 1` while streaming bytes, then update `IS_PENDING = 0`.
- For legacy API 26-28, write to `Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)/$relativeSubDir` and trigger a `MediaScannerConnection` scan.

### Step 2: Repository Layer Implementation (`DownloadRepository`)
- Define `downloadJson(url: String): DownloadResult` with `withContext(Dispatchers.IO)`.
- Validate URL schema (`http://` or `https://`).
- Issue an OkHttp request with `Accept: application/json`.
- On HTTP 200 OK, format the current timestamp as `yyyyMMdd_HHmmss.json`.
- Stream response byte stream to `StorageManager.saveDownload`.

### Step 3: ViewModel & Reactive State (`Test1ViewModel`)
- Define `Test1UiState` sealed class with `Idle`, `Loading`, `Success(fileName, filePath)`, and `Error(message)`.
- Expose `url: StateFlow<String>` and `uiState: StateFlow<Test1UiState>`.
- Implement `updateUrl(newUrl: String)` and `downloadJson()`.

### Step 4: Compose UI (`Test1Screen`)
- Build Material 3 Scaffold with top app bar, navigation icon, and title.
- Render centered `OutlinedTextField` for URL input.
- Render styled `Button` with green container color (`ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))`).
- Render loading spinner, success message, or error message according to `uiState`.

### Step 5: Unit Testing & Verification
- Unit test repository URL validation and HTTP error response handling in `DownloadRepositoryTest`.
- Validate build and APK packaging using `./build.sh`.
