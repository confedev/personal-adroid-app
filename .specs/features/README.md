# Features Specification & Execution Plans

This directory contains functional specifications and implementation plans for the application's user-facing features, organized according to the **Spec-Driven Development (SDD)** standard.

---

## Structure Convention

Each feature is organized in its own subdirectory containing:
1. `*.spec.md`: User stories, requirements, business rules, and UI/interaction flows.
2. `*.plan.md`: Technical mapping, architecture decisions, and step-by-step execution guides for AI agents or developers.

```text
.specs/features/
├── menu/
│   ├── menu.spec.md          # Requirements & business rules for landing menu and version display
│   └── menu.plan.md          # Implementation plan for menu navigation and BuildConfig integration
├── test1/
│   ├── test1.spec.md         # Requirements & rules for JSON download and timestamp storage
│   └── test1.plan.md         # Implementation plan for OkHttp streaming and MediaStore JSON storage
└── test2/
    ├── test2.spec.md         # Requirements for image download, MIME resolution, and Coil preview
    └── test2.plan.md         # Implementation plan for dynamic filename resolution and image rendering
```

---

## Features Index

| Feature | Spec | Plan | Description |
| :--- | :--- | :--- | :--- |
| **Main Menu** | [menu.spec.md](./menu/menu.spec.md) | [menu.plan.md](./menu/menu.plan.md) | Landing screen with navigation options and dynamic compile-time version display |
| **Test 1 (Get JSON)** | [test1.spec.md](./test1/test1.spec.md) | [test1.plan.md](./test1/test1.plan.md) | Download arbitrary JSON payloads via GET and store with `yyyyMMdd_HHmmss.json` |
| **Test 2 (Get Image)** | [test2.spec.md](./test2/test2.spec.md) | [test2.plan.md](./test2/test2.plan.md) | Download images with dynamic filename/extension resolution and live Coil preview |
