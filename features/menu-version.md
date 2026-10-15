# Feature: Main Menu and Version Display

## 1. Description
The initial landing screen of the application presents a menu listing available test capabilities and shows the current compile-time application version.

## 2. Requirements
- Menu Items:
  - `test1 (get json)` -> Navigates to Test 1 Screen.
  - `test2 (get image)` -> Navigates to Test 2 Screen.
- Version Display:
  - Located on the initial menu (at the bottom or header).
  - Fetched dynamically from `BuildConfig.VERSION_NAME` generated during compilation (`app/build.gradle.kts`).
- Language: English UI elements, strictly defined in Android string resources.
