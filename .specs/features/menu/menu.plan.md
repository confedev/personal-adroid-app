# Implementation Plan: Main Menu and Version Display

## 1. Goal & Scope
Provide the landing UI with navigation entry points to test modules and dynamically surface the compile-time version from Gradle `BuildConfig`.

---

## 2. Technical Architecture & Component Mapping

| Layer | Component | File Path |
| :--- | :--- | :--- |
| **Build Configuration** | `BuildConfig.VERSION_NAME` | `app/build.gradle.kts` (`defaultConfig { versionName = "1.0.0" }`) |
| **ViewModel** | `MenuViewModel` | `app/src/main/java/com/personal/androidapp/ui/menu/MenuViewModel.kt` |
| **UI Presentation** | `MenuScreen` | `app/src/main/java/com/personal/androidapp/ui/menu/MenuScreen.kt` |
| **Navigation** | `AppNavHost`, `Screen` | `app/src/main/java/com/personal/androidapp/ui/navigation/AppNavHost.kt` |

---

## 3. Step-by-Step Execution Plan

### Step 1: Gradle Build Config Setup
- Ensure `buildFeatures { buildConfig = true }` is enabled in `app/build.gradle.kts`.
- Set `versionName = "1.0.0"` in `defaultConfig`.

### Step 2: ViewModel Implementation (`MenuViewModel`)
- Inject or read `BuildConfig.VERSION_NAME` and expose it via `val appVersion: String`.

### Step 3: Compose UI (`MenuScreen`)
- Construct centered Column layout with title and menu action cards/buttons.
- Bind `onNavigateToTest1` callback to navigate to `Screen.Test1.route`.
- Bind `onNavigateToTest2` callback to navigate to `Screen.Test2.route`.
- Render version string in footer container.

### Step 4: Verification
- Verify that changing `versionName` in `app/build.gradle.kts` updates the displayed version on rebuild.
