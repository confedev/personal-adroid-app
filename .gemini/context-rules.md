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

## Spec-Driven Development (SDD) Pattern & Rules
- **Single Source of Truth (`.specs/`):** All requirements, architecture guidelines, and domain models are strictly defined in `.specs/` before implementing code.
- **SDD Structure Standards:**
  - `.specs/system-architecture.md`: Overall system architecture, stack, layer boundaries, and technical decisions.
  - `.specs/domain-model.md`: Domain models, entities, value objects, and reactive UI state aggregates.
  - `.specs/features/<feature>/`: Feature-specific modules. Each feature must maintain:
    - `<feature>.spec.md`: User stories, functional requirements, business rules, and UI interaction flows.
    - `<feature>.plan.md`: Step-by-step execution and technical implementation plan for AI agents or developers.
- **Specification-First Rule:** Never introduce new features or change business logic without first updating or defining the corresponding `.spec.md` and `.plan.md` in `.specs/features/`.
- **Documentation Integrity:** Any refactoring or code alteration must maintain synchronization between code, specs, plans, and README files.