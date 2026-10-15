# Specifications (Source of Truth) 🧠

This directory is the **Source of Truth** for the project, following the **Spec-Driven Development (SDD)** methodology. All architectural guidelines, technical constraints, domain entities, and functional requirements are strictly maintained here before code changes are executed.

---

## Directory Structure

```text
.specs/
├── system-architecture.md        # General architecture, technical stack, layer boundaries, and storage strategy
├── domain-model.md               # Domain model, entities, state aggregates, and persistence contracts
│
└── features/                     # Functional specifications and execution plans per feature
    ├── menu/
    │   ├── menu.spec.md          # Requirements and flows for landing menu and version display
    │   └── menu.plan.md          # Execution plan for menu navigation and BuildConfig versioning
    ├── test1/
    │   ├── test1.spec.md         # Requirements, rules, and layout for Test 1 (JSON download)
    │   └── test1.plan.md         # Execution plan for Test 1 implementation and testing
    └── test2/
        ├── test2.spec.md         # Requirements, rules, and layout for Test 2 (Image download & preview)
        └── test2.plan.md         # Execution plan for Test 2 dynamic resolution and Coil preview
```

---

## Core Documents

1. **[System Architecture](system-architecture.md)**
   Comprehensive overview of the application's Clean MVVM architecture, Jetpack Compose UI layer, coroutines, OkHttp networking, scoped storage via Android's `MediaStore`, and the containerized Docker workflow.

2. **[Domain Model](domain-model.md)**
   Definitions of domain models and entities (`DownloadResult`), storage subdirectories (`SUBDIR_TEST1_JSONS`, `SUBDIR_TEST2_IMAGES`), UI state aggregates (`Test1UiState`, `Test2UiState`), and navigation targets (`Screen`).

3. **[Features Directory](features/README.md)**
   Modular specifications (`*.spec.md`) and implementation plans (`*.plan.md`) broken down per feature module.
