# personal-android-app

A simple Android application designed for testing and experimenting with modern mobile features, built using Java 17, terminal-based commands, and Docker.

---

## Project Directory Structure

```text
personal-android-app/
├── .gemini/
│   └── context-rules.md    # AGENT CONTEXT RULES
│   └── system-prompt.md    # AGENT SYSTEM PROMPT
├── specs/
│   ├── README.md           # Documentation for system specifications
│   └── example-spec.md     # Example specification for a test feature
├── features/
│   ├── README.md           # Documentation for user-facing features
│   └── example-feature.md  # Example feature blueprint
├── app/                    # Main Android application module
├── AGENTS.md               # AGENTS RULES AND CONSTRAINTS
├── build.gradle            # Project-level Gradle configuration
├── settings.gradle         # Project settings
└── gradlew                 # Gradle wrapper script
```

---

## Prerequisites

To build, test, and run this project, you **do not** need Android Studio installed locally. You only need:
1. **Docker** installed and running on your machine.
2. **Git** to clone and manage the repository.

---

## Getting Started & Terminal Workflows (Using Docker)

Since this project avoids Android Studio, all compilation and building are handled via command-line tools running inside a Docker container pre-configured with the Android SDK and Java 17.

### 1. Run a Command Inside the Docker Container
You can spin up an interactive or one-shot Docker container containing the Android SDK and Java 17 to execute Gradle commands. For example, using an official OpenJDK/Android SDK image:

```bash
docker run --rm -v "$(pwd)":/workspace -w /workspace mobileci/android-sdk:ubuntu java -version
```

### 2. Clean the Project
To clean previous build artifacts:
```bash
docker run --rm -v "$(pwd)":/workspace -w /workspace mobileci/android-sdk:ubuntu ./gradlew clean
```

### 3. Compile and Build Debug APK
To compile the project and build a debug APK for testing:
```bash
docker run --rm -v "$(pwd)":/workspace -w /workspace mobileci/android-sdk:ubuntu ./gradlew assembleDebug
```
*Once finished, the debug APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`*

### 4. Build Release APK (Exporting)
To generate a production-ready release APK:
```bash
docker run --rm -v "$(pwd)":/workspace -w /workspace mobileci/android-sdk:ubuntu ./gradlew assembleRelease
```
*The release APK will be located at: `app/build/outputs/apk/release/app-release.apk`*

### 5. Run Unit Tests
To execute automated unit tests:
```bash
docker run --rm -v "$(pwd)":/workspace -w /workspace mobileci/android-sdk:ubuntu ./gradlew test
```

---

## Testing the Application

1. **On an Emulator/Physical Device:** If you have an Android device connected via USB with USB Debugging enabled (or a running local emulator accessible from your host), you can install the debug build directly using:
   ```bash
   ./gradlew installDebug
   ```
2. **Manual Installation:** Transfer the generated `app-debug.apk` from the `app/build/outputs/apk/debug/` folder onto your physical Android device and tap it to install and test.