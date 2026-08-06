# 23_JSON_VALIDATION_AND_PARSER_ENGINE.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Kotlinx Serialization + Room Local SDUI Server
>
> **Module:** `core-json`
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - 00–22 Documentation
> - JSON Schema Design
> - Renderer Engine
> - Component Registry
> - Room Database Design

---

# Document Objective

This document defines the complete **JSON Infrastructure** for the SDUI platform.

The JSON Engine is responsible for converting JSON into strongly typed models while ensuring every payload is safe before reaching the renderer.

The engine consists of five major stages:

```text
JSON

↓

Parser

↓

Normalizer

↓

Validator

↓

Domain Mapper

↓

Renderer
```

The renderer should only receive **validated, normalized models**.

---

# 1. Core Philosophy

The parser never trusts JSON.

Every payload must pass validation before rendering.

Rule:

```text
Invalid JSON

↓

Reject

↓

Never Render
```

---

# 2. Module Structure

```text
core-json/

├── parser/

├── serializer/

├── validator/

├── mapper/

├── schema/

├── normalization/

├── version/

├── model/

├── extension/

├── error/

├── fixture/

└── util/
```

---

# 3. Parsing Pipeline

```text
Raw JSON

↓

Deserialize

↓

Normalize

↓

Schema Validation

↓

Business Validation

↓

Property Validation

↓

Version Validation

↓

Domain Mapping

↓

Renderer
```

Every stage is isolated.

---

# 4. Responsibilities

Parser

- Deserialize JSON

Normalizer

- Fill optional defaults

Schema Validator

- Validate JSON structure

Business Validator

- Validate SDUI rules

Property Validator

- Validate widget properties

Mapper

- Convert DTO → Domain

---

# 5. JSON Source

Current Assignment

```text
Assets

↓

Room

↓

Parser
```

Future

```text
REST API

↓

Parser
```

No architecture changes required.

---

# 6. Parser Engine

Responsibilities

- Deserialize JSON
- Return typed DTO
- Handle parsing failures
- Never expose raw exceptions

Implementation uses **Kotlinx Serialization** only.

---

# 7. Serializer Configuration

Provide one shared Json instance.

Recommended configuration

- ignoreUnknownKeys = true
- explicitNulls = false
- encodeDefaults = true
- prettyPrint = Debug only
- isLenient = false

One singleton instance only.

---

# 8. DTO Layer

DTOs mirror the JSON exactly.

Examples

```text
ScreenDto

SectionDto

ComponentDto

StyleDto

ActionDto

ThemeDto
```

DTOs must not contain business logic.

---

# 9. Domain Mapping

Flow

```text
DTO

↓

Mapper

↓

Domain Model
```

Renderer never consumes DTOs.

---

# 10. JSON Normalization

Normalizer applies defaults.

Example

Input

```json
{
  "visible": null
}
```

Output

```json
{
  "visible": true
}
```

Normalization never changes business meaning.

---

# 11. Schema Validation

Validate

- Root object
- Metadata
- Layout
- Sections
- Components
- Theme
- Required properties

Reject invalid payloads.

---

# 12. Business Validation

Validate

- Duplicate IDs
- Invalid hierarchy
- Circular references
- Invalid widget placement
- Invalid display order

---

# 13. Property Validation

Every widget validates its required properties.

Example

Button

Must contain

```text
text

action
```

Banner

Must contain

```text
image

title
```

---

# 14. Style Validation

Validate

- Color
- Padding
- Shape
- Typography
- Alignment
- Border

Ignore unsupported optional style values.

Reject malformed values.

---

# 15. Action Validation

Supported actions

```text
Navigate

OpenDialog

Snackbar

UpdateComponent

Refresh
```

Unknown actions

↓

Warning

↓

Ignore

---

# 16. Version Validation

Validate

```text
schemaVersion

rendererVersion

componentVersion
```

Flow

```text
Compatible

↓

Continue
```

Otherwise

↓

Reject

---

# 17. Duplicate ID Validation

Every ID must be unique.

Validate

- Screen IDs
- Section IDs
- Component IDs

Duplicate IDs reject the payload.

---

# 18. Layout Validation

Validate

- Parent exists
- Children valid
- Supported layout
- No invalid nesting

---

# 19. Component Validation

Validate

- Component type
- Required props
- Supported actions
- Supported styles
- Visibility

---

# 20. Theme Validation

Validate

- Colors
- Typography
- Shapes
- Elevation
- Spacing

Invalid theme

↓

Fallback to Material 3 defaults

---

# 21. Error Hierarchy

```text
JsonError

├── ParseError

├── SchemaError

├── ValidationError

├── VersionError

├── MappingError

└── UnknownError
```

---

# 22. Parser Result

Parser returns

```text
Success<ScreenDto>

Failure<JsonFailure>
```

Never throws expected parsing errors.

---

# 23. Mapping Result

DTO

↓

Domain

↓

Result

Mappers must remain deterministic.

---

# 24. JSON Fixtures

Provide reusable fixtures.

Examples

```text
ValidHome.json

ValidBanner.json

InvalidWidget.json

DuplicateIds.json

OldSchema.json

EmptyScreen.json
```

Used by tests.

---

# 25. Performance Strategy

Parser should naturally

- Deserialize once
- Reuse serializer
- Avoid reflection
- Avoid repeated validation
- Parse only modified JSON in the Server Panel where practical

---

# 26. Security Rules

Reject

- Unknown root object
- Kotlin code
- SQL
- HTML
- Script content
- Unsupported metadata

Accept only valid SDUI JSON.

---

# 27. Future Compatibility

Support

- Widget version upgrades
- Backend API
- Remote Config
- Multiple screen types
- Feature flags

Without parser redesign.

---

# 28. Testing Strategy

Unit Tests

- Parser
- Serializer
- Normalizer
- Validators
- Mapper

Integration Tests

- Assets → Parser
- Room → Parser

Negative Tests

- Invalid JSON
- Invalid schema
- Duplicate IDs
- Unknown widget
- Invalid styles
- Invalid actions

Target 100% coverage for parser and validators.

---

# 29. Assignment Parsing Flow

```text
Room JSON

↓

Parser

↓

Normalizer

↓

Validators

↓

Mapper

↓

ScreenModel

↓

Renderer
```

---

# 30. Best Practices

Always

- Validate before mapping
- Normalize before validation
- Return Result
- Keep validators independent
- Use immutable DTOs

Never

- Parse JSON inside Compose
- Skip validation
- Modify raw payload unexpectedly
- Expose serialization exceptions

---

# 31. Acceptance Criteria

Parser Engine is complete when

- Shared parser implemented
- Serializer configured
- DTO layer complete
- Multi-stage validation implemented
- Normalizer implemented
- Mapper implemented
- Error hierarchy defined
- Tests completed

---

# 32. Production AI Prompt

## Objective

Generate the complete `core-json` module.

### Generate

#### Parser

- JsonParser
- JsonSerializerProvider

#### DTO Models

- ScreenDto
- SectionDto
- ComponentDto
- StyleDto
- ActionDto
- ThemeDto

#### Validators

- SchemaValidator
- BusinessValidator
- PropertyValidator
- StyleValidator
- ActionValidator
- VersionValidator

#### Infrastructure

- JsonNormalizer
- JsonMapper
- JsonResult
- JsonError hierarchy

#### Test Fixtures

- Valid payloads
- Invalid payloads
- Duplicate IDs
- Unknown widget
- Version mismatch

### Requirements

- Kotlinx Serialization only
- Immutable DTOs
- Multi-stage validation
- No reflection-based parsing
- Result-based error handling
- Generic architecture
- Compatible with Renderer Engine
- Compatible with Room Local Server
- Compatible with JSON Schema
- Clean Architecture compliant

### Tests

Generate

- Parser tests
- Serializer tests
- Validator tests
- Mapper tests
- Version tests
- Normalizer tests
- Integration tests

### Output Summary

Provide

- Files created
- Files modified
- Validation pipeline diagram
- Parser dependency graph
- Error hierarchy
- Architecture compliance checklist

---

# JSON Parser Engine Documentation Status

| Section | Status |
|----------|--------|
| Parser Architecture | ✅ Complete |
| DTO Layer | ✅ Complete |
| Normalization | ✅ Complete |
| Validation Pipeline | ✅ Complete |
| Mapper Layer | ✅ Complete |
| Error Handling | ✅ Complete |
| Performance Strategy | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**24_DESIGN_SYSTEM_AND_THEME_ENGINE.md**

This document will define the complete Design System architecture, including:

- Material 3 integration
- Design tokens
- Color system
- Typography system
- Spacing system
- Shape system
- Elevation system
- Dynamic theme support
- SDUI style mapping
- Theme resolver
- Design token versioning
- Production AI prompt for implementing the complete Design System and Theme Engine.