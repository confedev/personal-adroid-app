# Implementation Plan: Test 2 - Get Image

## 1. Goal & Scope
Implement the end-to-end functionality of downloading images from user-provided URLs, dynamically resolving the filename and MIME type from HTTP headers or URL patterns, storing the file into `Downloads/test2/images/`, and rendering an on-screen preview with Coil.

---

## 2. Technical Architecture & Component Mapping

| Layer | Component | File Path |
| :--- | :--- | :--- |
| **Domain Model** | `DownloadResult` | `app/src/main/java/com/personal/androidapp/data/model/DownloadResult.kt` |
| **Data / Storage** | `StorageManager` | `app/src/main/java/com/personal/androidapp/data/storage/StorageManager.kt` |
| **Repository** | `DownloadRepository` | `app/src/main/java/com/personal/androidapp/data/repository/DownloadRepository.kt` |
| **State / ViewModel** | `Test2ViewModel` | `app/src/main/java/com/personal/androidapp/ui/test2/Test2ViewModel.kt` |
| **UI Presentation** | `Test2Screen` | `app/src/main/java/com/personal/androidapp/ui/test2/Test2Screen.kt` |
| **Image Loading** | Coil (`AsyncImage`) | `io.coil-kt:coil-compose:2.5.0` |

---

## 3. Step-by-Step Execution Plan

### Step 1: Filename & MIME Resolution Logic (`DownloadRepository`)
- Implement `resolveImageFileName(url: String, contentDisposition: String?, mimeType: String): String`.
- Parse `Content-Disposition` header with regex `filename\*?=['"]?(?:UTF-8'')?([^;'"]+)`.
- Query `MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)` with fallback mapping table for JPEG, PNG, WEBP, GIF, SVG.
- Parse URL path segments using `url.toHttpUrlOrNull()?.pathSegments`.
- Fall back to `image_yyyyMMdd_HHmmss.<ext>` when needed.
- Sanitize filename with `name.replace(Regex("[^a-zA-Z0-9._-]"), "_")`.

### Step 2: Media Storage Integration
- Call `StorageManager.saveDownload` targeting `StorageManager.SUBDIR_TEST2_IMAGES` (`test2/images`).
- Capture and return `uri: Uri?` in `DownloadResult.Success` so Coil can render it directly from scoped storage.

### Step 3: ViewModel & Reactive State (`Test2ViewModel`)
- Define `Test2UiState` sealed class with `Idle`, `Loading`, `Success(fileName, filePath, imageUri)`, and `Error(message)`.
- Expose `url: StateFlow<String>` and `uiState: StateFlow<Test2UiState>`.
- Implement `downloadImage()` inside `viewModelScope.launch(Dispatchers.IO)`.

### Step 4: Compose UI & Coil Integration (`Test2Screen`)
- Build UI layout with URL input, green "download" button, progress feedback, and Coil `AsyncImage`.
- Ensure `AsyncImage` handles loading states and placeholder/error indicators gracefully.
- Provide back navigation to `MenuScreen`.

### Step 5: Unit Testing & Verification
- Unit test filename resolution algorithms with various test cases (headers with quotes, without quotes, UTF-8 encodings, no headers, query parameters in URLs).
- Verify APK compilation and unit test execution inside Docker container (`./test.sh` and `./build.sh`).
