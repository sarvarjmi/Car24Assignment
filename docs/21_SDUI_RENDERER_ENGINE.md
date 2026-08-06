# 21_SDUI_RENDERER_ENGINE.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Generic SDUI Platform
>
> **Rendering Engine:** JSON → Compose Renderer
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - 00–20 Documentation
> - JSON Schema Design
> - Room Database Design
> - Home Screen Design
> - Server Panel Design

---

# Document Objective

This document defines the **heart of the entire assignment**—the **Generic SDUI Renderer Engine**.

This engine is responsible for converting **validated JSON** into **Jetpack Compose UI**.

The renderer must be:

- Generic
- Extensible
- Production-ready
- Testable
- Offline-first
- Independent of business logic
- Independent of specific screens

The renderer must **never know what screen it is rendering**.

---

# 1. Renderer Philosophy

Traditional Compose

```text id="renderer01"
Compose

↓

Text()

↓

Image()

↓

Button()
```

SDUI Renderer

```text id="renderer02"
JSON

↓

Parser

↓

ScreenModel

↓

Renderer Engine

↓

Compose Widgets
```

UI is generated dynamically.

---

# 2. Renderer Responsibilities

The Renderer is responsible for

- Rendering Screen
- Rendering Sections
- Rendering Components
- Resolving Styles
- Resolving Actions
- Rendering Nested Components
- Handling Unknown Components
- Maintaining Rendering Order

The Renderer never

- Reads Room
- Parses raw JSON
- Executes business logic
- Performs navigation directly

---

# 3. Renderer Architecture

```text id="renderer03"
Room

↓

Repository

↓

ScreenModel

↓

RendererHost

↓

RendererEngine

↓

ComponentRegistry

↓

WidgetRenderer

↓

Compose
```

---

# 4. Renderer Module Structure

```text id="renderer04"
core-renderer/

├── engine/

├── registry/

├── factory/

├── renderer/

├── component/

├── container/

├── action/

├── style/

├── layout/

├── fallback/

├── extension/

├── util/

└── preview/
```

---

# 5. Rendering Pipeline

```text id="renderer05"
Screen

↓

Sections

↓

Components

↓

Registry Lookup

↓

Renderer

↓

Compose Widget
```

Every component follows the same pipeline.

---

# 6. Rendering Flow

```text id="renderer06"
ScreenModel

↓

RendererEngine

↓

SectionRenderer

↓

ComponentRenderer

↓

Compose
```

---

# 7. Screen Renderer

Responsibilities

- Render screen
- Render layout
- Render sections
- Handle empty screen

Never renders widgets directly.

---

# 8. Section Renderer

Responsibilities

- Render section
- Maintain order
- Apply spacing
- Handle visibility
- Delegate component rendering

---

# 9. Component Renderer

Receives

```text id="renderer07"
ComponentModel
```

Flow

```text id="renderer08"
Component

↓

Registry

↓

Widget Renderer

↓

Compose
```

---

# 10. Component Registry

The registry is the central lookup table.

```text id="renderer09"
Component Type

↓

Renderer
```

Example

| Component | Renderer |
|------------|----------|
| Text | TextRenderer |
| Button | ButtonRenderer |
| Banner | BannerRenderer |
| Search | SearchRenderer |
| ChipGroup | ChipGroupRenderer |
| CarCard | CarCardRenderer |

---

# 11. Widget Factory

The factory resolves the renderer.

```text id="renderer10"
Component

↓

Factory

↓

Renderer
```

No `when(type)` inside the Home screen.

---

# 12. Renderer Interface

Every widget implements a common renderer contract.

Responsibilities

- Validate input model
- Render Compose UI
- Delegate actions
- Apply styles

Renderers remain stateless.

---

# 13. Supported Widgets (Assignment)

Minimum widgets

```text id="renderer11"
Text

Button

Image

Spacer

Divider

Column

Row

LazyColumn

LazyRow

Card

Banner

Search

Chip

ChipGroup

CarCard

CTA

Footer
```

Each widget has its own renderer.

---

# 14. Container Rendering

Containers

```text id="renderer12"
Column

↓

Children
```

```text id="renderer13"
Row

↓

Children
```

```text id="renderer14"
LazyColumn

↓

Items
```

Containers recursively render child components.

---

# 15. Nested Rendering

Renderer supports

```text id="renderer15"
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

Unlimited nesting depth (within reasonable recursion limits).

---

# 16. Style Resolver

Flow

```text id="renderer16"
Component

↓

Style Tokens

↓

Material3

↓

Compose Modifier
```

Supports

- Padding
- Margin (translated to layout spacing)
- Size
- Weight
- Background
- Shape
- Border
- Elevation
- Alignment

---

# 17. Theme Resolver

Reads

```text id="renderer17"
ThemeModel
```

Applies

- Colors
- Typography
- Shapes
- Spacing

Uses Material 3 theme values.

---

# 18. Action Engine Integration

Widget

↓

Click

↓

Action Engine

↓

UiEvent

↓

ViewModel

↓

Navigator / Business Flow

Renderer never navigates directly.

---

# 19. Visibility Rules

Component

```text id="renderer18"
visible=true
```

↓

Render

Component

```text id="renderer19"
visible=false
```

↓

Skip

Invisible widgets do not occupy layout space.

---

# 20. Ordering Rules

Components sorted by

```text id="renderer20"
displayOrder
```

Renderer must not depend on insertion order.

---

# 21. Unknown Component Strategy

Input

```text id="renderer21"
VideoCarouselV5
```

Flow

```text id="renderer22"
Registry Lookup

↓

Missing

↓

UnknownRenderer

↓

Placeholder

↓

Continue
```

Application never crashes.

---

# 22. Unknown Style Strategy

Unknown style token

↓

Ignore unsupported token

↓

Log warning

↓

Continue rendering

---

# 23. Unknown Action Strategy

Unknown action

↓

Log

↓

Ignore

↓

Continue

---

# 24. Empty Screen Strategy

If

```text id="renderer23"
sections = []
```

↓

Render Empty State

No crash.

---

# 25. Rendering Order

```text id="renderer24"
Header

↓

Search

↓

Banner

↓

Categories

↓

Cars

↓

CTA

↓

Footer
```

Order is entirely controlled by JSON.

---

# 26. Renderer Performance Strategy

The renderer should naturally:

- Render only visible Lazy items
- Avoid repeated registry lookups where possible
- Use stable Compose parameters
- Minimize unnecessary recompositions
- Avoid reparsing JSON
- Cache immutable screen models when appropriate

---

# 27. Recomposition Rules

Composable inputs should be

- Immutable
- Stable
- Minimal

Avoid passing mutable collections.

---

# 28. Renderer Extension Strategy

Adding a new widget requires

1. JSON schema update
2. Domain model support (if needed)
3. Widget renderer
4. Registry registration
5. Tests

HomeScreen remains unchanged.

---

# 29. Error Handling

Possible renderer failures

- Unknown widget
- Invalid style
- Invalid layout
- Missing required props

Recovery

↓

Fallback renderer

↓

Continue rendering

---

# 30. Testing Strategy

Unit Tests

- Registry
- Factory
- Style Resolver
- Theme Resolver

Renderer Tests

- Text
- Button
- Banner
- Search
- Chips
- CarCard
- Containers
- Nested rendering
- Unknown widget

Integration Tests

- Renderer + Home
- Renderer + Room
- Live updates

---

# 31. Assignment Rendering Flow

```text id="renderer25"
Landing

↓

Home

↓

Room

↓

ScreenModel

↓

Renderer

↓

Compose

↓

User Interaction

↓

Action Engine

↓

ViewModel
```

---

# 32. Renderer Rules

Always

- Generic
- Stateless
- Recursive
- Testable
- Extensible

Never

- Access Repository
- Read Room
- Parse raw JSON
- Navigate directly
- Hardcode widget types in screens

---

# 33. Acceptance Criteria

The Renderer Engine is complete when:

- Generic renderer implemented
- Registry implemented
- Widget factory implemented
- Nested rendering supported
- Container rendering supported
- Style resolver implemented
- Action integration completed
- Unknown widgets handled safely
- Unknown actions handled safely
- Home renders entirely from JSON

---

# 34. Production AI Prompt

## Objective

Generate the complete `core-renderer` module.

### Generate

#### Engine

- RendererEngine
- RendererHost
- ScreenRenderer
- SectionRenderer
- ComponentRenderer

#### Registry

- ComponentRegistry
- RegistryInitializer

#### Factory

- WidgetFactory

#### Widget Renderers

- TextRenderer
- ButtonRenderer
- ImageRenderer
- SpacerRenderer
- DividerRenderer
- ColumnRenderer
- RowRenderer
- LazyColumnRenderer
- LazyRowRenderer
- CardRenderer
- BannerRenderer
- SearchRenderer
- ChipRenderer
- ChipGroupRenderer
- CarCardRenderer
- CTARenderer
- FooterRenderer
- UnknownRenderer

#### Infrastructure

- StyleResolver
- ThemeResolver
- ActionDispatcher
- ModifierFactory

### Requirements

- Generic architecture
- No business logic
- Stateless renderers
- Material 3
- Recursive rendering
- Nested containers
- Immutable models
- Unknown widget fallback
- Unknown action fallback
- Compatible with Room Local SDUI Server
- Compatible with JSON schema
- Clean Architecture compliant

### Tests

Generate

- Registry tests
- Factory tests
- Renderer tests
- Style resolver tests
- Unknown widget tests
- Nested rendering tests
- Integration tests with Home

### Output Summary

Provide

- Files created
- Files modified
- Deferred files
- Renderer pipeline diagram
- Registry diagram
- Widget dependency graph
- Architecture compliance checklist

---

# Renderer Engine Documentation Status

| Section | Status |
|----------|--------|
| Renderer Architecture | ✅ Complete |
| Rendering Pipeline | ✅ Complete |
| Component Registry | ✅ Complete |
| Widget Factory | ✅ Complete |
| Container Rendering | ✅ Complete |
| Style & Theme Resolution | ✅ Complete |
| Action Integration | ✅ Complete |
| Fallback Strategy | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**22_COMPONENT_REGISTRY_AND_WIDGET_LIBRARY.md**

This document will define the complete widget ecosystem, including:

- Base widget contract
- Widget metadata
- Component registration lifecycle
- Supported widget properties
- Generic property resolution
- Widget catalog
- Custom widget extension strategy
- Design system integration
- Widget validation rules
- Production AI prompt for implementing the complete widget library and registry