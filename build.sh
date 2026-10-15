#!/usr/bin/env bash
set -euo pipefail

TARGET="${1:-debug}"
DIST_DIR="dist"

mkdir -p "$DIST_DIR"

if [ "$TARGET" = "release" ]; then
    echo "==> Building Release APK..."
    ./docker-run.sh ./gradlew assembleRelease
    
    SOURCE_APK="app/build/outputs/apk/release/app-release-unsigned.apk"
    DEST_APK="$DIST_DIR/personal-app-release.apk"
    
    if [ -f "$SOURCE_APK" ]; then
        cp "$SOURCE_APK" "$DEST_APK"
        echo "==> [SUCCESS] Release APK copied to: $DEST_APK"
    else
        echo "==> [WARNING] Source APK not found at $SOURCE_APK"
    fi
else
    echo "==> Building Debug APK (Full installable package)..."
    ./docker-run.sh ./gradlew assembleDebug
    
    SOURCE_APK="app/build/outputs/apk/debug/app-debug.apk"
    DEST_APK="$DIST_DIR/personal-app-debug.apk"
    
    if [ -f "$SOURCE_APK" ]; then
        cp "$SOURCE_APK" "$DEST_APK"
        echo "=========================================================="
        echo "==> [SUCCESS] Installable APK ready at: $DEST_APK"
        echo "==> Size: $(du -h "$DEST_APK" | cut -f1)"
        echo "=========================================================="
    else
        echo "==> [WARNING] Source APK not found at $SOURCE_APK"
    fi
fi
