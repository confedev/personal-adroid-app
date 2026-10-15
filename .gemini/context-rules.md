# Context Rules & Technical Constraints

## Language & Runtime
- **Java Version:** Java 17 must be used for all development, language features, Gradle configurations, and execution environments.
- **Code Language:** All generated source code, class names, variable names, documentation, and inline comments must be strictly written in **English**.

## Build & Environment Constraints
- **Docker First:** All compilation, APK packaging, and testing tasks **MUST** be performed using Docker containers equipped with Java 17 and Android SDK tools. Do not rely on local Android Studio installations.
- **Gradle Commands:** Use standard Gradle wrapper commands executed via terminal/Docker:
  - Clean project: `./gradlew clean`
  - Build Debug APK: `./gradlew assembleDebug`
  - Build Release APK: `./gradlew assembleRelease`
  - Unit Tests: `./gradlew test`
  - Instrumentation Tests: `./gradlew connectedAndroidTest`
  - Install on Device: `./gradlew installDebug`

## Architecture & Code Standards
- **Architecture:** Implement **MVVM (Model-View-ViewModel)**. Keep UI logic, ViewModels, and Repository data layers strictly separated.
- **User Interface:** Prefer **Jetpack Compose** for declarative UI development over legacy XML layouts, unless explicitly required.
- **Asynchrony:** Keep the UI thread unblocked using safe concurrency management (`CompletableFuture`, Executors, or background threads).
- **Resource Management:** Hardcoded strings, colors, or dimensions are strictly forbidden. Use `strings.xml`, `colors.xml`, and appropriate resource files.
- **Error Handling:** Use explicit exception handling and log debug events using Android's standard `Log` utility. Avoid unhandled crashes or silent failures.

## Directory Structure Expectations
- `.antigravity/` for agent configuration files.
- `specs/` for technical specifications and architecture design documents.
- `features/` for user story and feature breakdowns.
- `app/` for the main Android module.