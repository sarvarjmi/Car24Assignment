# 34_PHASE_06_CORE_DATABASE.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 06 – Core Database (Local SDUI Server)
>
> **Module:** `core-database`
>
> **Architecture:** Clean Architecture + Room + Offline First + Single Source of Truth
>
> **Status:** Implementation Phase 06
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
> - 📖 07_ROOM_DATABASE_DESIGN.md
> - 📖 20_SERVER_PANEL_DESIGN.md
> - 📖 23_JSON_VALIDATION_AND_PARSER_ENGINE.md

---

# 1. Phase Objective

This phase implements the complete **Local SDUI Server** using **Room Database**.

Unlike a traditional database layer, Room will simulate a backend server.

It must:

- Store complete SDUI JSON
- Allow editing from the Server Panel
- Automatically seed initial JSON
- Notify the Home screen through Flow
- Act as the **Single Source of Truth (SSOT)**

The Home screen must **never read assets directly**.

---

# 2. Core Philosophy

Application flow

```text
assets/home.json

↓

Initial Seeder

↓

Room Database

↓

Repository

↓

Flow

↓

Home Renderer
```

Assets are used **only once**.

After seeding, Room owns the data.

---

# 3. Responsibilities

The module is responsible for

- Room configuration
- Database creation
- Entities
- DAO
- TypeConverters
- Initial data seeding
- Migration support
- CRUD operations
- Flow updates
- Database validation

The module never

- Parses JSON
- Renders UI
- Executes navigation
- Contains business logic

---

# 4. Architecture Position

```text
Assets

↓

core-json

↓

core-database

↓

Repository

↓

Renderer
```

---

# 5. Module Structure

```text
core-database/

src/main/kotlin/

com.assignment.core.database/

├── database/
│
├── entity/
│
├── dao/
│
├── converter/
│
├── migration/
│
├── seeder/
│
├── datasource/
│
├── model/
│
├── util/
│
└── di/
```

---

# 6. Database Design

Database Name

```text
sdui_database.db
```

Version

```text
1
```

Future migrations must be supported.

---

# 7. Database Tables

For this assignment keep the schema intentionally simple.

## screen_json

Stores the complete SDUI payload.

Columns

```text
id

screenName

json

version

updatedAt

createdAt
```

---

## Optional Future Tables

Reserved

```text
theme

analytics

draft

history
```

Do **not** implement unless required.

---

# 8. Entity Design

Create

```text
ScreenJsonEntity
```

Responsibilities

- Store JSON
- Store metadata
- Store timestamps

No business logic.

---

# 9. DAO Design

Required APIs

```text
insert()

update()

delete()

getByScreen()

observeScreen()

observeAll()

exists()

count()

clear()
```

Observe APIs must return

```text
Flow
```

---

# 10. Flow Strategy

Every JSON update should automatically emit.

```text
Room

↓

Flow

↓

Repository

↓

Renderer

↓

Home Updated
```

No manual refresh.

---

# 11. Initial Seeder

On first launch

```text
assets/home.json

↓

Parser

↓

Validation

↓

Room

↓

Done
```

Never seed twice.

---

# 12. Seeder Rules

Seed only when

```text
table.isEmpty()
```

Otherwise

↓

Skip

---

# 13. Seeder Responsibilities

Validate JSON before saving.

Flow

```text
Assets

↓

Parser

↓

Validation

↓

Insert
```

Invalid assets must never reach Room.

---

# 14. Local Server Behavior

Server Panel

↓

Edit JSON

↓

Validate

↓

Update Room

↓

Flow

↓

Home Screen Updated

Exactly like a backend response.

---

# 15. CRUD Operations

Support

Create

Update

Delete

Read

Observe

Only repository layer will consume these.

---

# 16. TypeConverters

Only create converters if truly required.

Avoid unnecessary converters.

Current schema stores JSON as

```text
String
```

---

# 17. Database Migration Strategy

Version 1

↓

Version 2

↓

Migration

Never use destructive migration in production architecture.

Assignment may include fallback only during development if explicitly documented.

---

# 18. Data Validation

Before insert/update

Validate

- Screen exists
- JSON valid
- Version compatible
- Required metadata present

---

# 19. Error Handling

Database failures return

```text
Result

↓

DatabaseError
```

Never expose SQLite exceptions directly.

---

# 20. Database Error Model

Examples

```text
InsertFailed

UpdateFailed

DeleteFailed

NotFound

MigrationFailed

SeederFailed
```

---

# 21. Database Transactions

Use transactions when

- Multiple writes
- Future draft support
- Batch updates

Current assignment mainly requires single-screen updates.

---

# 22. Threading

Database work must run on

```text
IO Dispatcher
```

Never Main thread.

Dispatcher comes from

```text
core-common
```

---

# 23. Dependency Rules

Allowed

- Room
- Coroutines
- Flow
- core-common
- core-json

Not Allowed

- Compose
- Navigation
- ViewModel
- Renderer
- Feature modules

---

# 24. Repository Readiness

Expose APIs suitable for repository layer.

Do not expose DAO directly outside the module.

---

# 25. Database Security

Never execute raw SQL built from user input.

Use Room-generated queries.

---

# 26. Home Screen Integration

Home observes

```text
Repository

↓

Flow<ScreenModel>

↓

Renderer
```

Home never reads Room directly.

---

# 27. Server Panel Integration

Server Panel

↓

Repository

↓

Room Update

↓

Flow

↓

Home

No restart required.

---

# 28. JSON Preview Support

The Server Panel requires

```text
Read JSON

↓

Pretty Print

↓

Preview
```

Database should return raw JSON string.

Formatting belongs to presentation.

---

# 29. Version Support

Every record stores

```text
schemaVersion
```

Useful for future migrations.

---

# 30. Backup Strategy

Not required for assignment.

Architecture should allow future export/import.

---

# 31. Testing Strategy

Unit Tests

- DAO
- Seeder
- Entity mapping

Instrumentation Tests

- Room CRUD
- Flow emissions
- Initial seeding
- Update propagation

Negative Tests

- Invalid JSON
- Duplicate screen
- Missing screen
- Failed update

---

# 32. Performance Considerations

Use

- Indexed primary key
- Flow observation
- Single JSON storage
- Efficient updates

Do not introduce unnecessary caching.

Performance benchmarking is outside assignment scope.

---

# 33. Best Practices

Always

- Use Room
- Validate before insert
- Return Flow
- Keep entities simple
- Use immutable models

Never

- Parse JSON inside DAO
- Return mutable data
- Access Room from UI
- Store Compose models

---

# 34. Acceptance Criteria

Phase 06 is complete when

- Database configured
- Entity created
- DAO complete
- Seeder implemented
- Initial JSON seeded
- CRUD working
- Flow updates working
- Migration ready
- Tests passing

---

# 35. Common Pitfalls

Avoid

- Reading assets repeatedly
- Blocking Main thread
- Exposing DAO outside module
- Storing parsed Compose models
- Multiple database instances
- Manual Home refresh

---

# 36. Definition of Done

- Room builds successfully
- Initial seeding works
- CRUD verified
- Flow verified
- Tests pass
- Repository-ready APIs exposed

---

# 37. Production AI Prompt

## Objective

Implement the complete **core-database** module.

### Mandatory First Step

Inspect

- Existing Room setup
- Existing entities
- Existing DAO
- Existing parser
- Existing module dependencies

Never regenerate completed files.

---

### Create Packages

- database
- entity
- dao
- migration
- converter
- datasource
- seeder
- model
- util
- di

---

### Generate

#### Database

- AppDatabase

#### Entity

- ScreenJsonEntity

#### DAO

- ScreenJsonDao

#### Seeder

- DatabaseSeeder
- InitialJsonLoader

#### DataSource

- LocalDataSource

#### Migration

- Migration_1_2 placeholder

#### DI

- RoomModule

---

### Requirements

- Room only
- Flow APIs
- Offline-first
- Single Source of Truth
- Initial asset seeding
- Validation before insert
- Repository-ready APIs
- No Compose dependency
- No business logic

---

### Tests

Generate

- DAO tests
- Seeder tests
- CRUD tests
- Flow emission tests
- Migration tests (placeholder)
- Invalid JSON insertion tests

---

### Output Summary

Provide

- Files created
- Files modified
- Database schema
- DAO API summary
- Seeder flow
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 38. Database Lifecycle

```text
App Launch

↓

Database Created

↓

Seeder Runs

↓

Room Ready

↓

Repository Starts

↓

Home Observes

↓

Renderer Displays UI

↓

Server Panel Updates JSON

↓

Room Updated

↓

Flow Emits

↓

Home Auto Refresh
```

---

# 39. Phase Dependency

```text
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
Phase 07 – data module
```

---

# 40. Phase Completion Checklist

| Item | Status |
|------|--------|
| Room Configuration | ✅ Planned |
| Database | ✅ Planned |
| Entity | ✅ Planned |
| DAO | ✅ Planned |
| Seeder | ✅ Planned |
| CRUD Operations | ✅ Planned |
| Flow Updates | ✅ Planned |
| Migration Strategy | ✅ Planned |
| Local DataSource | ✅ Planned |
| DI Module | ✅ Planned |
| Tests | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# Next Document

**35_PHASE_07_DATA_MODULE.md**

This phase will implement the complete **`data` module**, including:

- Repository implementations
- LocalDataSource integration
- DTO → Domain mapping integration
- Flow-based repository APIs
- JSON parsing orchestration
- Database persistence coordination
- Error mapping
- Repository caching policy
- Offline-first data strategy
- Repository unit tests
- Production AI prompt for implementing the entire data layer.

This is the phase where **`core-json` + `core-database`** are connected into a clean, production-ready Repository layer that powers the Home screen and the Local SDUI Server.