# System Architecture Specification

## 1. Overview
The Personal Android Application is a modular experimentation client built with Java 17 compatibility, Kotlin, and Jetpack Compose. It uses an MVVM (Model-View-ViewModel) architecture.

## 2. Technical Stack
- **Language:** Kotlin (compiled with JVM target 17)
- **UI Framework:** Jetpack Compose (Material 3)
- **Build System:** Gradle (AGP 8.2.2 / Gradle 8.5)
- **Container Environment:** `mobiledevops/android-sdk-image:34.0.0`
- **Networking:** OkHttp 4.12.0
- **Asynchrony:** Kotlin Coroutines (`Dispatchers.IO`) & StateFlow
- **Image Preview:** Coil 2.5.0 (for Compose)
- **Minimum SDK:** 26 (Android 8.0 Oreo)
- **Target SDK:** 34 (Android 14)

## 3. Architecture Layers
- **UI Layer (Jetpack Compose + ViewModels):**
  - `MenuScreen`: Initial entry menu listing functionalities and displaying compile-time app version (`BuildConfig.VERSION_NAME`).
  - `Test1Screen`: Centered URL input, green download button, status feedback for JSON download.
  - `Test2Screen`: Centered image URL input, green download button, preview, and status feedback.
- **Domain / Repository Layer:**
  - `DownloadRepository`: Coordinates network fetching and delegates persistence to `StorageManager`.
- **Data / Storage Layer:**
  - `StorageManager`: Utilizes Android `MediaStore.Downloads` for scoped storage under `Download/test1/jsons` and `Download/test2/images`.

## 4. File Storage Strategy
- **JSON Files (`test1`):**
  - Path: `Downloads/test1/jsons/`
  - Naming: `yyyyMMdd_HHmmss.json` (timestamp of download execution)
  - MIME: `application/json`
- **Image Files (`test2`):**
  - Path: `Downloads/test2/images/`
  - Naming & Type: Derived from HTTP response headers (`Content-Disposition`, `Content-Type`) and fallback to URL path segments.
