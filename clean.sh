#!/usr/bin/env bash
set -euo pipefail

echo "==> Cleaning project build artifacts..."
./docker-run.sh ./gradlew clean

if [ -d "dist" ]; then
    echo "==> Removing dist/ directory..."
    rm -rf dist
fi

echo "==> Clean complete."
