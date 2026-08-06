# Cars24 SDUI Android Assignment

A production-grade, highly extensible Server-Driven UI (SDUI) framework built with Clean Architecture, MVI, and Jetpack Compose.

## 🚀 Key Features

- **Generic SDUI Engine**: Recursive rendering of sections and components using a formal Component Registry.
- **Local SDUI Server**: Room Database acts as the Single Source of Truth (SSOT), simulating a real-time backend.
- **Structured CMS**: A native administration panel to edit JSON payloads via form-based controls, with full validation and Undo/Redo support.
- **Token-Based Design System**: Centralized visual language (Colors, Spacing, Typography, Shapes) mapping JSON tokens to Material 3 values.
- **Action & Event Engine**: Declarative interaction handling (Navigate, Snackbar, BottomSheet, Analytics) with priority-based execution.
- **Zero-Missing Scenarios**: Fully implemented all requirements from 43+ technical documentation files.

## 🛠 Tech Stack

- **Kotlin** & **Jetpack Compose**
- **Clean Architecture** (13 specialized modules)
- **MVI Pattern** (State, Intent, Event, Reducer)
- **Room Persistence** (as a Local Server)
- **Hilt** (Dependency Injection)
- **Kotlinx Serialization** (Polymorphic JSON handling)
- **Coil** (Image Loading)
- **Timber** (Logging)

## 📦 Project Structure

- `:app`: Entry point, Startup management, and NavHost.
- `:core:core-designsystem`: Centralized theme tokens and resolvers.
- `:core:core-ui`: Reusable Compose components, Animations, and Layouts.
- `:core:core-renderer`: The heart of the SDUI engine and Action Dispatcher.
- `:core:core-json`: Parsing, Normalization, and strict Schema Validation.
- `:core:core-database`: Room implementation, Model Mapping, and Initial Seeding.
- `:core:core-domain`: Business logic, UseCases, and Repository Contracts.
- `:core:core-model`: Domain and JSON Data Transfer Objects (DTOs).
- `:feature:feature-home`: Dynamic SDUI-driven Home Screen.
- `:feature:feature-server`: Local Server CMS for real-time JSON evolution.
- `:feature:feature-landing`: Premium animated entry screen.

## 🚦 Getting Started

1. **Clone & Sync**: Open the project in Android Studio (Ladybug or newer).
2. **Initial Seed**: On first launch, the app automatically seeds the database from `assets/home.json` and others.
3. **Explore**:
   - Start from the **Landing Screen**.
   - Navigate to **Home** to see the SDUI in action.
   - Go to **Local Server** to modify any widget's property (e.g., change a Car Card price) and see the Home screen update instantly.

## 🧪 Testing

The project includes a comprehensive test suite:
- **Unit Tests**: Parser validation, Mapper integrity, and UseCase logic.
- **Integration Tests**: Room persistence and Flow-based state updates.
- **UI Tests**: Compose-based feature verification.

Run all tests via: `./gradlew test`

## 📄 Documentation

All 43+ design and phase documents are available in the `/docs` folder, serving as the source of truth for every architectural decision made.

---
Developed for the **Cars24 Mobile Engineering Assignment**.
