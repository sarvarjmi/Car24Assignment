# Cars24 SDUI Assignment

A production-quality, extensible **Server-Driven UI (SDUI)** framework for Android, built using Jetpack Compose and Clean Architecture.

## 🚀 Overview

This project demonstrates a complete SDUI ecosystem where the entire UI of the Home Screen is driven by JSON payloads. Instead of a traditional backend, this app uses **Room Database as a Local SDUI Server**, enabling:
- **Dynamic Rendering**: UI updates without app releases.
- **Live Editing**: A built-in Server Panel to edit JSON and see changes instantly.
- **Offline Support**: The UI is persisted and observed via Room.

## 🏗️ Architecture

The project follows **Clean Architecture** with a modular structure and **MVI** (Model-View-Intent) pattern for the presentation layer.

### Module Breakdown
- `:app`: Application bootstrap, Hilt setup, and Navigation Host.
- `:core:core-common`: Shared utilities, Dispatchers, and `DataResult` wrappers.
- `:core:core-ui`: Design System tokens (Color, Typography, Spacing) and shared components.
- `:core:core-model`: Immutable Domain models and Repository contracts.
- `:core:core-database`: Room persistence, entities, DAOs, and Mappers.
- `:core:core-json`: SDUI infrastructure (Parser and Validator).
- `:core:core-navigation`: Decoupled, type-safe navigation system.
- `:feature:feature-renderer`: The heart of the app. Contains the Registry and Recursive Renderer.
- `:feature:feature-landing`: Entry point screen.
- `:feature:feature-home`: Dynamic screen hosting the SDUI Renderer.
- `:feature:feature-server`: Local CMS for editing component JSON.

## 🛠️ Key Features

- **Component Registry**: Maps JSON types (e.g., `"banner"`) to Compose widgets.
- **Recursive Rendering**: Supports nested layouts like Columns and Rows defined in JSON.
- **Action Engine**: JSON-driven interactions (e.g., click to navigate).
- **Graceful Fallback**: Unknown components render a fallback UI instead of crashing.
- **Reactive Updates**: Uses Kotlin Flow to propagate database changes instantly to the UI.

## 📱 How to Use

1. **Launch**: Start at the Landing Screen.
2. **View Home**: Click "Open Home" to see the dynamic layout seeded from `home.json`.
3. **Edit UI**: 
   - Navigate to "Open Server Panel".
   - Enter a Component ID (e.g., `banner_1`).
   - Click **Load** to fetch the current JSON.
   - Modify the `title` or `subtitle` in the JSON text field.
   - Click **Save Changes**.
4. **Verify**: Go back to the Home Screen to see the updates immediately.

## 🧪 Testing

The project includes a comprehensive unit testing suite covering:
- **Validators & Parsers**: Ensuring JSON integrity.
- **Mappers**: Verifying data transformation between Room and Domain.
- **Action Engine**: Testing dynamic navigation routing.
- **ViewModels**: Verifying MVI state transitions using Turbine.

Run tests via: `./gradlew test`

---
*Developed as part of the Cars24 Mobile Engineering Assignment.*
