# personal-android-app

A simple Android application designed for testing and experimenting with modern mobile features, built using Kotlin, Jetpack Compose, Java 17, terminal-based commands, and Docker.

---

## Project Directory Structure

```text
personal-android-app/
├── .gemini/
│   ├── context-rules.md          # Agent context rules and technical constraints
│   └── system-prompt.md          # Agent system prompt
├── .specs/                       # 🧠 Specifications (Single Source of Truth - SDD)
│   ├── README.md                 # Specifications overview and index
│   ├── system-architecture.md    # Overall system architecture, stack, and decisions
│   ├── domain-model.md           # Domain model, entities, and state aggregates
│   └── features/                 # Functional specs and plans per feature
│       ├── README.md             # Features catalog and index
│       ├── menu/
│       │   ├── menu.spec.md      # Main menu & version requirements and flows
│       │   └── menu.plan.md      # Main menu implementation plan
│       ├── test1/
│       │   ├── test1.spec.md     # Test 1 (JSON download) requirements and rules
│       │   └── test1.plan.md     # Test 1 execution and testing plan
│       └── test2/
│           ├── test2.spec.md     # Test 2 (Image download) requirements and rules
│           └── test2.plan.md     # Test 2 execution and testing plan
├── app/                          # Main Android application module
│   ├── src/main/java/            # Kotlin source code (MVVM architecture)
│   ├── src/main/res/             # Resource definitions (strings.xml, colors.xml, themes.xml)
│   ├── src/test/java/            # Unit tests
│   └── build.gradle.kts          # Module-level Gradle configuration
├── AGENTS.md                     # Agents rules and constraints
├── build.gradle.kts              # Root Gradle build script
├── settings.gradle.kts           # Root Gradle settings
├── gradle.properties             # Gradle JVM and AndroidX configuration
├── gradlew                       # Gradle wrapper executable
├── gradle/wrapper/               # Gradle wrapper JAR and properties (Gradle 8.5)
├── docker-run.sh                 # Docker execution wrapper handling UID permissions & caches
├── build.sh                      # Helper script to build debug/release APK
├── test.sh                       # Helper script to run automated unit tests
└── clean.sh                      # Helper script to clean build artifacts
```

---

## Prerequisites

To build, test, and run this project, you **do not** need Android Studio installed locally. You only need:
1. **Docker** installed and running on your machine.
2. **Git** to clone and manage the repository.

---

## Docker Execution Considerations

When executing Gradle commands inside the container image (`mobiledevops/android-sdk-image:34.0.0`):
1. **UID/GID Permissions:** The container image runs by default under internal UID 999 (`mobiledevops`), while workspace files on host Linux systems typically belong to UID 1000 (`$UID`). To avoid write permission errors on mounted volumes, containers must execute with `--user root` and restore workspace ownership using `chown -R $(id -u):$(id -g)`.
2. **Persistent Gradle Cache:** Setting `-e GRADLE_USER_HOME=/workspace/.gradle` preserves downloaded Android dependencies and Gradle distributions across container runs, significantly speeding up subsequent builds.
3. **Automated Wrapper Scripts:** To simplify workflows, helper shell scripts (`build.sh`, `test.sh`, `clean.sh`, `docker-run.sh`) encapsulate these Docker arguments automatically.

---

## Command Workflows (Using Helper Scripts)

### 1. Compile and Build Debug APK (Ready for Device Install)
Build the debug APK:
```bash
./build.sh
```
*The full installable APK is automatically placed in: `dist/personal-app-debug.apk`*

### 2. Compile and Build Release APK
Build the release APK:
```bash
./build.sh release
```
*Generated APK location: `dist/personal-app-release.apk`*

### 3. Run Automated Unit Tests
Execute unit tests inside the container:
```bash
./test.sh
```
*HTML reports generated at: `app/build/reports/tests/testDebugUnitTest/index.html`*

### 4. Clean Build Artifacts
Clean previous build outputs:
```bash
./clean.sh
```

### 5. Run Arbitrary Gradle Commands
Execute any Gradle task inside the container:
```bash
./docker-run.sh ./gradlew tasks
```

---

## Manual Docker Command Reference

If you prefer running Docker commands directly without using the helper scripts:

```bash
docker run --rm \
  --user root \
  -v "$PWD":/workspace \
  -w /workspace \
  -e GRADLE_USER_HOME=/workspace/.gradle \
  mobiledevops/android-sdk-image:34.0.0 \
  sh -c "./gradlew assembleDebug && chown -R $(id -u):$(id -g) /workspace/.gradle /workspace/app/build"
```

---

## Testing the Application

1. **On a Connected Device / Emulator:** If you have an Android device connected via USB with USB debugging enabled, install the debug build directly using:
   ```bash
   ./gradlew installDebug
   ```
2. **Manual Installation:** Transfer the generated `dist/personal-app-debug.apk` onto your Android device and tap it to install and test.

---

## Documentation References (SDD)
- **Specifications Root:** [.specs/README.md](.specs/README.md)
- **System Architecture & Stack:** [.specs/system-architecture.md](.specs/system-architecture.md)
- **Domain Model & Entities:** [.specs/domain-model.md](.specs/domain-model.md)
- **Feature Specs & Plans:** [.specs/features/README.md](.specs/features/README.md)
  - [Main Menu & Version Spec](.specs/features/menu/menu.spec.md) | [Plan](.specs/features/menu/menu.plan.md)
  - [Test 1 (JSON Download) Spec](.specs/features/test1/test1.spec.md) | [Plan](.specs/features/test1/test1.plan.md)
  - [Test 2 (Image Download) Spec](.specs/features/test2/test2.spec.md) | [Plan](.specs/features/test2/test2.plan.md)