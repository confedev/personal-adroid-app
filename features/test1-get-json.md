# Feature: Test 1 - Get JSON

## 1. Description
Provides a screen allowing the user to download arbitrary JSON payloads via an HTTP GET request and persist them locally.

## 2. UI Layout
- Centered layout containing:
  - Text input field for URL.
  - Green button labeled "download".
  - Status/Feedback text showing progress, downloaded file path, or error details.
- Navigation back to the Main Menu.

## 3. Storage & File Naming
- Destination directory: `Downloads/test1/jsons/`
- File Name: Formatted datetime of the moment the download is saved: `yyyyMMdd_HHmmss.json`.
- File content: JSON response body obtained via GET.
