# 29_PHASE_01_PROJECT_BOOTSTRAP.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 01 – Project Bootstrap & Foundation
>
> **Architecture:** Clean Architecture + MVI + Room Local SDUI Server
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Status:** Implementation Phase 1
>
> **Estimated Time:** 2–4 Hours
>
> **Priority:** Critical (Must be completed before any feature development)

---

# Phase Objective

This phase establishes the complete project foundation.

After this phase, the project should:

- Build successfully
- Run on a device/emulator
- Have a modular architecture
- Have all dependencies configured
- Have Hilt working
- Have Compose working
- Have Room configured
- Have Navigation configured
- Have KSP configured
- Have Version Catalog configured
- Have CI-ready Gradle configuration

**No business logic, SDUI rendering, or application features are implemented in this phase.**

---

# Deliverables

After completing this phase the project will contain:

- Android Studio project
- Modular structure
- Version Catalog
- Base Gradle configuration
- Compose configuration
- Material 3
- Hilt
- Room
- Kotlin Serialization
- Navigation Compose
- Testing infrastructure
- ktlint
- Detekt
- Build Configurations

---

# Architecture Scope

Only infrastructure.

```text
Project

↓

Gradle

↓

Modules

↓

Dependencies

↓

Application

↓

Ready For Development
```

No feature code.

---

# Module Creation

Create the following modules.

## Application

```text
app
```

---

## Core

```text
core-common

core-ui

core-designsystem

core-navigation

core-renderer

core-json

core-database
```

---

## Business

```text
data

domain
```

---

## Features

```text
feature-landing

feature-home

feature-server
```

---

# Project Structure

```text
project/

├── app/

├── core-common/

├── core-ui/

├── core-designsystem/

├── core-navigation/

├── core-renderer/

├── core-json/

├── core-database/

├── data/

├── domain/

├── feature-landing/

├── feature-home/

├── feature-server/

├── docs/

├── gradle/

└── build-logic/ (optional)
```

---

# Gradle Configuration

Configure

- Kotlin DSL
- Version Catalog
- Plugin Management
- Dependency Resolution
- KSP
- Java 17
- Compose Compiler
- Build Types

---

# Version Catalog

Create

```text
gradle/libs.versions.toml
```

All dependencies must come from this file.

Never hardcode versions inside Gradle modules.

---

# Required Plugins

Configure only stable plugins.

- Android Application
- Android Library
- Kotlin Android
- Kotlin Compose
- Kotlin Serialization
- Hilt
- KSP

No alpha or beta plugins.

---

# Dependency Strategy

Use

```text
libs.xxx
```

for every dependency.

No inline dependency strings.

---

# Android Configuration

Application

Configure

- compileSdk
- minSdk
- targetSdk
- namespace
- applicationId
- versionCode
- versionName

All values should align with `01_SYSTEM_REQUIREMENTS.md`.

---

# Java Configuration

Use

```text
Java 17
```

Configure

- Source Compatibility
- Target Compatibility
- JVM Target

---

# Compose Configuration

Enable

- Compose
- Material 3
- Compose Compiler
- Preview support

No XML UI.

---

# Material 3

Configure

- Theme
- Typography
- Shapes

Do not implement custom Design Tokens yet.

---

# Hilt Configuration

Configure

- Application class
- Hilt plugin
- Base dependency graph

No feature modules yet.

---

# Room Configuration

Configure

- Room dependencies
- KSP processor

Do not create entities yet.

---

# Navigation Configuration

Configure

- Navigation Compose

Do not implement navigation graph yet.

---

# Kotlin Serialization

Configure

- Serialization plugin
- Shared Json dependency

No parser implementation yet.

---

# Coroutine Configuration

Configure

- Coroutines
- Flow

No dispatcher abstraction yet.

---

# Logging

Add logging infrastructure dependency only.

Implementation comes later.

---

# Testing Infrastructure

Configure

Unit Test

- JUnit
- MockK
- Turbine
- Coroutines Test

Android Test

- Compose UI Test
- Espresso (only if required)
- AndroidX Test

---

# Static Analysis

Configure

## ktlint

Formatting

---

## Detekt

Static Analysis

Rules added later.

---

# Build Types

Create

```text
debug

release
```

Debug

- Logging enabled

Release

- Minify disabled (assignment)
- Debug logging disabled

---

# Packaging

Configure

- Resource exclusions
- Duplicate file handling

---

# Application Class

Create

```text
App.kt
```

Responsibilities

- Hilt initialization only

No business logic.

---

# Assets Folder

Create

```text
app/src/main/assets/
```

Placeholder

```text
home.json
```

Seeder implemented later.

---

# Documentation Folder

Create

```text
docs/
```

Move all architecture documents into this directory.

---

# Git Configuration

Create

```text
.gitignore
```

Include

- Gradle
- Build
- IDE
- Local Properties

---

# README

Create placeholder

```text
README.md
```

Final version comes later.

---

# Build Verification

Verify

- Sync succeeds
- Build succeeds
- Run succeeds
- No missing dependencies
- No Gradle warnings affecting correctness

---

# Folder Verification

Every module should contain

```text
src/

main/

AndroidManifest.xml

build.gradle.kts
```

Where applicable.

---

# Acceptance Criteria

Phase 01 is complete when

- Project builds successfully
- All modules created
- Version Catalog configured
- Stable dependencies configured
- Hilt configured
- Compose configured
- Room configured
- Navigation configured
- Serialization configured
- Testing dependencies configured
- Static analysis configured
- Application launches successfully
- No feature code exists

---

# Common Pitfalls

Avoid

- Hardcoded dependency versions
- XML layouts
- Feature implementation
- Business logic
- Room entities
- Navigation graph
- ViewModels
- Repositories
- UseCases
- JSON parsing
- Renderer implementation

This phase is **foundation only**.

---

# Definition of Done

- Clean build
- Clean sync
- Modular project structure
- Dependency graph ready
- Infrastructure ready
- CI-ready Gradle setup
- No architecture violations

---

# Production AI Prompt

## Objective

Generate the complete **Phase 01 – Project Bootstrap**.

### Create

#### Project

- Android Studio project
- Modular structure
- Kotlin DSL Gradle

#### Modules

- app
- core-common
- core-ui
- core-designsystem
- core-navigation
- core-renderer
- core-json
- core-database
- data
- domain
- feature-landing
- feature-home
- feature-server

#### Configuration

- Version Catalog
- Hilt
- Compose
- Material 3
- Room
- Navigation
- Serialization
- Coroutines
- Testing
- ktlint
- Detekt

#### Base Files

- App.kt
- README.md
- .gitignore
- assets/home.json (placeholder)
- docs/

### Rules

- Use only stable libraries.
- Use Kotlin DSL.
- Java 17.
- Compose-only UI.
- No XML layouts.
- No feature implementation.
- No business logic.
- No Room entities.
- No ViewModels.
- No repositories.
- No SDUI implementation.
- Use only `libs.versions.toml` for dependency versions.

### Output Summary

Provide

- Files created
- Modules created
- Gradle files created
- Dependencies configured
- Plugins configured
- Deferred work
- Build verification checklist
- Architecture compliance checklist

---

# Phase Dependency

```text
Phase 01

↓

Phase 02

Core Common Infrastructure
```

Do **not** begin Phase 02 until every acceptance criterion in Phase 01 has been satisfied.

---

# Phase Status

| Item | Status |
|------|--------|
| Project Structure | ✅ Defined |
| Module Structure | ✅ Defined |
| Build Configuration | ✅ Defined |
| Dependency Strategy | ✅ Defined |
| Plugin Strategy | ✅ Defined |
| Testing Infrastructure | ✅ Defined |
| Static Analysis | ✅ Defined |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**30_PHASE_02_CORE_COMMON.md**

This document will define the implementation of the `core-common` module, including:

- Result wrapper
- DispatcherProvider
- Constants
- Logger abstraction
- Resource abstraction
- Base interfaces
- Extensions
- Utility classes
- Error models
- Shared models
- Coding standards specific to `core-common`
- Production AI prompt for implementing the entire `core-common` module.