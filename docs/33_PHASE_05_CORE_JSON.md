# 33_PHASE_05_CORE_JSON.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 05 – Core JSON Foundation
>
> **Module:** `core-json`
>
> **Architecture:** Clean Architecture + Kotlinx Serialization + JSON Validation Pipeline
>
> **Status:** Implementation Phase 05
>
> **Priority:** Critical
>
> **Estimated Time:** 8–12 Hours
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – core-common
> - ✅ Phase 04 – core-designsystem
> - 📖 06_JSON_SCHEMA_DESIGN.md
> - 📖 23_JSON_VALIDATION_AND_PARSER_ENGINE.md
> - 📖 26_COMPLETE_WIDGET_CATALOG.md

---

# 1. Phase Objective

This phase implements the complete **JSON Foundation Layer** for the SDUI platform.

The goal is to convert JSON into validated, strongly typed models without any dependency on:

- Compose
- Room
- Renderer
- Navigation
- ViewModel
- Feature modules

After this phase, the application will be able to safely:

```text
Raw JSON

↓

Deserialize

↓

Normalize

↓

Validate

↓

Map

↓

Domain Screen Model
```

No UI will be rendered in this phase.

---

# 2. Responsibilities

The `core-json` module is responsible for:

- JSON serialization
- DTO models
- Schema contracts
- JSON parser
- Normalization
- Validation pipeline
- Version compatibility
- Error handling
- DTO → Domain mapping
- JSON fixtures
- Parser unit tests

It must **never**:

- Render Compose
- Read Room
- Execute navigation
- Hold business state
- Perform network requests

---

# 3. Module Position

```text
Assets / Room

↓

core-json

↓

Domain ScreenModel

↓

Repository

↓

Renderer
```

---

# 4. Module Structure

```text
core-json/

src/main/kotlin/

com.assignment.core.json/

├── parser/
│
├── serializer/
│
├── dto/
│
├── model/
│
├── mapper/
│
├── validator/
│
├── normalization/
│
├── version/
│
├── schema/
│
├── error/
│
├── fixture/
│
├── extension/
│
└── util/
```

---

# 5. Package Responsibilities

## parser/

Contains

- JsonParser
- ParserResult

---

## serializer/

Contains

- Shared Json configuration
- Serialization utilities

---

## dto/

Contains

Every DTO mirroring JSON.

Examples

```text
ScreenDto
SectionDto
ComponentDto
StyleDto
ActionDto
ThemeDto
MetadataDto
```

---

## model/

Contains internal parser models.

No business models.

---

## mapper/

Contains DTO → Domain mappers.

---

## validator/

Contains

- SchemaValidator
- BusinessValidator
- PropertyValidator
- ActionValidator
- StyleValidator

---

## normalization/

Contains

JSON normalization.

---

## version/

Contains

Schema version validation.

---

## schema/

Contains

JSON schema constants.

---

## error/

Contains parser-specific errors.

---

## fixture/

Contains JSON fixtures.

---

# 6. Serialization Engine

Use only:

```text
Kotlinx Serialization
```

No Gson.

No Moshi.

No reflection-based parser.

---

# 7. Shared Json Configuration

Create one singleton Json instance.

Configuration

- ignoreUnknownKeys = true
- encodeDefaults = true
- explicitNulls = false
- prettyPrint = false
- isLenient = false

Shared across the application.

---

# 8. Root JSON Contract

Every screen follows

```text
Screen

├── Metadata

├── Theme

├── Sections

└── Version
```

Nothing else is allowed at root.

---

# 9. Screen DTO

Contains

```text
id

name

version

theme

sections
```

Immutable.

---

# 10. Section DTO

Contains

```text
id

title

displayOrder

visible

components
```

---

# 11. Component DTO

Contains

```text
id

type

props

style

actions

children

visible

displayOrder
```

This is the central SDUI model.

---

# 12. Style DTO

Supports

```text
padding

margin

background

foreground

shape

elevation

border

alignment

width

height

alpha
```

Only style definitions.

---

# 13. Action DTO

Supports

```text
navigate

dialog

snackbar

refresh

updateComponent

composite
```

Typed models only.

---

# 14. Theme DTO

Contains

```text
colors

typography

spacing

shapes
```

No Compose types.

---

# 15. Metadata DTO

Contains

```text
screenVersion

schemaVersion

rendererVersion
```

---

# 16. Polymorphic Component Strategy

Instead of creating a DTO for every widget, use:

```text
ComponentDto

↓

type

↓

Renderer Layer
```

This keeps JSON generic and extensible.

The parser does not know how widgets render.

---

# 17. Generic Property Model

`props` should be represented in a way that supports all assignment widgets.

The property model must support:

- Text
- Numbers
- Boolean
- Arrays
- Objects
- Nested values

without coupling to Compose.

---

# 18. JSON Normalization

Normalizer responsibilities

- Missing visible → true
- Missing displayOrder → append order
- Missing style → empty style
- Missing actions → empty list
- Missing children → empty list

Normalization happens before validation.

---

# 19. Validation Pipeline

Execution order

```text
Parse

↓

Normalize

↓

Schema Validation

↓

Version Validation

↓

Business Validation

↓

Property Validation

↓

Action Validation

↓

Style Validation

↓

Map
```

---

# 20. Schema Validation

Validate

- Root
- Sections
- Components
- Theme
- Metadata
- Required fields

Reject malformed payloads.

---

# 21. Business Validation

Validate

- Duplicate IDs
- Circular hierarchy
- Invalid parent-child relationship
- Invalid display order

---

# 22. Property Validation

Validate required properties according to widget type.

Example

Button

Requires

```text
text

action
```

Text

Requires

```text
text
```

Image

Requires

```text
url
```

Validation rules must align with **26_COMPLETE_WIDGET_CATALOG.md**.

---

# 23. Action Validation

Supported

```text
navigate

snackbar

dialog

refresh

updateComponent

composite
```

Unknown action

↓

Warning

↓

Ignored

---

# 24. Style Validation

Validate

- Supported tokens
- Padding
- Shapes
- Alpha
- Elevation

Do not resolve to Compose values here.

---

# 25. Version Validation

Validate

```text
schemaVersion

rendererVersion
```

Reject incompatible versions.

---

# 26. Mapper Layer

Convert

```text
DTO

↓

Domain ScreenModel
```

The renderer must consume domain models only.

---

# 27. Error Hierarchy

Create

```text
JsonError

├── ParseError

├── SchemaError

├── VersionError

├── ValidationError

├── MappingError

└── UnknownError
```

---

# 28. Parser Result

Return

```text
Success<ScreenModel>

Failure<JsonError>
```

Never throw parsing exceptions for expected failures.

---

# 29. JSON Fixtures

Provide fixtures

```text
valid_home.json

valid_banner.json

invalid_widget.json

invalid_style.json

duplicate_ids.json

empty_screen.json

unsupported_version.json
```

Used for tests.

---

# 30. Widget Compatibility

The parser must support every widget documented in

```text
26_COMPLETE_WIDGET_CATALOG.md
```

without adding Compose-specific logic.

---

# 31. Performance Considerations

The parser should:

- Deserialize once
- Reuse Json instance
- Avoid reflection
- Avoid duplicate validation
- Remain allocation-efficient

This phase intentionally excludes benchmark implementation.

---

# 32. Security Rules

Reject payloads containing:

- Invalid schema
- Unsupported root objects
- Malformed structures

The parser must never evaluate executable content.

---

# 33. Dependency Rules

Allowed

- Kotlinx Serialization
- core-common

Not allowed

- Compose
- Material3
- Room
- Navigation
- Renderer
- Feature modules

---

# 34. Testing Strategy

Unit Tests

- Parser
- Serializer
- Normalizer
- Validators
- Mapper
- Version validation

Negative Tests

- Invalid JSON
- Missing required props
- Unknown widget
- Invalid action
- Invalid style
- Duplicate IDs
- Unsupported version

---

# 35. Best Practices

Always

- Immutable DTOs
- Deterministic mapping
- Validate before mapping
- Normalize before validation
- Return Result objects

Never

- Parse inside Compose
- Throw runtime exceptions for expected validation failures
- Use reflection-based serialization
- Couple parser to renderer

---

# 36. Acceptance Criteria

Phase 05 is complete when:

- Shared Json configured
- DTO models implemented
- Parser implemented
- Normalizer implemented
- Validation pipeline implemented
- Version validation implemented
- Mapper implemented
- Error hierarchy implemented
- Fixtures created
- Tests passing

---

# 37. Common Pitfalls

Avoid

- Compose dependencies
- Room entities
- Business models
- Widget rendering
- ViewModels
- Mutable DTOs
- Generic `Map<String, Any>` APIs leaking throughout the architecture
- Parsing JSON inside repositories

---

# 38. Definition of Done

- Module builds independently
- Parser validated
- DTOs immutable
- Validation complete
- Mapping complete
- Tests pass
- No dependency violations

---

# 39. Production AI Prompt

## Objective

Implement the complete **core-json** module.

### First Step (Mandatory)

Inspect the repository.

Verify:

- Existing package names
- Existing modules
- Existing DTOs
- Existing serialization configuration
- Existing tests

Never regenerate existing files.

---

### Create Packages

- parser
- serializer
- dto
- mapper
- validator
- normalization
- version
- schema
- error
- fixture
- util

---

### Generate

#### Serializer

- Shared Json Provider

#### Parser

- JsonParser
- ParserResult

#### DTOs

- ScreenDto
- SectionDto
- ComponentDto
- ThemeDto
- StyleDto
- ActionDto
- MetadataDto

#### Validators

- SchemaValidator
- BusinessValidator
- PropertyValidator
- StyleValidator
- ActionValidator
- VersionValidator

#### Mapping

- DTO → Domain mappers

#### Fixtures

Generate valid and invalid JSON samples.

---

### Requirements

- Kotlinx Serialization only
- Immutable DTOs
- Clean Architecture
- Result-based parsing
- Generic SDUI models
- No Compose dependency
- No Room dependency
- Compatible with Renderer
- Compatible with Widget Catalog
- Compatible with future Server Panel

---

### Tests

Generate

- Parser tests
- Validator tests
- Mapper tests
- Version tests
- Normalizer tests
- Fixture-based tests

---

### Output Summary

Provide

- Files created
- Files modified
- DTO catalog
- Validation pipeline
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 40. Phase Dependency

```text
Phase 01
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
```

---

# 41. Phase Completion Checklist

| Item | Status |
|------|--------|
| Serialization Engine | ✅ Planned |
| Shared Json Config | ✅ Planned |
| DTO Models | ✅ Planned |
| Parser | ✅ Planned |
| Normalizer | ✅ Planned |
| Validation Pipeline | ✅ Planned |
| Version Validation | ✅ Planned |
| Mapper | ✅ Planned |
| Error Hierarchy | ✅ Planned |
| JSON Fixtures | ✅ Planned |
| Tests | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# Next Document

**34_PHASE_06_CORE_DATABASE.md**

This phase will implement the complete `core-database` module, including:

- Room database configuration
- Entities
- DAOs
- TypeConverters
- Initial JSON seeding from assets
- Database migrations
- Local SDUI Server persistence
- CRUD APIs for JSON editing
- Reactive `Flow` updates for Home screen
- Repository-ready database layer
- Unit and instrumentation tests
- Production AI prompt for implementing the complete Room database foundation.

**This phase is where the Room database officially becomes the Local SDUI Server for the assignment.**