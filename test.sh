#!/usr/bin/env bash
set -euo pipefail

echo "==> Running Automated Unit Tests..."
./docker-run.sh ./gradlew test
echo "==> Test reports available at: app/build/reports/tests/testDebugUnitTest/index.html"
