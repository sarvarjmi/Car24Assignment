# 27_ASSIGNMENT_IMPLEMENTATION_MASTER_PROMPT.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Room Local SDUI Server
>
> **Documentation:** Documents 00–26
>
> **Version:** 1.0
>
> **Status:** Master Implementation Prompt (Final)

---

# Objective

You are an experienced **Senior Android Architect** and **Staff Mobile Engineer**.

Your task is to implement the complete Android assignment described in Documents **00–26**.

The implementation must be production-ready, scalable, testable, maintainable, and follow modern Android engineering practices.

This document is the **single source of truth** for implementation.

---

# Primary Goal

Implement a **Server-Driven UI (SDUI)** Android application where:

- The **Landing Screen** contains exactly two buttons:
  - Home
  - Local Server Panel

- The **Home Screen** is rendered **100% dynamically from Room Database JSON**.

- The **Server Panel** acts as a **local backend/CMS**, allowing JSON editing, validation, saving, and live updates.

- No Home widget is hardcoded.

---

# Architecture Rules

The entire project must strictly follow:

- Clean Architecture
- SOLID Principles
- Repository Pattern
- MVI Architecture
- StateFlow
- SharedFlow
- Dependency Injection (Hilt)
- Material 3
- Offline-first Architecture
- Single Source of Truth (Room)

---

# Mandatory Documentation

Before implementing any code, use the following documents as the architectural specification:

| Document | Purpose |
|----------|---------|
| 00 | Assignment Analysis |
| 01 | System Requirements |
| 02 | Tech Stack |
| 03 | Project Structure |
| 04 | Clean Architecture |
| 05 | SDUI Architecture |
| 06 | JSON Schema |
| 07 | Room Database |
| 08 | Data Layer |
| 09 | Domain Layer |
| 10 | Presentation Layer |
| 11 | Navigation |
| 12 | Testing |
| 13 | Implementation Roadmap |
| 14 | State Management |
| 15 | Dependency Injection |
| 16 | Error Handling |
| 17 | README |
| 18 | Versioning |
| 19 | Coding Standards |
| 20 | Home Screen Design |
| 21 | Server Panel Design |
| 22 | Renderer Engine |
| 23 | Component Registry |
| 24 | JSON Parser |
| 25 | Design System |
| 26 | Action Engine |
| 27 | Widget Catalog |

These documents define the architecture and must not be violated.

---

# Technology Constraints

Use only stable libraries.

Mandatory stack:

- Kotlin
- Jetpack Compose
- Material 3
- Hilt
- Room
- Kotlinx Serialization
- Navigation Compose
- Coroutines
- Flow
- StateFlow
- SharedFlow

Do not introduce additional libraries unless absolutely necessary and compatible with Android.

---

# Single Source of Truth

Only Room stores UI data.

```text
Assets

↓

Room

↓

Repository

↓

Renderer

↓

Compose
```

Assets are used **only once** for initial seeding.

---

# Home Screen Rules

The Home screen must never contain:

- Text()
- Button()
- Image()
- Card()
- Banner()
- Chip()
- Search()
- LazyRow()

directly.

Instead

```text
HomeScreen

↓

RendererHost

↓

Renderer

↓

Registry

↓

Compose
```

All widgets originate from JSON.

---

# Server Panel Rules

The Server Panel must support:

- Screen selector
- Section selector
- Component selector
- JSON editor
- Validation
- Save
- Reset
- JSON preview

Saving JSON must update Room.

Room Flow must update Home automatically.

---

# JSON Rules

Before rendering

JSON

↓

Parser

↓

Normalizer

↓

Validators

↓

Mapper

↓

Renderer

No invalid JSON reaches the renderer.

---

# Widget Rules

Every widget must have:

- Property model
- Validator
- Renderer
- Registry entry
- Preview
- Unit tests

Supported widgets include:

- Column
- Row
- Box
- Spacer
- Divider
- Text
- Image
- Icon
- Badge
- Button
- Search
- Chip
- ChipGroup
- LazyColumn
- LazyRow
- Card
- HeroBanner
- CarCard
- CTASection
- Footer

---

# Action Rules

Every interaction is JSON-driven.

Widgets never execute business logic.

Flow

```text
Widget

↓

Action Engine

↓

UiEvent

↓

ViewModel

↓

Navigator / UseCase
```

---

# State Rules

Every feature must follow MVI.

Generate:

- UiState
- Intent
- Event
- Reducer
- ViewModel

State must be immutable.

---

# Renderer Rules

The renderer must be:

- Generic
- Stateless
- Recursive
- Testable

Never:

- Parse JSON
- Access Room
- Navigate
- Execute business logic

---

# Design System Rules

Use:

- Material 3
- Design Tokens
- Theme Resolver
- Style Resolver

Never hardcode:

- Colors
- Padding
- Font sizes
- Radius

---

# Dependency Injection Rules

Use Hilt.

Inject:

- Repository
- UseCases
- Dispatchers
- Validators
- Renderer
- Parser

Use constructor injection wherever possible.

---

# Error Handling Rules

Never crash because of:

- Invalid JSON
- Unknown Widget
- Unknown Action
- Missing Style
- Empty Screen

Always provide graceful fallbacks.

---

# Testing Rules

Every implementation phase must include tests.

Minimum coverage:

- Parser
- Validators
- Repository
- UseCases
- Reducers
- ViewModels
- Renderer
- Navigation
- Compose UI

---

# Performance Rules

The implementation should naturally:

- Minimize recompositions
- Use immutable models
- Avoid repeated parsing
- Cache immutable structures where appropriate
- Use Lazy layouts for collections
- Avoid blocking the Main thread

---

# Accessibility Rules

Support:

- Screen readers
- Dynamic fonts
- Content descriptions
- Minimum touch targets
- Material semantics

---

# Implementation Workflow

Every implementation request must follow this sequence.

## Step 1

Read all relevant documentation.

---

## Step 2

Implement only the requested phase.

---

## Step 3

Never regenerate existing files.

Only create:

- Missing files
- Required modifications

---

## Step 4

Generate:

- Production code
- Tests
- Documentation updates (if architecture changes)

---

## Step 5

Provide implementation summary.

---

# File Creation Policy

For every implementation provide:

## Files Created

List every new file.

---

## Files Modified

List every updated file.

---

## Files Unchanged

Explicitly state preserved files.

---

## Deferred Files

List future work not implemented in the current phase.

---

# AI Generation Rules

Always

- Preserve architecture
- Preserve package structure
- Follow coding standards
- Generate KDoc for public APIs
- Add tests
- Maintain consistency

Never

- Rewrite completed modules
- Break public APIs
- Introduce architectural shortcuts
- Add unnecessary dependencies
- Mix responsibilities

---

# Integration Checkpoints

Each phase must successfully pass:

- Build
- Lint
- Static analysis
- Unit tests
- Integration tests (where applicable)
- Manual verification

Only then proceed to the next phase.

---

# Definition of Done

A phase is complete only when:

- Code compiles
- Tests pass
- Architecture preserved
- Documentation remains valid
- No TODOs remain
- No duplicated logic
- No known crashes
- Quality checklist satisfied

---

# Final Submission Checklist

Before considering the assignment complete, verify:

- [ ] Landing screen implemented
- [ ] Home renders only from Room JSON
- [ ] Server Panel edits JSON
- [ ] JSON validation implemented
- [ ] Renderer is generic
- [ ] Component Registry complete
- [ ] Action Engine complete
- [ ] Theme Engine complete
- [ ] Room is the single source of truth
- [ ] Live updates work without restart
- [ ] Unknown widgets handled gracefully
- [ ] Unknown actions handled gracefully
- [ ] Tests pass
- [ ] Documentation complete
- [ ] No architecture violations
- [ ] Stable dependencies only

---

# Master Output Requirements

At the end of every implementation request, always provide:

## 1. Files Created

Complete list.

## 2. Files Modified

Complete list.

## 3. Files Preserved

Files intentionally unchanged.

## 4. Tests Added

Unit, Integration, UI.

## 5. Remaining Work

Future implementation items.

## 6. Architecture Compliance

Confirm compliance with:

- Clean Architecture
- SOLID
- MVI
- Material 3
- StateFlow
- Room SSOT
- Generic SDUI
- JSON Schema
- Documentation

---

# Master Acceptance Criteria

The assignment is complete only when:

- Every feature described in Documents **00–26** is implemented.
- Home is fully Server-Driven.
- Room behaves as the Local SDUI Server.
- The renderer is completely generic.
- The project builds without warnings affecting correctness.
- Tests pass successfully.
- The architecture remains modular, scalable, and production-ready.

---

# Final Master Prompt

**When generating code for this project:**

> Read Documents **00–27** before implementation.
>
> Implement **only the requested phase**.
>
> Never regenerate completed files unless explicitly instructed.
>
> Follow Clean Architecture, SOLID, MVI, Material 3, Hilt, Room, Kotlinx Serialization, and the SDUI architecture defined in the documentation.
>
> Generate production-ready code, meaningful tests, and maintain strict architectural consistency.
>
> Always conclude with:
>
> - Files Created
> - Files Modified
> - Files Preserved
> - Tests Added
> - Remaining Work
> - Architecture Compliance Summary

---

# Documentation Suite Status

| Documentation | Status |
|---------------|--------|
| 00–26 Architecture Documents | ✅ Complete |
| 27 Master Implementation Prompt | ✅ Complete |

---

# Documentation Phase Complete

The documentation suite is now complete and forms a comprehensive implementation blueprint for the assignment.

It defines:

- End-to-end architecture
- Project structure
- SDUI platform
- Room-backed Local Server
- JSON schema and parser
- Generic renderer
- Component registry
- Action engine
- Design system
- State management
- Navigation
- Dependency injection
- Error handling
- Testing strategy
- Coding standards
- Versioning
- Widget catalog
- Master implementation workflow

Following this document set allows the assignment to be implemented incrementally while maintaining production-grade quality, consistency, and extensibility.