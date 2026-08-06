# 35_PHASE_07_DATA_MODULE.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 07 – Data Layer Implementation
>
> **Module:** `data`
>
> **Architecture:** Clean Architecture + Repository Pattern + Offline First
>
> **Status:** Implementation Phase 07
>
> **Priority:** Critical
>
> **Estimated Time:** 12–16 Hours
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – core-common
> - ✅ Phase 05 – core-json
> - ✅ Phase 06 – core-database
> - 📖 08_DATA_LAYER_DESIGN.md
> - 📖 09_DOMAIN_LAYER_DESIGN.md
> - 📖 20_SERVER_PANEL_DESIGN.md

---

# 1. Phase Objective

This phase connects the **JSON Engine** and the **Room Database** into a clean, production-ready **Repository Layer**.

The Data module is responsible for orchestrating:

- Room Database
- JSON Parser
- Validation
- Domain Mapping
- Repository APIs

It becomes the **only source of data** for the Domain layer.

Neither the UI nor the Domain layer should know how data is stored.

---

# 2. Architecture Position

```text id="data01"
Presentation

↓

Domain

↓

Repository Interface

↓

Repository Implementation

↓

LocalDataSource

↓

Room Database

↓

JSON Parser

↓

Domain Models
```

---

# 3. Responsibilities

The Data module is responsible for:

- Repository implementations
- LocalDataSource integration
- JSON parsing orchestration
- Validation orchestration
- DTO → Domain mapping
- Error mapping
- Flow transformation
- Offline-first strategy
- Repository caching policy
- Transaction coordination

The Data module must never:

- Render UI
- Contain Compose
- Execute navigation
- Hold ViewModel state

---

# 4. Module Structure

```text id="data02"
data/

src/main/kotlin/

com.assignment.data/

├── repository/
│
├── datasource/
│
├── mapper/
│
├── model/
│
├── cache/
│
├── validation/
│
├── error/
│
├── util/
│
└── di/
```

---

# 5. Dependency Direction

```text id="data03"
Presentation

↓

Domain

↓

Data

↓

core-database

↓

core-json

↓

core-common
```

No reverse dependencies are allowed.

---

# 6. Repository Philosophy

Repositories expose **domain models only**.

Never expose:

- Room Entity
- DTO
- JsonElement
- DAO

Repositories abstract storage completely.

---

# 7. Repository Catalog

Implement

```text id="data04"
HomeRepositoryImpl

ServerRepositoryImpl
```

Repositories implement interfaces defined in the **Domain** module.

---

# 8. HomeRepository

Responsibilities

- Observe Home JSON
- Parse JSON
- Validate JSON
- Map to Domain
- Emit Flow<ScreenModel>

---

# 9. ServerRepository

Responsibilities

- Save JSON
- Update JSON
- Delete JSON
- Read JSON
- Pretty-print JSON (if required)
- Validate before persistence

---

# 10. LocalDataSource

Acts as the bridge to Room.

Responsibilities

- CRUD
- Observe Flow
- Seeder access

Never expose DAO outside the Data layer.

---

# 11. Data Flow

Home

```text id="data05"
Room

↓

LocalDataSource

↓

Repository

↓

Parser

↓

Mapper

↓

Flow<ScreenModel>

↓

Domain
```

---

# 12. Server Flow

```text id="data06"
JSON Editor

↓

Repository

↓

Parser

↓

Validation

↓

Room

↓

Flow

↓

Home
```

---

# 13. Offline-First Strategy

Room is the only persistence layer.

There is no network source.

Future architecture should support:

```text id="data07"
Remote

↓

Repository

↓

Room

↓

UI
```

without redesign.

---

# 14. Repository Result

Repositories return

```text id="data08"
Result<T>
```

using the Result wrapper from **core-common**.

Never throw expected failures.

---

# 15. Error Mapping

Map

```text id="data09"
DatabaseError

↓

RepositoryError

↓

Domain
```

Parser errors

↓

Repository errors

↓

Domain

Never expose implementation-specific errors.

---

# 16. Flow Strategy

Repositories expose

```text id="data10"
Flow
```

not LiveData.

Flows should be:

- Cold where appropriate
- Lifecycle-friendly
- Cancellation-safe

---

# 17. Caching Strategy

Current assignment

Room = Cache

No additional in-memory cache.

Future caching layer can be added without changing repository contracts.

---

# 18. Validation Pipeline

Repository update flow

```text id="data11"
JSON

↓

Parser

↓

Normalize

↓

Validate

↓

Save

↓

Emit
```

Never save invalid JSON.

---

# 19. Transaction Strategy

For operations requiring multiple writes

Use

```text id="data12"
Room Transaction
```

Single updates do not require complex transaction orchestration.

---

# 20. Repository Threading

All repository work must use

```text id="data13"
DispatcherProvider.IO
```

No hardcoded dispatchers.

---

# 21. Domain Mapping

Repository emits

```text id="data14"
ScreenModel
```

not DTOs.

The mapping boundary belongs to the Data layer.

---

# 22. Dependency Injection

Provide

- HomeRepositoryImpl
- ServerRepositoryImpl

through Hilt.

Bind implementations to Domain interfaces.

---

# 23. Logging

Repositories should use the Logger abstraction.

Log

- Parse failures
- Validation failures
- Database failures

Do not log entire JSON payloads in production.

---

# 24. Security

Reject invalid JSON before persistence.

Never execute user-provided SQL.

Never expose internal database structure.

---

# 25. Repository Interfaces

Implemented from Domain

Examples

```text id="data15"
HomeRepository

ServerRepository
```

No new public contracts should be introduced here.

---

# 26. Home Screen Integration

Presentation

↓

HomeUseCase

↓

HomeRepository

↓

Room Flow

↓

Renderer

---

# 27. Server Panel Integration

Presentation

↓

ServerUseCase

↓

ServerRepository

↓

Room Update

↓

Home Auto Refresh

---

# 28. Failure Recovery

If JSON update fails

↓

Return Failure

↓

Database unchanged

↓

Home continues rendering previous valid JSON

Never corrupt stored state.

---

# 29. Repository Testing Strategy

Unit Tests

- HomeRepository
- ServerRepository
- LocalDataSource
- Error mapping
- Mapper integration

Fake dependencies

- Fake DAO
- Fake Parser

---

# 30. Integration Tests

Verify

- Save JSON
- Observe Flow
- Parse
- Validate
- Emit ScreenModel

---

# 31. Best Practices

Always

- Return domain models
- Use Flow
- Validate before save
- Keep repositories thin
- Delegate parsing

Never

- Parse inside UI
- Expose Entity
- Expose DTO
- Hardcode dispatchers
- Access DAO outside Data layer

---

# 32. Acceptance Criteria

Phase 07 is complete when

- Repository implementations created
- LocalDataSource integrated
- Parser integrated
- Validation integrated
- Mapping integrated
- Error mapping complete
- Flow APIs complete
- Tests passing

---

# 33. Common Pitfalls

Avoid

- Business logic in repositories
- Compose imports
- DAO exposure
- Duplicate mapping
- LiveData
- Mutable shared state
- Multiple JSON parsing paths

---

# 34. Definition of Done

- Repositories compile
- Flow works
- JSON validation enforced
- Domain models emitted
- Tests pass
- Offline-first architecture preserved

---

# 35. Production AI Prompt

## Objective

Implement the complete **data** module.

### Mandatory First Step

Inspect

- Domain interfaces
- Existing Room implementation
- Existing parser
- Existing mapper
- Existing DI modules

Do not regenerate completed files.

---

### Create Packages

- repository
- datasource
- mapper
- validation
- error
- cache
- util
- di

---

### Generate

#### Repository Implementations

- HomeRepositoryImpl
- ServerRepositoryImpl

#### DataSource

- LocalDataSource

#### Mapping

- Entity → DTO
- DTO → Domain

#### Error Mapping

- RepositoryErrorMapper

#### DI

- RepositoryModule

---

### Requirements

- Offline-first
- Flow-based
- Repository Pattern
- Clean Architecture
- Result wrapper
- No Compose
- No ViewModel
- No Room exposure
- Domain models only

---

### Tests

Generate

- Repository tests
- DataSource tests
- Mapper tests
- Error mapping tests
- Flow integration tests
- JSON update tests

---

### Output Summary

Provide

- Files created
- Files modified
- Repository APIs
- Flow diagram
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 36. Repository Lifecycle

```text id="data16"
App Start

↓

Repository Created

↓

Observe Room

↓

Receive JSON

↓

Parse

↓

Validate

↓

Map

↓

Emit Domain Model

↓

Presentation
```

---

# 37. JSON Update Lifecycle

```text id="data17"
Server Panel

↓

Repository

↓

Validate JSON

↓

Room Update

↓

Flow Emit

↓

Home Repository

↓

ScreenModel

↓

Renderer

↓

Updated UI
```

---

# 38. Phase Dependency

```text id="data18"
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
```

---

# 39. Phase Completion Checklist

| Item | Status |
|------|--------|
| Repository Implementations | ✅ Planned |
| LocalDataSource | ✅ Planned |
| Parser Integration | ✅ Planned |
| Validation Integration | ✅ Planned |
| DTO → Domain Mapping | ✅ Planned |
| Error Mapping | ✅ Planned |
| Flow APIs | ✅ Planned |
| Offline-first Strategy | ✅ Planned |
| Repository DI | ✅ Planned |
| Repository Tests | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# 40. Improvements Over Previous Design

This phase introduces several refinements to make the architecture more production-ready:

### Repository Responsibilities

Repositories should remain orchestration layers only. They should:

- Coordinate LocalDataSource and Parser
- Coordinate validation
- Map infrastructure models to domain models
- Never contain feature business rules

Business decisions belong in the **Domain UseCases**, not repositories.

### DataSource Contract

Introduce explicit interfaces:

```text id="data19"
LocalDataSource

↓

RoomLocalDataSourceImpl
```

This keeps repositories independent of Room implementation details and improves testability.

### Mapper Separation

Separate mapping responsibilities into:

```text id="data20"
Entity → RawJson

RawJson → DTO

DTO → Domain
```

Avoid large monolithic mapper classes.

### Future Remote Readiness

Although the assignment is offline-only, repository contracts should be designed so a future:

```text id="data21"
RemoteDataSource
```

can be added without changing the Domain API.

### Reactive Consistency

Repositories should expose a **single reactive source** for Home data.

Avoid multiple observation APIs returning different representations of the same screen.

---

# Next Document

**36_PHASE_08_DOMAIN_MODULE.md**

This phase will implement the complete **Domain Layer**, including:

- Repository interfaces
- UseCases
- Domain models
- Business validation
- Repository contracts
- MVI interaction boundaries
- UseCase composition
- Error abstraction
- Domain testing strategy
- Production AI prompt for implementing the entire Domain module.

This phase will complete the **core business layer** before moving into Presentation and Feature implementation.