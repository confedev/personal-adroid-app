# Feature: Test 2 - Get Image

## 1. Description
Provides a screen enabling the user to download an image from an HTTP/HTTPS URL and save it into the dedicated directory.

## 2. UI Layout
- Centered layout containing:
  - Text input field for Image URL.
  - Green button labeled "download".
  - Image preview (using Coil) upon successful download.
  - Status/Feedback text showing progress, detected file name, detected extension/MIME type, or error details.
- Navigation back to the Main Menu.

## 3. Storage & Dynamic Name/Type Resolution
- Destination directory: `Downloads/test2/images/`
- Dynamic File Name and Extension Detection:
  1. Header check: `Content-Disposition` header for explicit `filename="example.png"`.
  2. URL path check: Extract file name and extension from URL path if present (e.g., `image.jpeg`).
  3. Content-Type MIME check: Inspect HTTP `Content-Type` header (e.g., `image/png` -> `.png`, `image/jpeg` -> `.jpg`, `image/webp` -> `.webp`, `image/gif` -> `.gif`).
  4. Fallback: If no filename is available, generate timestamped name with the resolved extension (e.g., `image_yyyyMMdd_HHmmss.jpg`).
- Persistence: Saved directly into `Downloads/test2/images/` using Android's MediaStore scoped storage API.
