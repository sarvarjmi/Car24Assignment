# 30_PHASE_02_CORE_COMMON.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 02 – Core Common Infrastructure
>
> **Module:** `core-common`
>
> **Architecture:** Clean Architecture + MVI
>
> **Status:** Implementation Phase 02
>
> **Estimated Time:** 4–6 Hours
>
> **Priority:** Critical
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap Completed

---

# Phase Objective

This phase establishes the **shared foundation** used by every module in the application.

The `core-common` module must remain **100% platform-independent** and should **not depend on Android framework classes** unless absolutely required.

Everything inside this module should be reusable across:

- Data Layer
- Domain Layer
- Presentation Layer
- Renderer
- JSON Engine
- Server Panel
- Future feature modules

This module becomes the backbone of the project.

---

# Module Responsibility

The module provides

- Shared Result Wrapper
- Error Models
- Dispatcher Provider
- Coroutine Utilities
- Logger Abstraction
- Constants
- Shared Extensions
- Utility Classes
- Validation Helpers
- Base Interfaces
- Common Models
- Shared Annotations

It **must never contain business logic**.

---

# Architecture Position

```text
Presentation

↓

Domain

↓

Data

↓

core-common
```

Every layer depends on `core-common`.

`core-common` depends on **nothing** except Kotlin standard libraries and Coroutines.

---

# Folder Structure

```text
core-common/

src/main/kotlin/

com.assignment.core.common/

├── result/

├── error/

├── dispatcher/

├── logger/

├── constants/

├── extensions/

├── util/

├── model/

├── validation/

├── exception/

├── annotations/

├── interfaces/

└── coroutine/
```

---

# Package Responsibilities

## result/

Contains

- Result Wrapper
- Success
- Failure

---

## error/

Contains

- AppError
- ErrorCode
- ErrorCategory

---

## dispatcher/

Contains

- DispatcherProvider
- DefaultDispatcherProvider

---

## logger/

Contains

- Logger interface

No implementation.

---

## constants/

Contains

- AppConstants
- JsonConstants
- NavigationConstants
- ValidationConstants

---

## extensions/

Contains only extension functions.

Examples

- String
- Collection
- Flow
- Result
- Throwable

---

## util/

Contains

Pure utility classes.

No Android dependencies.

---

## model/

Contains

Reusable lightweight models.

No feature models.

---

## validation/

Contains

Generic validation helpers.

No business validation.

---

## exception/

Contains

Custom exceptions.

Only unexpected failures.

---

## interfaces/

Contains

Shared interfaces.

Examples

- Mapper
- Validator
- Initializer

---

## coroutine/

Contains

Coroutine helper utilities.

---

# Result Wrapper

Create a generic Result hierarchy.

Requirements

- Generic
- Immutable
- Exhaustive
- Easy to use with Flow

Support

- Success
- Failure

Never use Kotlin's built-in `Result` in public APIs.

---

# Error Model

Create a centralized error hierarchy.

Examples

```text
AppError

├── Network

├── Database

├── Json

├── Validation

├── Unknown
```

Errors should carry

- Code
- Message
- Cause (optional)

No Android resources.

---

# Dispatcher Provider

Create abstraction.

Support

- Main
- IO
- Default
- Unconfined

Inject everywhere.

Never call `Dispatchers.IO` directly.

---

# Logger

Create interface only.

Responsibilities

- Debug
- Info
- Warning
- Error

Implementation added later.

Presentation layer should not know the implementation.

---

# Constants

Split constants by responsibility.

Examples

```text
AppConstants

JsonConstants

DatabaseConstants

RendererConstants

ValidationConstants
```

Avoid one giant constants file.

---

# Extensions

Allowed

- String
- Number
- Collection
- Flow
- Throwable
- Result
- CoroutineScope

Not allowed

- Feature-specific extensions
- Android UI extensions

---

# Validation Helpers

Generic utilities

Examples

- isNullOrBlank
- requireNotEmpty
- safeBoolean
- safeNumber

No SDUI-specific validation.

---

# Utility Classes

Examples

- IdGenerator
- TimeProvider
- UuidProvider
- DateFormatter (if platform-independent)

No singleton objects with mutable state.

---

# Mapper Interface

Provide generic interface.

Purpose

```text
Input

↓

Output
```

Reusable across Data Layer.

---

# Validator Interface

Provide generic validation contract.

Returns

- Valid
- Invalid

Never throw for expected validation failures.

---

# Initializer Interface

Used for

- Seeders
- Registry Initialization
- Future Startup Tasks

---

# Common Models

Examples

- Pagination (future)
- Coordinates (future)
- KeyValue
- OptionItem

Only generic models.

---

# Exception Strategy

Create only domain-independent exceptions.

Examples

- InvalidStateException
- MappingException
- ValidationException

Avoid excessive custom exceptions.

---

# Coroutines

Provide

- DispatcherProvider
- Coroutine helpers

Do not create scopes here.

---

# Thread Safety

All utilities must be

- Stateless
- Immutable
- Thread-safe

---

# Dependency Rules

Allowed

- Kotlin Stdlib
- Coroutines

Not Allowed

- Compose
- Room
- Navigation
- Android Context
- Material3
- ViewModel

---

# Testing Strategy

Unit Tests

- Result Wrapper
- Error Models
- DispatcherProvider
- Validators
- Extensions
- Utilities
- Mappers

Target high coverage.

---

# Best Practices

Always

- Immutable classes
- Constructor injection
- Small focused files
- Generic abstractions

Never

- Business logic
- Android APIs
- Feature-specific models
- Mutable global state

---

# Acceptance Criteria

Phase 02 is complete when

- Result wrapper implemented
- Error hierarchy implemented
- Dispatcher abstraction created
- Logger interface created
- Constants organized
- Extensions added
- Generic interfaces added
- Utilities added
- Tests passing
- No Android dependency introduced

---

# Common Pitfalls

Avoid

- Using Android Context
- Accessing Room
- ViewModel references
- Feature models
- Navigation classes
- Mutable singleton objects
- Hardcoded dispatchers
- Kotlin `Result` in architecture APIs

---

# Definition of Done

- Module compiles
- Unit tests pass
- No Android dependency
- Used as foundation by every layer
- Architecture boundaries maintained

---

# Production AI Prompt

## Objective

Implement the complete **core-common** module.

### Create Packages

- result
- error
- dispatcher
- logger
- constants
- extensions
- util
- validation
- exception
- interfaces
- model
- coroutine
- annotations

### Generate

#### Result

- Result
- Success
- Failure

#### Errors

- AppError
- ErrorCode
- ErrorCategory

#### Dispatcher

- DispatcherProvider
- DefaultDispatcherProvider

#### Logger

- Logger interface

#### Interfaces

- Mapper
- Validator
- Initializer

#### Utilities

- IdGenerator
- TimeProvider
- UuidProvider

#### Extensions

Generate only reusable Kotlin extensions.

#### Constants

Split by responsibility.

### Requirements

- Kotlin only
- Platform-independent
- Immutable
- Thread-safe
- SOLID compliant
- Clean Architecture compliant
- Constructor injection ready
- No Android dependency
- No business logic
- No feature-specific code

### Tests

Generate

- Result tests
- Error tests
- Dispatcher tests
- Validator tests
- Extension tests
- Utility tests

### Output Summary

Provide

- Files created
- Files modified
- Package dependency graph
- Public API list
- Test coverage summary
- Architecture compliance checklist

---

# Phase Dependency

```text
Phase 01

↓

Phase 02 (core-common)

↓

Phase 03 (core-ui)

↓

Remaining Features
```

---

# Phase Completion Checklist

| Item | Status |
|------|--------|
| Result Wrapper | ✅ Planned |
| Error Models | ✅ Planned |
| Dispatcher Provider | ✅ Planned |
| Logger Abstraction | ✅ Planned |
| Constants | ✅ Planned |
| Extensions | ✅ Planned |
| Utilities | ✅ Planned |
| Interfaces | ✅ Planned |
| Testing Strategy | ✅ Planned |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**31_PHASE_03_CORE_UI.md**

This phase will implement the **`core-ui`** module, including:

- Base Compose components
- Reusable loading states
- Error state components
- Empty state components
- AppScaffold
- Toolbar
- Dialog components
- Snackbar host
- Generic UI helpers
- Preview utilities
- UI testing strategy
- Production AI prompt for implementing the complete `core-ui` module.