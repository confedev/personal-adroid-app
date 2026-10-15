#!/usr/bin/env bash
set -euo pipefail

IMAGE="mobiledevops/android-sdk-image:34.0.0"
HOST_UID="$(id -u)"
HOST_GID="$(id -g)"

COMMAND="${*:-./gradlew assembleDebug}"

echo "==> Running in Docker container (${IMAGE}): ${COMMAND}"

docker run --rm \
  --user root \
  -v "${PWD}":/workspace \
  -w /workspace \
  -e GRADLE_USER_HOME=/workspace/.gradle \
  "${IMAGE}" \
  sh -c "${COMMAND} && chown -R ${HOST_UID}:${HOST_GID} /workspace/.gradle /workspace/app/build /workspace/dist 2>/dev/null || true"
