# 22_COMPONENT_REGISTRY_AND_WIDGET_LIBRARY.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Generic SDUI Renderer
>
> **Module:** `core-renderer`
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - 00–21 Documentation
> - JSON Schema Design
> - SDUI Renderer Engine
> - Design System
> - Home Screen Design

---

# Document Objective

This document defines the complete **Component Registry** and **Widget Library** architecture.

The Component Registry is the heart of the SDUI platform.

It is responsible for:

- Registering widgets
- Finding widget renderers
- Creating Compose UI dynamically
- Supporting unlimited future widgets
- Maintaining backward compatibility

The goal is to ensure **adding a new widget never requires changing the renderer engine or Home screen**.

---

# 1. Widget Philosophy

Traditional Android

```text id="widget01"
HomeScreen

↓

Text()

↓

Image()

↓

Button()
```

SDUI

```text id="widget02"
JSON

↓

ComponentRegistry

↓

WidgetRenderer

↓

Compose
```

The renderer does not know widget implementations.

---

# 2. Registry Responsibilities

The registry is responsible for

- Widget registration
- Widget lookup
- Widget validation
- Renderer resolution
- Unknown widget fallback

It never

- Reads Room
- Parses JSON
- Executes business logic
- Performs navigation

---

# 3. Registry Architecture

```text id="widget03"
Component

↓

Registry

↓

Widget Renderer

↓

Compose Widget
```

Every widget passes through the registry.

---

# 4. Registry Module Structure

```text id="widget04"
core-renderer/

registry/

├── ComponentRegistry

├── RegistryInitializer

├── WidgetMetadata

├── WidgetDefinition

├── WidgetFactory

├── WidgetValidator

└── UnknownWidgetHandler
```

---

# 5. Widget Registration Lifecycle

```text id="widget05"
Application

↓

Registry Initialization

↓

Register Widgets

↓

Ready
```

Registration occurs once during application startup.

---

# 6. Widget Definition

Every widget exposes

```text id="widget06"
Component Type

Renderer

Supported Properties

Supported Actions

Supported Styles

Version
```

---

# 7. Base Widget Contract

Every widget implements a common contract.

Responsibilities

- Render UI
- Validate props
- Apply styles
- Dispatch actions

Widgets remain stateless.

---

# 8. Widget Metadata

Every widget defines

```text id="widget07"
id

name

type

version

description

category
```

Useful for debugging and future tooling.

---

# 9. Widget Categories

Current categories

```text id="widget08"
Layout

Display

Input

Collection

Feedback

Navigation

Media
```

Future categories can be added without redesign.

---

# 10. Assignment Widget Catalog

Layout

```text id="widget09"
Column

Row

Box

Spacer

Divider
```

Display

```text id="widget10"
Text

Image

Card

Badge
```

Input

```text id="widget11"
Button

Search

Chip

ChipGroup
```

Collection

```text id="widget12"
LazyColumn

LazyRow

CarCard
```

Business Widgets

```text id="widget13"
HeroBanner

CTA

Footer
```

---

# 11. Widget Registration Table

| Component Type | Renderer |
|----------------|----------|
| Text | TextRenderer |
| Image | ImageRenderer |
| Button | ButtonRenderer |
| Column | ColumnRenderer |
| Row | RowRenderer |
| Card | CardRenderer |
| Banner | BannerRenderer |
| Search | SearchRenderer |
| Chip | ChipRenderer |
| ChipGroup | ChipGroupRenderer |
| CarCard | CarCardRenderer |
| CTA | CTARenderer |
| Footer | FooterRenderer |

---

# 12. Widget Lifecycle

```text id="widget14"
JSON

↓

Parse

↓

Registry Lookup

↓

Renderer

↓

Compose

↓

Dispose
```

---

# 13. Widget Properties

Each widget supports

```text id="widget15"
id

type

props

style

actions

visibility

children
```

Widgets ignore unsupported optional properties.

---

# 14. Generic Property Resolution

Flow

```text id="widget16"
JSON Props

↓

Property Mapper

↓

Typed Props

↓

Widget Renderer
```

Property mapping is centralized.

---

# 15. Style Integration

Every widget supports

- Padding
- Background
- Border
- Shape
- Elevation
- Typography
- Color
- Alignment
- Size

Style resolution is delegated to the Style Resolver.

---

# 16. Action Integration

Widgets never execute navigation.

Flow

```text id="widget17"
Widget Click

↓

Action Dispatcher

↓

ViewModel Event

↓

Navigator
```

---

# 17. Container Widgets

Container widgets

```text id="widget18"
Column

Row

Box

LazyColumn

LazyRow
```

They recursively render child components.

---

# 18. Nested Components

Example

```text id="widget19"
Column

↓

Card

↓

Row

↓

Image

↓

Text

↓

Button
```

Unlimited nesting is supported.

---

# 19. Unknown Widget Strategy

Flow

```text id="widget20"
Registry Lookup

↓

Missing

↓

UnknownWidgetRenderer

↓

Placeholder

↓

Continue
```

No application crash.

---

# 20. Widget Validation

Validate

- Supported type
- Required props
- Required children
- Style compatibility
- Action compatibility

Validation occurs before rendering.

---

# 21. Widget Versioning

Each widget contains

```text id="widget21"
widgetVersion
```

Supports future evolution.

---

# 22. Business Widgets

Assignment-specific widgets

```text id="widget22"
HeroBanner

SearchBar

CategoryChipGroup

FeaturedCars

CarCard

CTASection

Footer
```

These are still generic renderers with business-specific property models.

---

# 23. Widget Extensibility

Adding a widget requires

1. JSON schema update
2. Property model
3. Renderer
4. Registry registration
5. Tests

No HomeScreen modification.

---

# 24. Widget Dependency Rules

Widgets may depend on

- Material 3
- Style Resolver
- Action Dispatcher

Widgets must never depend on

- Room
- Repository
- ViewModel
- Navigation
- JSON parser

---

# 25. Widget Composition Rules

Widgets should

- Be stateless
- Accept immutable models
- Support previews
- Use Material 3
- Be reusable

Avoid feature-specific logic.

---

# 26. Accessibility

Every widget supports

- Content descriptions
- Accessible click targets
- Material semantics
- Dynamic font scaling
- Screen readers

---

# 27. Performance Guidelines

Widgets should naturally

- Avoid unnecessary recompositions
- Use stable parameters
- Reuse modifiers where appropriate
- Avoid expensive calculations during composition
- Support lazy rendering when part of collections

---

# 28. Widget Testing Strategy

Unit Tests

- Property validation
- Registry lookup
- Widget metadata

Renderer Tests

- Text
- Image
- Button
- Banner
- Search
- Chip
- CarCard
- CTA
- Footer

Integration Tests

- Registry
- Factory
- Nested rendering

---

# 29. Assignment Widget Flow

```text id="widget23"
Room JSON

↓

ScreenModel

↓

Registry

↓

Widget Renderer

↓

Compose Widget

↓

User Interaction

↓

Action Dispatcher
```

---

# 30. Widget Development Checklist

Before adding a widget verify

- JSON schema updated
- Property model created
- Renderer implemented
- Registry registration complete
- Preview added
- Unit tests added
- Renderer tests added
- Documentation updated

---

# 31. Acceptance Criteria

The Widget Library is complete when

- Registry implemented
- Widget catalog documented
- Base contract defined
- Property mapping defined
- Validation defined
- Unknown widget fallback implemented
- Extension strategy documented
- Testing strategy documented

---

# 32. Production AI Prompt

## Objective

Generate the complete Component Registry and Widget Library.

### Generate

#### Registry

- ComponentRegistry
- RegistryInitializer
- WidgetFactory
- WidgetMetadata
- WidgetDefinition
- WidgetValidator

#### Base Contracts

- WidgetRenderer interface
- BaseWidgetProps
- WidgetAction
- WidgetStyle

#### Widget Renderers

Layout

- ColumnRenderer
- RowRenderer
- BoxRenderer
- SpacerRenderer
- DividerRenderer

Display

- TextRenderer
- ImageRenderer
- CardRenderer
- BadgeRenderer

Input

- ButtonRenderer
- SearchRenderer
- ChipRenderer
- ChipGroupRenderer

Business

- HeroBannerRenderer
- CarCardRenderer
- CTARenderer
- FooterRenderer

Fallback

- UnknownWidgetRenderer

### Requirements

- Generic architecture
- Stateless renderers
- Immutable property models
- Material 3
- Centralized property mapping
- Centralized style resolution
- Action dispatcher integration
- Compatible with JSON schema
- Compatible with Renderer Engine
- No business logic inside widgets

### Tests

Generate

- Registry tests
- Widget validation tests
- Renderer tests
- Nested rendering tests
- Unknown widget tests
- Widget metadata tests

### Output Summary

Provide

- Files created
- Files modified
- Widget catalog
- Registry dependency graph
- Widget lifecycle diagram
- Architecture compliance checklist

---

# Component Registry Documentation Status

| Section | Status |
|----------|--------|
| Registry Architecture | ✅ Complete |
| Widget Library | ✅ Complete |
| Base Widget Contract | ✅ Complete |
| Property Resolution | ✅ Complete |
| Style Integration | ✅ Complete |
| Action Integration | ✅ Complete |
| Extensibility | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**23_JSON_VALIDATION_AND_PARSER_ENGINE.md**

This document will define the complete JSON infrastructure, including:

- JSON parser engine
- Kotlinx Serialization models
- Multi-stage validation pipeline
- Schema validator
- Business validator
- Property validator
- Parser error handling
- Version compatibility validation
- JSON normalization
- Production AI prompt for implementing the complete parser and validation engine