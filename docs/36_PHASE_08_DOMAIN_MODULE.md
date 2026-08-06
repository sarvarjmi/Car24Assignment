# 36_PHASE_08_DOMAIN_MODULE.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 08 – Domain Layer
>
> **Module:** `domain`
>
> **Architecture:** Clean Architecture + MVI + SOLID
>
> **Status:** Implementation Phase 08
>
> **Priority:** Critical
>
> **Estimated Time:** 10–14 Hours
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – core-common
> - ✅ Phase 05 – core-json
> - ✅ Phase 06 – core-database
> - ✅ Phase 07 – data
> - 📖 09_DOMAIN_LAYER_DESIGN.md
> - 📖 14_STATE_MANAGEMENT.md
> - 📖 16_ERROR_HANDLING_AND_LOGGING.md

---

# 1. Phase Objective

This phase implements the **Domain Layer**, which contains all business contracts and business rules.

The Domain module is the center of the Clean Architecture.

It must know **nothing** about:

- Room
- Compose
- Hilt
- Navigation
- Android
- JSON
- Feature implementations

Instead it defines:

- Repository Contracts
- UseCases
- Domain Models
- Business Validation
- Business Errors

The Presentation layer communicates **only** with the Domain layer.

---

# 2. Domain Philosophy

```text id="domain01"
Presentation

↓

UseCase

↓

Repository Interface

↓

Data Layer
```

Presentation never calls Repository implementations.

---

# 3. Responsibilities

The Domain module is responsible for

- Repository interfaces
- UseCases
- Domain models
- Business rules
- Validation contracts
- Business error abstraction

The Domain module never

- Uses Room
- Uses Compose
- Uses Android APIs
- Parses JSON
- Renders UI

---

# 4. Module Structure

```text id="domain02"
domain/

src/main/kotlin/

com.assignment.domain/

├── repository/
│
├── usecase/
│
├── model/
│
├── validation/
│
├── error/
│
├── contract/
│
├── util/
│
└── extension/
```

---

# 5. Dependency Direction

```text id="domain03"
Presentation

↓

Domain

↓

Repository Interface

↓

Data
```

The Domain module depends only on:

- core-common

Nothing else.

---

# 6. Repository Interfaces

Create repository contracts only.

Required

```text id="domain04"
HomeRepository

ServerRepository
```

No implementation.

---

# 7. HomeRepository Contract

Responsibilities

- Observe Home screen
- Observe updates
- Refresh screen
- Validate access

Returns

```text id="domain05"
Flow<ScreenModel>
```

---

# 8. ServerRepository Contract

Responsibilities

- Save JSON
- Update JSON
- Load JSON
- Delete JSON
- Validate JSON

No Room references.

---

# 9. UseCase Philosophy

Every business operation should be represented by one UseCase.

Small.

Focused.

Reusable.

---

# 10. Home UseCases

Generate

```text id="domain06"
ObserveHomeScreenUseCase

RefreshHomeUseCase
```

---

# 11. Server UseCases

Generate

```text id="domain07"
LoadJsonUseCase

SaveJsonUseCase

UpdateJsonUseCase

DeleteJsonUseCase

ValidateJsonUseCase
```

Each UseCase performs one responsibility.

---

# 12. Domain Models

Create immutable models.

Examples

```text id="domain08"
ScreenModel

SectionModel

ComponentModel

StyleModel

ActionModel

ThemeModel
```

These models are independent of JSON DTOs and Room entities.

---

# 13. Component Model

Contains

```text id="domain09"
id

type

props

style

actions

children

visible

displayOrder
```

Uses strongly typed domain structures.

---

# 14. Theme Model

Contains

```text id="domain10"
colors

typography

spacing

shape
```

No Compose classes.

---

# 15. Style Model

Contains

```text id="domain11"
padding

margin

background

foreground

shape

elevation

alignment
```

Pure business model.

---

# 16. Action Model

Supports

```text id="domain12"
navigate

dialog

snackbar

refresh

updateComponent

composite
```

Renderer interprets actions later.

---

# 17. Business Validation

Create validation contracts.

Examples

```text id="domain13"
ScreenValidator

SectionValidator

ComponentValidator
```

No implementation here.

---

# 18. Business Errors

Create

```text id="domain14"
DomainError

├── InvalidScreen

├── InvalidJson

├── InvalidAction

├── ValidationFailed

├── Unknown
```

Hide infrastructure-specific failures.

---

# 19. Result Strategy

Every UseCase returns

```text id="domain15"
Result<T>
```

using the Result wrapper from `core-common`.

Never throw expected business failures.

---

# 20. UseCase Composition

Current assignment

One operation

↓

One UseCase

Future

```text id="domain16"
Composite UseCase

↓

Multiple Repositories
```

Architecture should support composition.

---

# 21. Business Rules

Current assignment

- JSON must be valid before save
- Home observes latest valid JSON
- Invalid update never replaces existing screen

These rules belong in UseCases.

---

# 22. Threading

UseCases never hardcode dispatchers.

Dispatchers are injected.

---

# 23. Dependency Injection Readiness

Domain does not use Hilt.

Constructors should support injection by the Presentation/Data layers.

---

# 24. MVI Boundary

Presentation

↓

Intent

↓

UseCase

↓

Repository

↓

Flow

↓

Presentation State

Domain never exposes UI state.

---

# 25. Renderer Independence

The Domain layer never knows:

- Compose
- Renderer
- Registry

It only exposes ScreenModel.

---

# 26. JSON Independence

The Domain layer never knows:

- DTO
- JsonObject
- Serialization

Only business models.

---

# 27. Room Independence

The Domain layer never knows:

- Entity
- DAO
- Database

Only repository contracts.

---

# 28. Error Mapping Boundary

Infrastructure errors

↓

Data layer

↓

DomainError

↓

Presentation

---

# 29. Testing Strategy

Unit Tests

- Every UseCase
- Business rules
- Repository contracts (fake implementations)
- Error propagation

No Android tests.

---

# 30. Fake Repository Strategy

Use fake repositories for testing.

Never use Room in Domain tests.

---

# 31. Best Practices

Always

- Immutable models
- Small UseCases
- Repository contracts only
- Business-centric naming

Never

- Import Android
- Import Compose
- Import Room
- Import DTOs
- Hold UI state

---

# 32. Acceptance Criteria

Phase 08 is complete when

- Repository interfaces created
- UseCases implemented
- Domain models created
- Error models created
- Validation contracts created
- Tests passing

---

# 33. Common Pitfalls

Avoid

- Business logic in repositories
- Android imports
- Compose types
- JSON DTOs
- Room entities
- Navigation references

---

# 34. Definition of Done

- Domain compiles independently
- Repository contracts complete
- UseCases tested
- Business rules centralized
- No dependency violations

---

# 35. Production AI Prompt

## Objective

Implement the complete **domain** module.

### Mandatory First Step

Inspect

- Existing repository implementations
- Existing models
- Existing Result wrapper
- Existing architecture

Do not regenerate completed files.

---

### Create Packages

- repository
- usecase
- model
- validation
- error
- contract
- util
- extension

---

### Generate

#### Repository Contracts

- HomeRepository
- ServerRepository

#### UseCases

- ObserveHomeScreenUseCase
- RefreshHomeUseCase
- LoadJsonUseCase
- SaveJsonUseCase
- UpdateJsonUseCase
- DeleteJsonUseCase
- ValidateJsonUseCase

#### Models

- ScreenModel
- SectionModel
- ComponentModel
- ThemeModel
- StyleModel
- ActionModel

#### Validation Contracts

- ScreenValidator
- SectionValidator
- ComponentValidator

#### Errors

- DomainError hierarchy

---

### Requirements

- Pure Kotlin
- Immutable models
- Clean Architecture
- SOLID
- No Android
- No Compose
- No Room
- No DTO exposure
- Repository contracts only

---

### Tests

Generate

- UseCase tests
- Fake repository tests
- Domain model tests
- Error propagation tests
- Business rule tests

---

### Output Summary

Provide

- Files created
- Files modified
- UseCase catalog
- Repository contracts
- Domain model catalog
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 36. Improvements for Production Architecture

Compared to the earlier design, the following refinements should be applied during implementation.

## UseCase Categories

Organize UseCases into feature-specific folders instead of a flat package.

Example:

```text id="domain17"
usecase/

├── home/
│   ├── ObserveHomeScreenUseCase
│   └── RefreshHomeUseCase
│
└── server/
    ├── LoadJsonUseCase
    ├── SaveJsonUseCase
    ├── UpdateJsonUseCase
    ├── DeleteJsonUseCase
    └── ValidateJsonUseCase
```

This scales better as the project grows.

---

## Repository Contracts

Prefer narrowly focused interfaces.

Example:

```text id="domain18"
HomeRepository

ServerRepository
```

Avoid creating a single large repository with unrelated responsibilities.

---

## Business Models

Domain models should remain stable across infrastructure changes.

If JSON schema or Room entities evolve, Domain models should require minimal or no modification.

---

## Business Validation

Validation contracts belong in Domain.

Concrete validator implementations belong in Data or Presentation depending on responsibility.

---

## Future Extensibility

The Domain layer should allow future addition of:

- Remote server synchronization
- Authentication
- Multiple SDUI screens
- Draft management
- Undo/redo history
- Feature flags

without changing public contracts.

---

# 37. Phase Dependency

```text id="domain19"
Phase 01 – Bootstrap
        │
        ▼
Phase 02 – core-common
        │
        ▼
Phase 03 – core-ui
        │
        ▼
Phase 04 – core-designsystem
        │
        ▼
Phase 05 – core-json
        │
        ▼
Phase 06 – core-database
        │
        ▼
Phase 07 – data
        │
        ▼
Phase 08 – domain
        │
        ▼
Phase 09 – feature-landing
```

---

# 38. Phase Completion Checklist

| Item | Status |
|------|--------|
| Repository Interfaces | ✅ Planned |
| Home UseCases | ✅ Planned |
| Server UseCases | ✅ Planned |
| Domain Models | ✅ Planned |
| Business Validation Contracts | ✅ Planned |
| Domain Error Hierarchy | ✅ Planned |
| Result Strategy | ✅ Planned |
| Unit Testing | ✅ Planned |
| Future Extensibility | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# Next Document

**37_PHASE_09_FEATURE_LANDING.md**

This phase will implement the **Landing Feature**, including:

- Landing Screen
- Two-button entry UI
- Navigation to Home
- Navigation to Local Server Panel
- Landing ViewModel
- Landing MVI state
- Landing UI tests
- Integration with Navigation
- Material 3 UI using `core-ui` and `core-designsystem`
- Production AI prompt for implementing the complete Landing feature.

This is the **first user-visible feature** and the application's entry point before the SDUI Home screen.