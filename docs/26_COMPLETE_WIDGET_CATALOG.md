# 26_COMPLETE_WIDGET_CATALOG.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Generic SDUI Platform
>
> **Module:** `core-renderer`
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - Documents 00–25
> - JSON Schema Design
> - Renderer Engine
> - Component Registry
> - Design System
> - Action Engine

---

# Document Objective

This document is the **Single Source of Truth (SSOT)** for every widget supported by the SDUI platform.

It defines:

- Complete widget catalog
- Widget purpose
- JSON schema
- Required properties
- Optional properties
- Supported styles
- Supported actions
- Validation rules
- Compose mapping
- Renderer implementation guidelines

Adding a new widget must only require:

1. JSON schema
2. Property model
3. Renderer
4. Registry registration

No screen modifications should ever be required.

---

# Widget Architecture

```text
JSON

↓

Parser

↓

ComponentModel

↓

Registry

↓

WidgetRenderer

↓

Compose Widget
```

---

# Common Widget Contract

Every widget shares the following fields.

```json
{
  "id": "unique_component_id",
  "type": "widget_type",
  "displayOrder": 1,
  "visible": true,
  "props": {},
  "style": {},
  "actions": [],
  "children": []
}
```

---

# Common Supported Styles

Every widget supports

- padding
- margin
- width
- height
- background
- foreground
- border
- radius
- elevation
- alignment
- alpha
- visibility

---

# Common Supported Actions

Every widget may support

- navigate
- snackbar
- dialog
- refresh
- updateComponent
- composite

---

# Widget Categories

| Category | Widgets |
|-----------|----------|
| Layout | Column, Row, Box, Spacer, Divider |
| Display | Text, Image, Icon, Badge |
| Input | Button, Search, Chip, ChipGroup |
| Collection | LazyColumn, LazyRow |
| Business | HeroBanner, CarCard, CTASection, Footer |

---

# 1. Column Widget

Purpose

Vertical layout container.

Compose Mapping

```kotlin
Column()
```

Required Props

None

Children

Required

Example JSON

```json
{
  "type":"column",
  "children":[]
}
```

---

# 2. Row Widget

Compose

```kotlin
Row()
```

Supports

- weight
- alignment
- spacing

Children required.

---

# 3. Box Widget

Compose

```kotlin
Box()
```

Supports overlay layouts.

---

# 4. Spacer Widget

Purpose

Create spacing.

Required Props

```json
{
  "height":"16dp"
}
```

or

```json
{
  "width":"16dp"
}
```

Compose

```kotlin
Spacer()
```

---

# 5. Divider Widget

Purpose

Visual separation.

Required Props

None

Optional

- thickness
- color

Compose

```kotlin
HorizontalDivider()
```

---

# 6. Text Widget

Purpose

Display text.

Required Props

```json
{
  "text":"Cars24"
}
```

Optional

- typography
- color
- maxLines
- overflow
- textAlign

Compose

```kotlin
Text()
```

Validation

- text required

---

# 7. Image Widget

Required

```json
{
  "url":"..."
}
```

Optional

- contentScale
- placeholder
- shape

Compose

Async image implementation selected during implementation.

---

# 8. Icon Widget

Required

```json
{
  "icon":"search"
}
```

Uses Design System icon catalog.

---

# 9. Badge Widget

Props

```json
{
  "text":"NEW"
}
```

Used inside cards.

---

# 10. Button Widget

Required

```json
{
  "text":"Buy Car"
}
```

Actions

Required

Compose

```kotlin
Button()
```

Validation

- action required

---

# 11. Search Widget

Required

```json
{
  "hint":"Search cars"
}
```

Optional

- leadingIcon
- trailingIcon
- enabled

Current assignment supports display only.

---

# 12. Chip Widget

Required

```json
{
  "text":"SUV"
}
```

Optional

- selected
- enabled

---

# 13. ChipGroup Widget

Children

Chip widgets

Supports

- single selection
- multi selection

---

# 14. LazyColumn Widget

Children

Dynamic list

Compose

```kotlin
LazyColumn()
```

---

# 15. LazyRow Widget

Compose

```kotlin
LazyRow()
```

Used for featured cars.

---

# 16. Card Widget

Container widget.

Children

Required.

Optional

- elevation
- border
- radius

---

# 17. HeroBanner Widget

Required Props

```json
{
  "title":"Find Your Dream Car",
  "subtitle":"Best Deals",
  "image":"..."
}
```

Optional

- CTA
- background

---

# 18. CarCard Widget

Required

```json
{
  "title":"Honda City",
  "price":"₹8.5L",
  "image":"..."
}
```

Optional

- badges
- location
- EMI
- fuel
- transmission

---

# 19. CTA Section Widget

Contains

Buttons

Examples

- Buy
- Sell
- Finance

---

# 20. Footer Widget

Supports

- text
- copyright
- version

Optional.

---

# Widget Validation Rules

Every widget validates

- Required props
- Children
- Supported actions
- Supported styles
- Visibility
- Version

---

# Widget Property Resolution

```text
JSON

↓

Props DTO

↓

Domain Props

↓

Renderer
```

---

# Unknown Widget

Unknown type

↓

UnknownRenderer

↓

Placeholder

↓

Continue

Never crash.

---

# Widget Registration Matrix

| Widget | Renderer | Validator |
|----------|-----------|-----------|
| Column | ColumnRenderer | LayoutValidator |
| Row | RowRenderer | LayoutValidator |
| Box | BoxRenderer | LayoutValidator |
| Spacer | SpacerRenderer | LayoutValidator |
| Divider | DividerRenderer | LayoutValidator |
| Text | TextRenderer | TextValidator |
| Image | ImageRenderer | ImageValidator |
| Icon | IconRenderer | IconValidator |
| Badge | BadgeRenderer | BadgeValidator |
| Button | ButtonRenderer | ButtonValidator |
| Search | SearchRenderer | SearchValidator |
| Chip | ChipRenderer | ChipValidator |
| ChipGroup | ChipGroupRenderer | ChipGroupValidator |
| LazyColumn | LazyColumnRenderer | CollectionValidator |
| LazyRow | LazyRowRenderer | CollectionValidator |
| Card | CardRenderer | CardValidator |
| HeroBanner | HeroBannerRenderer | BannerValidator |
| CarCard | CarCardRenderer | CarCardValidator |
| CTASection | CTARenderer | CTAValidator |
| Footer | FooterRenderer | FooterValidator |

---

# Renderer Rules

Every renderer must

- Be stateless
- Accept immutable models
- Use Material 3
- Delegate actions
- Resolve styles centrally
- Support previews
- Handle null-safe optional props

---

# Assignment Initial Home Layout

The initial JSON should render

```text
Column

├── Header(Text)

├── Search

├── HeroBanner

├── ChipGroup

├── LazyRow(CarCards)

├── CTASection

└── Footer
```

No widget is manually added to HomeScreen.

---

# Widget Testing Strategy

Each widget requires

## Unit Tests

- Validation
- Property mapping

## Renderer Tests

- Correct Compose output
- Style application
- Action dispatch

## Integration Tests

- Registry lookup
- Renderer pipeline
- Home rendering

---

# Acceptance Criteria

Widget Catalog is complete when

- Every supported widget documented
- JSON schema defined
- Required props documented
- Optional props documented
- Validation rules defined
- Compose mapping defined
- Renderer rules defined
- Testing strategy documented

---

# Production AI Prompt

## Objective

Generate the complete widget library for the SDUI platform.

### Generate

#### Layout Widgets

- ColumnRenderer
- RowRenderer
- BoxRenderer
- SpacerRenderer
- DividerRenderer

#### Display Widgets

- TextRenderer
- ImageRenderer
- IconRenderer
- BadgeRenderer

#### Input Widgets

- ButtonRenderer
- SearchRenderer
- ChipRenderer
- ChipGroupRenderer

#### Collection Widgets

- LazyColumnRenderer
- LazyRowRenderer

#### Business Widgets

- HeroBannerRenderer
- CarCardRenderer
- CTASectionRenderer
- FooterRenderer

#### Validation

Generate validator for every widget.

#### Preview

Generate Compose previews for every widget.

### Requirements

- Stateless implementation
- Material 3
- Immutable property models
- Generic renderer architecture
- JSON-driven
- No business logic
- Centralized style resolution
- Action Engine integration
- Clean Architecture compliant

### Tests

Generate

- Widget validator tests
- Renderer tests
- Registry tests
- Integration tests
- Preview verification

### Output Summary

Provide

- Files created
- Files modified
- Widget implementation order
- Registry update summary
- Test coverage summary
- Architecture compliance checklist

---

# Widget Catalog Status

| Section | Status |
|----------|--------|
| Widget Catalog | ✅ Complete |
| JSON Contracts | ✅ Complete |
| Validation Rules | ✅ Complete |
| Compose Mapping | ✅ Complete |
| Registry Mapping | ✅ Complete |
| Renderer Guidelines | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**27_ASSIGNMENT_IMPLEMENTATION_MASTER_PROMPT.md**

This final document will consolidate **Documents 00–26** into a single master implementation prompt. It will include:

- End-to-end implementation workflow
- File creation order
- Module dependency order
- AI implementation rules
- Incremental development strategy
- "Never regenerate existing files" policy
- Integration checkpoints
- Testing checkpoints
- Final submission checklist

This will become the single prompt used to generate the entire assignment phase by phase with production-quality consistency.