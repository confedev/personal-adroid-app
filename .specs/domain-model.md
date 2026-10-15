# Domain Model Specification

## 1. Overview
This document defines the core domain model, entities, state aggregates, value objects, and persistence contracts for the Personal Android Application.

---

## 2. Entities & Domain Results

### 2.1 `DownloadResult`
Represents the domain outcome of any network fetch and local persistence operation executed through the repository layer.

```kotlin
sealed class DownloadResult {
    data class Success(
        val fileName: String,
        val filePath: String,
        val uri: Uri?
    ) : DownloadResult()

    data class Error(
        val message: String
    ) : DownloadResult()
}
```

- **`Success`**:
  - `fileName`: The final sanitized name under which the file was stored.
  - `filePath`: The relative or absolute device path (e.g., `Download/test1/jsons/20261015_183000.json`).
  - `uri`: The `android.net.Uri` handle returned by `MediaStore` (enabling preview loading via Coil or content sharing).
- **`Error`**:
  - `message`: User-friendly descriptive error message explaining why the download or storage operation failed.

---

## 3. Storage Directory & Media Aggregates

### 3.1 Storage Subdirectories
Domain constants representing target storage categories under the public `Downloads` volume:

| Constant | Path Value | Description |
| :--- | :--- | :--- |
| `SUBDIR_TEST1_JSONS` | `test1/jsons` | Dedicated storage partition for raw JSON payloads |
| `SUBDIR_TEST2_IMAGES` | `test2/images` | Dedicated storage partition for downloaded images |

### 3.2 Filename Resolution Domain Rules
For dynamic media resolution (`SUBDIR_TEST2_IMAGES`):
- **Content-Disposition Regex:** `filename\*?=['"]?(?:UTF-8'')?([^;'"]+)`
- **Supported Image MIME Types:**
  - `image/jpeg`, `image/jpg` -> `.jpg`
  - `image/png` -> `.png`
  - `image/webp` -> `.webp`
  - `image/gif` -> `.gif`
  - `image/svg+xml` -> `.svg`
  - Fallback extension: `.jpg`
- **Fallback Formatter:** `image_yyyyMMdd_HHmmss.<ext>`
- **Sanitization Rule:** Replace any character not matching `[a-zA-Z0-9._-]` with `_`.

---

## 4. UI State Aggregates

Each feature screen manages a dedicated reactive state aggregate implementing unidirectional state flow.

### 4.1 `Test1UiState`
Represents the reactive state of the JSON download screen:

```kotlin
sealed class Test1UiState {
    data object Idle : Test1UiState()
    data object Loading : Test1UiState()
    data class Success(val fileName: String, val filePath: String) : Test1UiState()
    data class Error(val message: String) : Test1UiState()
}
```

### 4.2 `Test2UiState`
Represents the reactive state of the image download screen:

```kotlin
sealed class Test2UiState {
    data object Idle : Test2UiState()
    data object Loading : Test2UiState()
    data class Success(val fileName: String, val filePath: String, val imageUri: Uri?) : Test2UiState()
    data class Error(val message: String) : Test2UiState()
}
```

---

## 5. Navigation Aggregates

Navigation destinations are strongly typed using a sealed class hierarchy:

```kotlin
sealed class Screen(val route: String) {
    data object Menu : Screen("menu")
    data object Test1 : Screen("test1")
    data object Test2 : Screen("test2")
}
```

- `Screen.Menu`: Entry screen displaying the menu and app version.
- `Screen.Test1`: Target screen for JSON download tests.
- `Screen.Test2`: Target screen for image download and preview tests.
