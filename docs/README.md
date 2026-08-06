# README.md

<div align="center">

# 🚗 Cars24 Android Assignment
### Server-Driven UI (SDUI) using Kotlin, Jetpack Compose & Clean Architecture

**Production-Ready Android Architecture**  
**Offline First • Room as Local Server • Dynamic UI Rendering • Clean Architecture • MVI**

---

![Kotlin](https://img.shields.io/badge/Kotlin-2.x-purple)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-blue)
![Architecture](https://img.shields.io/badge/Clean%20Architecture-MVI-success)
![Database](https://img.shields.io/badge/Room-Offline%20First-green)
![Status](https://img.shields.io/badge/Status-Production%20Ready-success)

</div>

---

# Overview

This project is a **production-grade Android application** built using **Kotlin**, **Jetpack Compose**, and **Clean Architecture**.

Unlike traditional Android applications, the Home Screen is **not hardcoded**.

Instead, the UI is rendered dynamically using a **Server-Driven UI (SDUI)** architecture where the complete screen is described by JSON.

Since this assignment does not include a real backend server, **Room Database acts as the Local SDUI Server**.

Users can edit the stored JSON through a built-in **Server Panel**, save the changes, and immediately see the updated Home Screen without restarting the application.

---

# Assignment Objectives

This project demonstrates:

- Server Driven UI Architecture
- Clean Architecture
- SOLID Principles
- MVI Architecture
- Modular Project Structure
- Offline First Design
- Generic JSON Renderer
- Dynamic Widget Rendering
- Production Ready Codebase

---

# Features

## Landing Screen

Native Compose screen.

Contains:

- Open Home
- Open Local Server

---

## Home Screen

The Home Screen is rendered completely from JSON stored in Room.

No business widgets are manually implemented.

Supports:

- Dynamic Layouts
- Dynamic Widgets
- Dynamic Styling
- Dynamic Actions
- Automatic Updates

---

## Local SDUI Server

Room Database simulates a backend server.

Features:

- Screen Selector
- Section Selector
- Component Selector
- Widget Editor
- JSON Preview
- Validation
- Save
- Reset

Every save automatically updates the Home screen.

---

# Architecture

```text
Presentation

↓

Domain

↓

Data

↓

Room Database

↓

JSON Parser

↓

Renderer Engine

↓

Compose Widgets
```

The project follows strict **Clean Architecture**.

---

# Project Modules

```text
app/

core-common/

core-ui/

core-designsystem/

core-navigation/

core-renderer/

core-json/

core-database/

data/

domain/

feature-landing/

feature-home/

feature-server/
```

Each module has a single responsibility.

---

# Technology Stack

## Language

- Kotlin

---

## UI

- Jetpack Compose
- Material 3

---

## Architecture

- Clean Architecture
- MVI
- Repository Pattern

---

## Dependency Injection

- Hilt

---

## Database

- Room

---

## JSON

- Kotlinx Serialization

---

## Async

- Coroutines
- Flow

---

## Navigation

- Navigation Compose

---

## Testing

- JUnit
- Compose UI Testing
- MockK (or equivalent stable mocking library)
- Turbine

---

# Project Structure

```text
app

├── feature-landing

├── feature-home

├── feature-server

├── domain

├── data

├── core-renderer

├── core-json

├── core-database

├── core-ui

├── core-designsystem

├── core-navigation

└── core-common
```

---

# SDUI Flow

```text
Assets

↓

Initial Seeder

↓

Room Database

↓

Repository

↓

Domain Models

↓

Renderer Engine

↓

Compose UI
```

Assets are used only once.

After first launch, Room becomes the Single Source of Truth.

---

# Local Server Flow

```text
Server Panel

↓

Edit Widget

↓

Generate JSON

↓

Validate

↓

Room Database

↓

Flow

↓

Home Screen Updated
```

---

# Home Screen Flow

```text
Room Flow

↓

Repository

↓

UseCase

↓

Home ViewModel

↓

RendererHost

↓

Component Registry

↓

Compose
```

---

# Widget Rendering

The Renderer Engine dynamically renders widgets from JSON.

Supported widgets include:

### Layout

- Column
- Row
- Box
- Spacer
- Divider

### Basic

- Text
- Button
- Image
- Icon
- Badge

### Advanced

- Search
- Chip
- ChipGroup
- Hero Banner
- Car Card
- CTA Section
- Footer
- LazyColumn
- LazyRow
- Card

Unknown widgets never crash the application.

---

# Folder Structure

```text
feature-home/

ui/

state/

intent/

event/

reducer/

viewmodel/

renderer/

navigation/

preview/
```

Every feature follows the same architecture.

---

# MVI Flow

```text
Intent

↓

ViewModel

↓

UseCase

↓

Repository

↓

Reducer

↓

State

↓

Compose UI
```

---

# Clean Architecture

```text
Presentation

↓

Domain

↓

Data

↓

Infrastructure
```

Dependency rule:

Only inward dependencies are allowed.

---

# Offline First

The application works completely offline.

Room Database acts as:

- Local Backend
- Local Cache
- Single Source of Truth

---

# Room Database

Database stores

```text
Screen JSON

Version

Metadata

Timestamp
```

Flow updates automatically notify the Home screen.

---

# JSON Validation

Every JSON update goes through:

```text
Parse

↓

Normalize

↓

Validate

↓

Map

↓

Save
```

Invalid JSON is rejected.

---

# Error Handling

Application handles

- Invalid JSON
- Unknown Widget
- Invalid Action
- Invalid Style
- Empty Screen
- Database Errors
- Validation Errors

Gracefully.

---

# Testing

The project contains

## Unit Tests

- Parser
- Validators
- Repositories
- UseCases
- ViewModels

---

## UI Tests

- Landing
- Home
- Server Panel

---

## Integration Tests

- Room
- Renderer
- Navigation
- Flow
- JSON Update

---

# Accessibility

Supports

- TalkBack
- Dynamic Fonts
- Content Descriptions
- Material Accessibility
- Minimum Touch Targets

---

# Coding Standards

The project follows

- Kotlin Coding Conventions
- SOLID Principles
- Clean Code
- Immutable State
- Stateless UI
- One-way Data Flow

---

# Build Requirements

- Android Studio (latest stable)
- JDK 17
- Android Gradle Plugin (stable)
- Gradle (stable)
- Kotlin (stable)

All dependency versions are managed through:

```text
gradle/libs.versions.toml
```

---

# Build Commands

```bash
./gradlew clean
```

```bash
./gradlew assembleDebug
```

```bash
./gradlew test
```

```bash
./gradlew connectedAndroidTest
```

```bash
./gradlew lint
```

---

# Assignment Workflow

```text
Launch

↓

Landing

↓

Home

↓

Dynamic SDUI

OR

↓

Local Server

↓

Edit JSON

↓

Save

↓

Room

↓

Flow

↓

Home Updated
```

---

# Documentation

The project includes a complete documentation suite.

| Document Range | Description |
|---------------|-------------|
| 00–18 | Foundation & Architecture |
| 19–28 | SDUI, Renderer & Design |
| 29–41 | Implementation Phases |
| 42 | Coverage Matrix |
| 43 | AI Development Workflow |

---

# Production Features

- Clean Architecture
- MVI
- Offline First
- Room Local Server
- Dynamic SDUI
- Generic Renderer
- Generic JSON Parser
- Component Registry
- Design System
- Stable Libraries Only
- Production-Ready Structure

---

# Future Enhancements

The architecture supports future additions without major redesign:

- Remote API synchronization
- Multiple SDUI screens
- Import/Export JSON
- Widget drag-and-drop
- Undo/Redo
- Draft mode
- Theme editor
- Analytics
- Feature flags

---

# Assignment Deliverables

This repository contains:

- Complete Android Source Code
- Modular Architecture
- Documentation Suite
- JSON Samples
- Gradle Configuration
- Unit Tests
- UI Tests
- Integration Tests

---

# Final Verification Checklist

Before submission, verify:

- [ ] Project builds successfully
- [ ] No unstable libraries
- [ ] Clean Architecture maintained
- [ ] SOLID principles followed
- [ ] MVI implemented
- [ ] Home rendered from Room JSON
- [ ] Local Server edits JSON
- [ ] Live updates working
- [ ] Tests passing
- [ ] Documentation updated

---

# License

This project was created as part of an Android engineering assignment for demonstration and evaluation purposes.

---

<div align="center">

## Thank You

**Built using Kotlin • Jetpack Compose • Room • Hilt • Clean Architecture • MVI • Server-Driven UI**

</div>