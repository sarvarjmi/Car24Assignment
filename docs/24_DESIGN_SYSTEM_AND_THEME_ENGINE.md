# 24_DESIGN_SYSTEM_AND_THEME_ENGINE.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Material 3 Design System
>
> **Module:** `core-designsystem`
>
> **Theme Engine:** JSON Driven + Material 3 Compatible
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - 00–23 Documentation
> - SDUI Renderer Engine
> - JSON Schema Design
> - Component Registry
> - Home Screen Design

---

# Document Objective

This document defines the complete **Design System** and **Theme Engine** used by the SDUI platform.

Unlike a traditional Android application, every widget should obtain its visual appearance from a centralized Design System.

The Theme Engine must support:

- Material 3
- Design Tokens
- SDUI JSON Styling
- Theme Resolution
- Dynamic Theme Switching
- Future Remote Theme Updates

The renderer should never directly hardcode colors, typography, or spacing.

---

# 1. Design Philosophy

The UI should never contain hardcoded values.

Instead of

```kotlin
Text(
    color = Color.Red,
    fontSize = 18.sp
)
```

everything comes from

```text
JSON

↓

Theme

↓

Design Tokens

↓

Theme Resolver

↓

Material3

↓

Compose
```

---

# 2. Module Structure

```text
core-designsystem/

├── color/

├── typography/

├── spacing/

├── shape/

├── elevation/

├── icon/

├── token/

├── theme/

├── resolver/

├── preview/

├── util/

└── extension/
```

---

# 3. Design System Architecture

```text
JSON Style

↓

Theme Resolver

↓

Design Tokens

↓

Material Theme

↓

Compose Widgets
```

---

# 4. Design Tokens

All visual values are represented as tokens.

Supported tokens

```text
Color

Typography

Spacing

Radius

Border

Elevation

Opacity

Icon Size

Animation (Future)
```

---

# 5. Color System

Supported semantic colors

```text
Primary

OnPrimary

Secondary

OnSecondary

Background

Surface

SurfaceVariant

Error

Success

Warning

Info

Outline
```

Never reference raw hex values inside widgets.

---

# 6. Typography System

Supported styles

```text
DisplayLarge

DisplayMedium

HeadlineLarge

HeadlineMedium

TitleLarge

TitleMedium

BodyLarge

BodyMedium

BodySmall

LabelLarge

LabelMedium

LabelSmall
```

Uses Material 3 Typography.

---

# 7. Spacing System

Spacing tokens

```text
None

XS

S

M

L

XL

XXL
```

Mapped to Dp values.

Never hardcode padding.

---

# 8. Shape System

Supported shapes

```text
None

Small

Medium

Large

ExtraLarge

Full
```

Maps to Material Shapes.

---

# 9. Elevation System

Tokens

```text
Level0

Level1

Level2

Level3

Level4

Level5
```

Widgets consume elevation tokens.

---

# 10. Border System

Border properties

```text
Width

Color

Shape
```

Renderer converts tokens into Compose borders.

---

# 11. Icon System

Centralized icon catalog.

Categories

```text
Navigation

Action

Status

Search

Car

Profile

Settings
```

No widget references drawable resources directly.

---

# 12. Theme Model

Theme contains

```text
Colors

Typography

Spacing

Shapes

Elevation

Icons
```

Represented as immutable models.

---

# 13. Theme Resolver

Flow

```text
Theme JSON

↓

Theme Resolver

↓

Material3 Theme

↓

Widget
```

Widgets receive resolved values only.

---

# 14. Style Resolver

Responsibilities

- Resolve colors
- Resolve spacing
- Resolve typography
- Resolve borders
- Resolve radius
- Resolve alignment

Renderer never interprets raw style tokens.

---

# 15. Supported Style Properties

```text
padding

margin

background

foreground

shape

border

width

height

weight

alpha

visibility

alignment

elevation
```

---

# 16. Widget Style Flow

```text
Component Style

↓

Style Resolver

↓

Modifier

↓

Compose
```

---

# 17. Dark Theme Support

Architecture supports

```text
Light

Dark

System Default
```

Current assignment may default to Light Theme, but the engine remains ready for dynamic switching.

---

# 18. Dynamic Theme

Future-ready flow

```text
Server

↓

Theme JSON

↓

Room

↓

Theme Resolver

↓

Compose

↓

UI Updated
```

No app restart required.

---

# 19. Theme Versioning

Every theme contains

```json
{
  "themeVersion": "1.0.0"
}
```

Validated before applying.

---

# 20. Widget Style Hierarchy

Priority

```text
Component Style

↓

Section Style

↓

Screen Style

↓

Theme Defaults

↓

Material Defaults
```

Most specific style wins.

---

# 21. Responsive Design

Support

- Small phones
- Large phones
- Foldables (future)
- Tablets (future)

Use adaptive spacing tokens instead of fixed values.

---

# 22. Accessibility

Ensure

- Minimum touch target
- Color contrast
- Dynamic font scaling
- Screen reader compatibility

The Design System should not reduce accessibility.

---

# 23. Theme Validation

Validate

- Required colors
- Typography completeness
- Shape compatibility
- Elevation values
- Duplicate tokens

Fallback to Material defaults when safe.

---

# 24. Theme Fallback Strategy

Missing token

↓

Theme Resolver

↓

Default Token

↓

Material Default

↓

Continue Rendering

Never crash because of a missing theme property.

---

# 25. Performance Strategy

The Theme Engine should naturally

- Resolve tokens once
- Cache immutable themes
- Avoid repeated token lookups
- Avoid recomposition caused by identical theme values

---

# 26. Testing Strategy

Unit Tests

- Theme Resolver
- Style Resolver
- Token mapping

Renderer Tests

- Widget style application
- Typography mapping
- Color mapping

Integration Tests

- Theme + Renderer
- Dynamic theme updates

---

# 27. Assignment Theme Flow

```text
Theme JSON

↓

Theme Resolver

↓

Design Tokens

↓

Renderer

↓

Compose
```

---

# 28. Best Practices

Always

- Use semantic colors
- Use spacing tokens
- Use typography tokens
- Use immutable theme models
- Keep widgets theme-aware

Never

- Hardcode colors
- Hardcode spacing
- Hardcode font sizes
- Use widget-specific themes
- Bypass the Theme Resolver

---

# 29. Acceptance Criteria

The Design System is complete when

- Design tokens defined
- Theme model documented
- Theme Resolver implemented
- Style Resolver implemented
- Material 3 integrated
- Widget styling centralized
- Theme validation documented
- Dynamic theming architecture ready
- Tests defined

---

# 30. Production AI Prompt

## Objective

Generate the complete `core-designsystem` module.

### Generate

#### Theme Infrastructure

- AppTheme
- ThemeModel
- ThemeResolver
- StyleResolver
- ModifierFactory

#### Design Tokens

- ColorTokens
- TypographyTokens
- SpacingTokens
- ShapeTokens
- ElevationTokens
- BorderTokens

#### Material Integration

- ColorScheme provider
- Typography provider
- Shapes provider

#### Utilities

- TokenMapper
- ThemeValidator
- ThemeDefaults

### Requirements

- Material 3 only
- Immutable theme models
- JSON-driven styling
- Semantic colors
- Centralized token resolution
- Widget-independent design system
- Compatible with SDUI Renderer
- Compatible with JSON Schema
- Clean Architecture compliant

### Tests

Generate

- Theme Resolver tests
- Style Resolver tests
- Token mapping tests
- Theme validation tests
- Renderer style integration tests

### Output Summary

Provide

- Files created
- Files modified
- Theme architecture diagram
- Token hierarchy
- Style resolution flow
- Architecture compliance checklist

---

# Design System Documentation Status

| Section | Status |
|----------|--------|
| Design System Architecture | ✅ Complete |
| Theme Engine | ✅ Complete |
| Design Tokens | ✅ Complete |
| Style Resolver | ✅ Complete |
| Material 3 Integration | ✅ Complete |
| Dynamic Theme Support | ✅ Complete |
| Theme Validation | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**25_ACTION_ENGINE_AND_EVENT_SYSTEM.md**

This document will define one of the most important parts of the SDUI platform:

- Generic Action Engine
- Event Dispatcher
- Action Registry
- Navigation Actions
- Dialog Actions
- Snackbar Actions
- State Update Actions
- Custom Action Support
- Action Validation
- Action Versioning
- Action Execution Pipeline
- Safe Action Handling
- Production AI prompt for implementing the complete Action Engine.