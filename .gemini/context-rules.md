# Context Rules & Technical Constraints

## Tech Stack & Language Standards
- **Primary Language:** Kotlin (with full interoperability support for Java 17).
- **Target JVM:** Java 17 bytecode compatibility (`compileOptions` with `sourceCompatibility = VERSION_17`, `targetCompatibility = VERSION_17`, and Kotlin `jvmTarget = "17"`).

## Architecture & Best Practices
- **Architecture Pattern:** Follow the official Android Architecture Guidelines (MVVM - Model-View-ViewModel). Separate UI components from business logic (ViewModels) and data repositories.
- **Modern UI:** Prefer **Jetpack Compose** for building declarative user interfaces.
- **Asynchronous Operations:** Use **Kotlin Coroutines** and **Flow** for background tasks, threading, and reactive data streams instead of blocking main threads.
- **Resource Management:** Never hardcode strings, dimensions, or colors; use centralized resource files (`strings.xml`, `dimens.xml`, `colors.xml`).
- **Error Handling:** Implement robust exception handling and meaningful logging using Android's standard `Log` utility.

## Build, Compile & Execution Workflow (Docker-Preferred)
- **No Local Android Studio Dependency:** All project compilation, packaging, and testing must be executed via command-line tools.
- **Docker-Preferred Builds:** Always recommend and prefer running Gradle commands inside the official container image:
  - Docker Image: `mobiledevops/android-sdk-image:34.0.0`
  - Example wrapper command: `docker run --rm -v "$PWD":/workspace -w /workspace mobiledevops/android-sdk-image:34.0.0 ./gradlew assembleDebug`