# 32_PHASE_04_CORE_DESIGN_SYSTEM.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 04 – Core Design System
>
> **Module:** `core-designsystem`
>
> **Platform:** Android – Kotlin + Jetpack Compose
>
> **Architecture:** Clean Architecture + Material 3 + Token-Based Design System
>
> **Status:** Implementation Phase 04
>
> **Priority:** Critical
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – `core-common`
> - ✅ Phase 03 – `core-ui`
> - 📖 `24_DESIGN_SYSTEM_AND_THEME_ENGINE.md`
> - 📖 `26_COMPLETE_WIDGET_CATALOG.md`

---

# 1. Phase Objective

Phase 04 implements the centralized visual language of the application.

The purpose of `core-designsystem` is to ensure that:

- Compose components do not hardcode visual values.
- SDUI widgets can reference semantic design tokens.
- Material 3 remains the underlying UI system.
- Home and Server Panel have consistent styling.
- Light and dark themes are structurally supported.
- JSON-defined styles can eventually resolve into safe Compose values.
- Unsupported or missing tokens have deterministic fallbacks.
- The renderer does not need to understand raw Compose styling.

The module becomes the visual foundation for:

```text
core-ui

feature-landing

feature-home

feature-server

core-renderer
```

---

# 2. Scope of Phase 04

Implement:

- Material 3 theme foundation
- Semantic color tokens
- Typography tokens
- Spacing tokens
- Shape tokens
- Elevation tokens
- Size tokens
- Border tokens
- Icon tokens/catalog
- Theme mode abstraction
- Theme models
- Theme defaults
- Token resolvers
- Theme resolver
- Initial style primitives required by SDUI
- Compose theme extensions
- Preview theme infrastructure
- Unit tests
- Compose theme tests where valuable

Do not implement:

- Full SDUI `StyleResolver`
- JSON parsing
- Room theme persistence
- Component Registry
- Widget renderers
- Remote theme loading
- Server Panel theme editor
- Dynamic backend themes
- Feature-specific visual components

Those belong to later phases.

---

# 3. Important Architecture Correction

`core-designsystem` is a lower-level visual foundation.

Therefore the preferred dependency direction is:

```text
core-common

     ↓

core-designsystem

     ↓

core-ui

     ↓

features
```

Not:

```text
core-designsystem
        ↓
core-ui
```

The Design System must not depend on reusable UI components.

This prevents circular dependencies when `core-ui` consumes Design System tokens.

If Phase 03 initially used raw `MaterialTheme` values, Phase 04 may make **minimal targeted modifications** to `core-ui` so reusable components consume the new Design System.

Do not regenerate the entire `core-ui` module.

---

# 4. Architecture Position

```text
                    core-common
                         │
                         ▼
                 core-designsystem
                         │
             ┌───────────┴───────────┐
             ▼                       ▼
          core-ui              core-renderer
             │                       │
             └───────────┬───────────┘
                         ▼
                     features
```

---

# 5. Module Responsibility

`core-designsystem` owns:

```text
Theme
Tokens
Semantic Colors
Typography
Spacing
Shapes
Elevation
Borders
Sizes
Icons
Token Resolution
Theme Defaults
```

It does not own:

```text
Screens
Feature Components
Navigation
Repositories
Room
ViewModels
UseCases
JSON Parsing
SDUI Rendering
```

---

# 6. Recommended Folder Structure

```text
core-designsystem/
└── src/
    ├── main/
    │   └── kotlin/
    │       └── <base-package>/core/designsystem/
    │           │
    │           ├── theme/
    │           │   ├── AppTheme.kt
    │           │   ├── AppThemeMode.kt
    │           │   ├── AppColorScheme.kt
    │           │   ├── AppTypography.kt
    │           │   ├── AppShapes.kt
    │           │   └── ThemeDefaults.kt
    │           │
    │           ├── token/
    │           │   ├── ColorToken.kt
    │           │   ├── TypographyToken.kt
    │           │   ├── SpacingToken.kt
    │           │   ├── ShapeToken.kt
    │           │   ├── ElevationToken.kt
    │           │   ├── SizeToken.kt
    │           │   ├── BorderToken.kt
    │           │   └── IconToken.kt
    │           │
    │           ├── resolver/
    │           │   ├── ColorTokenResolver.kt
    │           │   ├── TypographyTokenResolver.kt
    │           │   ├── SpacingTokenResolver.kt
    │           │   ├── ShapeTokenResolver.kt
    │           │   ├── ElevationTokenResolver.kt
    │           │   ├── SizeTokenResolver.kt
    │           │   └── IconTokenResolver.kt
    │           │
    │           ├── model/
    │           │   ├── BorderStyle.kt
    │           │   └── ResolvedTheme.kt
    │           │
    │           ├── icon/
    │           │   └── AppIcons.kt
    │           │
    │           ├── extension/
    │           │   └── MaterialThemeExtensions.kt
    │           │
    │           └── preview/
    │               └── AppThemePreview.kt
    │
    └── test/
```

Use the project's existing base package instead of inventing a new one.

---

# 7. Design System Philosophy

The application should reference **semantic intent**, not arbitrary visual values.

Avoid:

```text
#FF0000
16.dp
18.sp
RoundedCornerShape(12.dp)
```

inside feature and renderer code.

Prefer:

```text
ColorToken.Error

SpacingToken.Medium

TypographyToken.TitleMedium

ShapeToken.Medium
```

Resolution becomes:

```text
Semantic Token

↓

Resolver

↓

Current Theme

↓

Material 3 Value

↓

Compose
```

---

# 8. Material 3 Foundation

Material 3 remains the final rendering system.

The Design System should wrap and extend it rather than replace it.

```text
Application Theme

↓

AppTheme

↓

MaterialTheme

├── ColorScheme
├── Typography
└── Shapes
```

This gives the project:

- Native Compose compatibility
- Accessibility behavior
- Stable Material components
- Predictable theming
- Less custom framework code

---

# 9. AppTheme

Create the application theme entry point.

Conceptually:

```text
AppTheme

├── Theme Mode
├── Color Scheme
├── Typography
├── Shapes
└── Content
```

It should eventually support:

```text
LIGHT

DARK

SYSTEM
```

Do not couple `AppTheme` to a ViewModel or persistence mechanism.

Theme selection must be passed as input.

---

# 10. Theme Mode

Define a stable abstraction:

```text
AppThemeMode

├── LIGHT
├── DARK
└── SYSTEM
```

The mode represents user/application intent.

It must not contain business logic.

---

# 11. Light Theme

Create a complete Material 3 light color scheme.

At minimum define semantic values for:

- primary
- onPrimary
- primaryContainer
- onPrimaryContainer
- secondary
- onSecondary
- secondaryContainer
- onSecondaryContainer
- tertiary
- background
- onBackground
- surface
- onSurface
- surfaceVariant / equivalent supported Material role
- outline
- error
- onError

Additional project semantic colors may be exposed separately.

---

# 12. Dark Theme

The architecture must support a dark color scheme from this phase.

Even if assignment screenshots primarily use light mode, dark mode must not require architectural changes later.

Do not create partially invalid dark colors merely to satisfy an API.

Use complete, readable defaults.

---

# 13. Dynamic Color Decision

Android dynamic color should **not be enabled by default** for this assignment.

Reason:

The assignment demonstrates controlled SDUI styling.

System-generated dynamic colors could make renderer output inconsistent across devices and screenshots.

Architecture may allow dynamic color later.

Current default:

```text
dynamicColor = false
```

---

# 14. Semantic Color Tokens

Define tokens based on meaning.

Example catalog:

```text
PRIMARY

ON_PRIMARY

PRIMARY_CONTAINER

ON_PRIMARY_CONTAINER

SECONDARY

ON_SECONDARY

BACKGROUND

ON_BACKGROUND

SURFACE

ON_SURFACE

SURFACE_VARIANT

OUTLINE

ERROR

ON_ERROR

SUCCESS

ON_SUCCESS

WARNING

ON_WARNING

INFO

ON_INFO

TRANSPARENT
```

Avoid tokens such as:

```text
RED
BLUE
GREEN
GRAY_400
```

in public renderer contracts.

Implementation palettes may internally use raw colors.

Public APIs should remain semantic.

---

# 15. Color Resolution

Required flow:

```text
ColorToken

↓

ColorTokenResolver

↓

Current App Theme

↓

Compose Color
```

Unknown SDUI strings must not be resolved directly inside Composables.

Later:

```text
JSON "primary"

↓

SDUI Style Mapper

↓

ColorToken.PRIMARY

↓

ColorTokenResolver
```

---

# 16. Typography System

Typography must build upon Material 3.

Required semantic token coverage should include:

```text
DISPLAY_LARGE
DISPLAY_MEDIUM
DISPLAY_SMALL

HEADLINE_LARGE
HEADLINE_MEDIUM
HEADLINE_SMALL

TITLE_LARGE
TITLE_MEDIUM
TITLE_SMALL

BODY_LARGE
BODY_MEDIUM
BODY_SMALL

LABEL_LARGE
LABEL_MEDIUM
LABEL_SMALL
```

---

# 17. Typography Rules

Typography must support:

- Font scaling
- Accessibility
- Material 3 hierarchy
- Predictable line height
- Predictable font weight

Do not disable system font scaling.

Do not convert typography to fixed pixel values.

---

# 18. Font Strategy

Prefer system/default fonts unless the assignment explicitly requires a custom brand font.

This avoids:

- Unnecessary assets
- Licensing concerns
- APK growth
- Font-loading complexity

A custom font can be introduced later without changing token contracts.

---

# 19. Spacing System

Create semantic spacing tokens.

Recommended baseline:

| Token | Suggested Value |
|---|---:|
| None | 0.dp |
| XXS | 2.dp |
| XS | 4.dp |
| Small | 8.dp |
| Medium | 16.dp |
| Large | 24.dp |
| XL | 32.dp |
| XXL | 48.dp |

The exact values should remain centralized.

---

# 20. Spacing Usage

Instead of:

```text
padding(16.dp)
```

feature and renderer code should conceptually use:

```text
SpacingToken.Medium
```

Resolution:

```text
SpacingToken

↓

SpacingTokenResolver

↓

Dp
```

---

# 21. Shape System

Define:

```text
NONE

EXTRA_SMALL

SMALL

MEDIUM

LARGE

EXTRA_LARGE

FULL
```

Map compatible values to Material 3 shapes.

`FULL` may be used for pills/chips.

---

# 22. Shape Resolution

```text
ShapeToken

↓

ShapeTokenResolver

↓

Shape
```

The renderer must never parse radius strings directly.

---

# 23. Elevation System

Define semantic elevation levels:

```text
LEVEL_0

LEVEL_1

LEVEL_2

LEVEL_3

LEVEL_4

LEVEL_5
```

Do not allow arbitrary elevation values throughout widgets unless explicitly required by the JSON contract.

---

# 24. Size Tokens

Introduce reusable size tokens for common UI requirements.

Examples:

```text
ICON_SMALL

ICON_MEDIUM

ICON_LARGE

TOUCH_TARGET_MIN

BUTTON_MIN_HEIGHT

AVATAR_SMALL

AVATAR_MEDIUM
```

Do not overbuild a large size framework.

Only define values useful to this assignment.

---

# 25. Minimum Touch Target

Interactive elements must respect accessible touch targets.

Target:

```text
48.dp minimum
```

unless Material 3 already enforces the requirement.

Do not shrink interaction areas simply because JSON requests a smaller visual size.

Accessibility takes precedence.

---

# 26. Border System

A border should be represented using semantic information:

```text
BorderStyle

├── width
├── colorToken
└── shapeToken
```

Avoid passing raw arbitrary Compose values throughout SDUI models.

---

# 27. Icon System

Create a controlled icon catalog.

Example tokens:

```text
BACK

FORWARD

HOME

SERVER

SEARCH

EDIT

SAVE

REFRESH

CLOSE

INFO

WARNING

ERROR

SUCCESS

EXPAND_MORE

COPY

PREVIEW
```

Only icons actually required by the assignment should be added initially.

---

# 28. Icon Resolution

Flow:

```text
IconToken

↓

IconTokenResolver

↓

ImageVector / Painter abstraction

↓

Compose Icon
```

Do not allow arbitrary drawable resource names from JSON to be loaded dynamically.

---

# 29. Theme Defaults

Create centralized safe defaults.

`ThemeDefaults` should define fallback tokens such as:

```text
DefaultTextColor

DefaultBackground

DefaultSpacing

DefaultShape

DefaultElevation

DefaultTypography
```

This becomes important for SDUI resilience.

---

# 30. Token Resolver Architecture

Each token family should have one responsibility.

```text
ColorTokenResolver
TypographyTokenResolver
SpacingTokenResolver
ShapeTokenResolver
ElevationTokenResolver
SizeTokenResolver
IconTokenResolver
```

Do not create one giant resolver containing every concern.

---

# 31. Resolver Characteristics

Resolvers should be:

- Deterministic
- Stateless where possible
- Small
- Independently testable
- Free of business logic

Input:

```text
Token
```

Output:

```text
Resolved Compose Value
```

---

# 32. SDUI Compatibility Boundary

This phase must prepare for SDUI without implementing the entire SDUI style engine.

Correct future flow:

```text
JSON

↓

core-json

↓

SDUI Style Model

↓

core-renderer StyleResolver

↓

core-designsystem Token Resolver

↓

Compose
```

`core-designsystem` must not depend on:

```text
core-json
core-renderer
Room
```

This keeps dependency direction clean.

---

# 33. JSON Token Contract Preparation

The following canonical token strings should be documented for future parser mapping.

Example:

```text
primary
on_primary
background
surface
error
success
warning
```

Spacing:

```text
none
xxs
xs
small
medium
large
xl
xxl
```

Typography:

```text
title_large
title_medium
body_large
body_medium
label_large
```

These strings belong to the SDUI contract documentation.

The Design System itself should preferably operate on typed tokens.

---

# 34. Unknown Token Strategy

A remote/local JSON payload must never crash the application because of:

```text
"color": "future_color_123"
```

Future resolution flow:

```text
Raw Token

↓

Token Mapping

↓

Known?
   │
 ┌─┴─┐
 │   │
Yes  No
 │   │
 ▼   ▼
Use  Fallback + Log
```

The fallback must be deterministic.

---

# 35. Invalid Style Safety

Design System resolution must never result in:

- Negative spacing
- Negative size
- Invalid alpha
- Invalid elevation
- Unreadable default text
- Runtime parsing exceptions

Validation occurs before values reach Compose.

---

# 36. Theme Hierarchy

Future SDUI styling follows:

```text
Material Default
       ↑
Application Theme
       ↑
Screen Style
       ↑
Section Style
       ↑
Component Style
```

More specific values override less specific values.

Phase 04 implements the **Application Theme / Design Token foundation** only.

Screen/Section/Component cascading belongs to the renderer style phase.

---

# 37. Compose Theme Extensions

Expose ergonomic access where useful.

Conceptually:

```text
MaterialTheme.appSpacing

MaterialTheme.appColors

MaterialTheme.appElevation
```

Use `CompositionLocal` only when it materially improves access to non-Material token groups.

Do not create CompositionLocals for values already handled correctly by `MaterialTheme`.

---

# 38. CompositionLocal Rules

Appropriate for:

- Spacing
- Additional semantic colors
- Elevation scale
- Additional sizes

Avoid CompositionLocal for:

- Repositories
- ViewModels
- Navigation
- Mutable business state

---

# 39. `core-ui` Integration

After Phase 04, generic `core-ui` components should gradually consume Design System values.

Example:

```text
PrimaryButton

↓

MaterialTheme

+

Design Tokens
```

Only modify `core-ui` files that actually require token integration.

Do not regenerate previously completed components.

---

# 40. Feature Integration

Features should consume:

```text
core-ui
core-designsystem
```

They should not define their own independent:

- Color palettes
- Typography systems
- Spacing scales
- Shape scales

---

# 41. Renderer Integration

Later renderer flow:

```text
ComponentModel.style

↓

StyleResolver

↓

Design Tokens

↓

ModifierFactory

↓

Compose Widget
```

This keeps SDUI styling consistent with native screens.

---

# 42. Landing Screen Consideration

The Landing screen is a native navigation entry point, not an SDUI-rendered Home widget.

It should still use the same:

- Theme
- Typography
- Spacing
- Buttons
- Shapes

This prevents the native shell and JSON-rendered Home from looking like different applications.

---

# 43. Home Screen Consideration

The Home screen must not choose colors itself.

Correct:

```text
Home

↓

RendererHost

↓

Widget Renderer

↓

Design System
```

Incorrect:

```text
HomeScreen

↓

Hardcoded Color
```

---

# 44. Server Panel Consideration

The Server Panel should use the Design System for its native editor controls:

- Dropdowns
- Text fields
- Buttons
- Validation messages
- Preview container
- App bars

JSON being edited may control Home styling, but the editor UI itself remains native Material 3 UI.

---

# 45. State

`core-designsystem` must not own mutable application state.

Theme state belongs to the appropriate presentation/application owner.

The theme API receives resolved configuration and renders accordingly.

---

# 46. Dependency Injection

Most token objects should not require Hilt.

Prefer:

```text
Pure objects
Pure functions
Immutable values
```

Use DI only where a resolver genuinely needs abstraction.

Do not inject trivial constants.

---

# 47. Dependency Requirements

Allowed:

- Kotlin
- Jetpack Compose UI
- Material 3
- `core-common` where genuinely required

Potentially allowed:

- Stable Material Icons subset if already approved in the project dependency strategy

Not allowed:

- Room
- Navigation Compose
- Hilt ViewModel
- Retrofit
- Feature modules
- `core-renderer`
- `core-json`
- Data layer
- Domain business layer

---

# 48. Stable Dependency Rule

Do not add a new dependency merely to implement:

- Colors
- Spacing
- Shapes
- Typography
- Icons

Prefer stable AndroidX/Compose capabilities already available.

All dependency declarations must use:

```text
libs.versions.toml
```

Never hardcode library versions.

---

# 49. Preview Infrastructure

Provide reusable theme previews.

At minimum:

```text
Light Theme Preview

Dark Theme Preview
```

Useful previews:

```text
Typography Catalog
Color Catalog
Spacing Catalog
Shape Catalog
```

Catalog previews are development aids, not production screens.

---

# 50. Accessibility Requirements

The Design System must preserve:

- System font scaling
- Material touch targets
- Readable contrast
- Semantic content
- Dark-theme readability

Do not:

- Force tiny text
- Disable font scaling
- Encode meaning using color alone
- Make clickable components visually accessible but semantically inaccessible

---

# 51. Contrast Safety

Semantic colors should be paired correctly.

Examples:

```text
primary ↔ onPrimary

surface ↔ onSurface

error ↔ onError
```

Do not assume one text color works on every background.

---

# 52. Responsive Design

Tokens should support multiple device sizes without feature rewrites.

For this assignment:

- Prioritize phone layouts.
- Avoid hardcoded screen widths.
- Keep spacing semantic.
- Do not build an unnecessary tablet framework.

Future adaptive layouts should be possible without replacing the token system.

---

# 53. Performance Considerations

This phase does **not** introduce `Phase_6_Performance.md` or benchmarking work.

However, normal production practices still apply:

- Keep theme models immutable.
- Avoid rebuilding equivalent objects unnecessarily.
- Avoid expensive calculations during composition.
- Resolve static token mappings efficiently.
- Keep CompositionLocal usage controlled.

---

# 54. Error Handling

Theme-related failures should degrade gracefully.

Examples:

```text
Unknown Color Token
        ↓
Fallback Semantic Color
```

```text
Unknown Typography Token
        ↓
BodyMedium
```

```text
Unknown Shape Token
        ↓
Medium Shape
```

No styling problem should crash Home rendering.

---

# 55. Logging

The Design System should not aggressively log normal rendering.

Warnings are appropriate only for meaningful fallback scenarios during SDUI token mapping.

Logging implementation should use the abstraction established by `core-common` when applicable.

---

# 56. Testing Strategy

Testing is divided into:

```text
Unit Tests

+

Compose Tests

+

Integration Verification
```

---

# 57. Color Token Tests

Verify:

- Every token resolves.
- Light theme mapping is correct.
- Dark theme mapping is correct.
- Semantic fallback exists.
- No required token resolves unexpectedly to an unusable value.

---

# 58. Typography Tests

Verify:

- Every token maps to a style.
- Fallback typography is defined.
- Material hierarchy remains available.

Do not write brittle tests asserting every internal font metric unless necessary.

---

# 59. Spacing Tests

Verify:

- Every spacing token resolves.
- Values are non-negative.
- Ordering is logical.

Example invariant:

```text
XS < Small < Medium < Large
```

---

# 60. Shape Tests

Verify:

- Every token resolves.
- `NONE` has no radius.
- `FULL` behaves as pill/circle-compatible where applicable.

---

# 61. Elevation Tests

Verify:

- Levels are non-negative.
- Levels increase predictably.
- Default elevation exists.

---

# 62. Theme Tests

Verify:

- Light theme composes.
- Dark theme composes.
- Material values are available inside `AppTheme`.
- Additional Design System values are available.
- Content renders without exceptions.

---

# 63. Accessibility Verification

Manually/automatically verify where practical:

- Large font scale
- Dark mode
- Touch target behavior
- Contrast-sensitive components

Full accessibility testing remains part of feature/UI validation too.

---

# 64. Test Folder Structure

```text
core-designsystem/
└── src/test/kotlin/<base-package>/core/designsystem/
    ├── token/
    ├── resolver/
    └── theme/
```

Compose instrumentation tests should only be introduced where JVM tests cannot verify behavior meaningfully.

---

# 65. Acceptance Criteria

Phase 04 is complete when:

- [ ] `core-designsystem` builds independently.
- [ ] Material 3 theme is configured.
- [ ] Light theme is implemented.
- [ ] Dark theme is implemented.
- [ ] Theme mode abstraction exists.
- [ ] Semantic color tokens exist.
- [ ] Typography tokens exist.
- [ ] Spacing tokens exist.
- [ ] Shape tokens exist.
- [ ] Elevation tokens exist.
- [ ] Required size tokens exist.
- [ ] Border model exists.
- [ ] Required icon catalog exists.
- [ ] Token resolvers are implemented.
- [ ] Safe defaults are defined.
- [ ] Unknown-token fallback strategy is supported.
- [ ] Preview theme exists.
- [ ] Tests pass.
- [ ] No Room dependency exists.
- [ ] No Navigation dependency exists.
- [ ] No feature dependency exists.
- [ ] No SDUI parser logic exists.
- [ ] No renderer logic exists.

---

# 66. Best Practices

Always:

- Prefer semantic tokens.
- Keep token APIs small.
- Use Material 3 underneath.
- Keep models immutable.
- Keep resolvers deterministic.
- Provide safe defaults.
- Test token resolution.
- Preserve accessibility.
- Use existing stable libraries.

---

# 67. Common Pitfalls to Avoid

## Do not create a second UI framework

Material 3 is already the UI foundation.

The Design System should standardize it, not replace it.

---

## Do not over-tokenize

Bad:

```text
HomeBannerLeftPaddingToken
ServerButtonTopPaddingToken
```

Good:

```text
SpacingToken.Medium
SpacingToken.Large
```

Tokens should be reusable.

---

## Do not expose raw JSON

`core-designsystem` must not know:

```text
JsonObject
JsonElement
ComponentDto
```

Typed mapping happens elsewhere.

---

## Do not introduce circular dependencies

Never:

```text
core-designsystem → core-ui → core-designsystem
```

Correct:

```text
core-designsystem → Material3

core-ui → core-designsystem
```

---

## Do not use mutable theme singletons

Avoid global mutable theme state.

---

## Do not hardcode values in widgets

Widget-specific styling will resolve through this module later.

---

# 68. Future Extensibility

The architecture must allow future support for:

- Remote theme configuration
- Theme persistence
- Brand variants
- Additional semantic colors
- Tablet-specific scales
- Dynamic colors
- Runtime theme switching
- SDUI theme payloads
- Theme schema migration

Without rewriting feature screens.

These capabilities are **future-ready**, not required in Phase 04.

---

# 69. Phase Implementation Order

Implement Phase 04 in this order:

```text
1. Verify module dependencies

↓

2. Create token contracts

↓

3. Create light/dark color schemes

↓

4. Create typography

↓

5. Create shapes

↓

6. Create spacing/elevation/size scales

↓

7. Create theme defaults

↓

8. Create token resolvers

↓

9. Create AppTheme

↓

10. Create MaterialTheme extensions

↓

11. Create preview infrastructure

↓

12. Integrate required core-ui files

↓

13. Add tests

↓

14. Build + test
```

---

# 70. Files Expected to Be Created

The exact package/file set should be validated against the existing project before creation.

Expected files include:

```text
core-designsystem/
├── theme/
│   ├── AppTheme.kt
│   ├── AppThemeMode.kt
│   ├── AppColorScheme.kt
│   ├── AppTypography.kt
│   ├── AppShapes.kt
│   └── ThemeDefaults.kt
│
├── token/
│   ├── ColorToken.kt
│   ├── TypographyToken.kt
│   ├── SpacingToken.kt
│   ├── ShapeToken.kt
│   ├── ElevationToken.kt
│   ├── SizeToken.kt
│   ├── BorderToken.kt
│   └── IconToken.kt
│
├── resolver/
│   ├── ColorTokenResolver.kt
│   ├── TypographyTokenResolver.kt
│   ├── SpacingTokenResolver.kt
│   ├── ShapeTokenResolver.kt
│   ├── ElevationTokenResolver.kt
│   ├── SizeTokenResolver.kt
│   └── IconTokenResolver.kt
│
├── model/
│   ├── BorderStyle.kt
│   └── ResolvedTheme.kt
│
├── icon/
│   └── AppIcons.kt
│
├── extension/
│   └── MaterialThemeExtensions.kt
│
└── preview/
    └── AppThemePreview.kt
```

Do not blindly create a duplicate file if an equivalent already exists.

---

# 71. Files Potentially Modified

Only when required:

```text
core-designsystem/build.gradle.kts

core-ui/build.gradle.kts

app/build.gradle.kts

libs.versions.toml

existing app theme entry point

selected core-ui components
```

Do not regenerate complete Gradle files if a small dependency change is sufficient.

---

# 72. Files Explicitly Not Implemented

Do not create in Phase 04:

```text
StyleResolver.kt
ModifierFactory.kt
ComponentStyleMapper.kt
ThemeDto.kt
ThemeEntity.kt
ThemeDao.kt
ThemeRepository.kt
ServerThemeEditor.kt
RendererThemeAdapter.kt
```

These belong to later SDUI/data/renderer phases unless already required by an earlier authoritative contract.

---

# 73. Integration Checkpoint

Before Phase 04 is marked complete:

```text
App

↓

AppTheme

↓

core-ui component

↓

Design System token

↓

Material 3
```

must work successfully.

A simple reusable `core-ui` component should visually prove that the Design System is connected.

Do not implement feature screens merely for this verification.

---

# 74. Build Verification

Run the project's existing verification commands.

At minimum verify equivalent tasks for:

```text
assembleDebug

core-designsystem tests

core-ui tests
```

Also run existing lint/static-analysis tasks if Phase 01 configured them.

Do not invent new build tooling solely for this phase.

---

# 75. Definition of Done

Phase 04 is done only when:

```text
Build
  ✓

Tests
  ✓

Material 3 Theme
  ✓

Token System
  ✓

Light/Dark
  ✓

Safe Fallbacks
  ✓

core-ui Integration
  ✓

Architecture Boundaries
  ✓
```

No feature implementation should be required to complete this phase.

---

# 76. Advanced Production AI Implementation Prompt

## Role

Act as a **Senior Android Architect and Jetpack Compose Engineer** implementing Phase 04 of an existing production-quality SDUI assignment.

The project already contains previous phases.

You must preserve existing code and integrate incrementally.

---

## Mandatory First Step — Repository Inspection

Before changing anything:

1. Inspect the complete current project structure.
2. Inspect:
   - `settings.gradle.kts`
   - root `build.gradle.kts`
   - `gradle/libs.versions.toml`
   - `core-designsystem`
   - `core-ui`
   - `core-common`
   - `app`
3. Identify existing theme/token files.
4. Identify existing Material 3 dependencies.
5. Identify naming/package conventions.
6. Identify existing tests.
7. Detect duplicate or conflicting abstractions.

Do not generate code until this inspection is complete.

---

## Documentation Authority

Use these documents as architectural constraints:

```text
02_TECH_STACK.md
03_PROJECT_STRUCTURE.md
04_CLEAN_ARCHITECTURE.md
19_CODING_STANDARDS_AND_CONVENTIONS.md
24_DESIGN_SYSTEM_AND_THEME_ENGINE.md
26_COMPLETE_WIDGET_CATALOG.md
27_ASSIGNMENT_IMPLEMENTATION_MASTER_PROMPT.md
29_PHASE_01_PROJECT_BOOTSTRAP.md
30_PHASE_02_CORE_COMMON.md
31_PHASE_03_CORE_UI.md
32_PHASE_04_CORE_DESIGN_SYSTEM.md
```

If documentation conflicts with the actual repository:

1. Preserve working code where architecturally valid.
2. Make the smallest safe correction.
3. Report the conflict.
4. Do not silently rewrite unrelated modules.

---

## Objective

Implement a production-ready `core-designsystem` foundation that provides:

- Material 3 theme
- Light theme
- Dark theme
- Semantic design tokens
- Typography
- Spacing
- Shapes
- Elevation
- Sizes
- Borders
- Icons
- Token resolvers
- Safe defaults
- Preview support
- Tests

---

## Mandatory Architecture Rules

Maintain:

```text
core-common
     ↓
core-designsystem
     ↓
core-ui
     ↓
features
```

Never introduce:

```text
core-designsystem → feature-*
core-designsystem → core-renderer
core-designsystem → core-json
core-designsystem → Room
```

---

## Dependency Rules

Use only existing stable dependencies from:

```text
gradle/libs.versions.toml
```

If a dependency is genuinely missing:

1. Explain why it is required.
2. Add it to `libs.versions.toml`.
3. Reference the catalog alias from the module.
4. Use only a stable release.
5. Do not add redundant libraries.

Prefer no new dependency when Compose/Material 3 already provides the capability.

---

## Implementation Requirements

### Theme

Implement:

```text
AppTheme
AppThemeMode
Light Color Scheme
Dark Color Scheme
Typography
Shapes
Theme Defaults
```

Do not bind theme selection to ViewModel or persistence.

---

### Tokens

Implement typed semantic tokens for:

```text
Color
Typography
Spacing
Shape
Elevation
Size
Border
Icon
```

Tokens must be immutable and reusable.

---

### Resolvers

Implement focused resolvers.

Do not create a giant all-purpose resolver.

Resolvers must:

- Be deterministic.
- Be testable.
- Provide safe fallbacks.
- Avoid business logic.

---

### Compose Integration

Expose Design System values through MaterialTheme and minimal CompositionLocals where necessary.

Do not duplicate values MaterialTheme already provides.

---

### core-ui Integration

Inspect Phase 03 output.

Modify only required files so reusable components consume the Design System.

Do not rewrite the module.

Do not change public APIs unnecessarily.

---

### SDUI Readiness

Prepare typed Design System APIs for later:

```text
JSON
→ style mapping
→ token
→ resolver
→ Compose
```

Do not implement JSON parsing or renderer style resolution in this phase.

---

## Accessibility Requirements

Ensure:

- Material minimum touch targets are preserved.
- Typography supports font scaling.
- Light/dark schemes use appropriate foreground/background pairs.
- No public API encourages inaccessible hardcoded styling.

---

## Testing Requirements

Add focused tests for:

```text
Color tokens
Spacing tokens
Typography tokens
Shape tokens
Elevation tokens
Size tokens
Resolvers
Theme defaults
```

Add Compose tests only where they provide meaningful confidence.

Avoid brittle screenshot/golden testing unless already part of the project.

---

## Code Quality Rules

Follow:

- Kotlin coding conventions
- SOLID
- Small focused files
- Immutable data
- Meaningful naming
- KDoc for meaningful public contracts
- No wildcard imports
- No dead code
- No unnecessary TODOs
- No suppression without justification

---

## Existing File Protection

Critical rule:

> Never regenerate an existing file solely because a new phase touches the same module.

Before modifying any file:

1. Read it.
2. Determine the smallest required change.
3. Preserve existing behavior.
4. Preserve existing formatting conventions.
5. Update tests when behavior changes.

---

## Forbidden Implementations

Do not implement:

```text
Room theme persistence
Remote theme API
Theme ViewModel
SDUI JSON parser
Full StyleResolver
Widget renderer
Server Panel theme editor
Feature-specific styling
Performance benchmark module
```

---

## Required Verification

After implementation:

1. Sync/build project.
2. Compile `core-designsystem`.
3. Compile dependent `core-ui`.
4. Run unit tests.
5. Run relevant Compose tests.
6. Run existing static-analysis tasks.
7. Fix failures caused by Phase 04.
8. Do not modify unrelated modules merely to hide failures.

---

## Required Final Output

After implementation provide exactly these sections:

### Files Created

List every new file.

### Files Modified

List every modified existing file and why.

### Files Preserved

List important existing files/modules intentionally left unchanged.

### Dependencies

State:

- Added dependencies
- Reused dependencies
- Confirmation that versions remain centralized in `libs.versions.toml`

### Design System Implemented

Summarize:

- Colors
- Typography
- Spacing
- Shapes
- Elevation
- Sizes
- Borders
- Icons
- Theme modes
- Resolvers

### Tests Added

List every test file and what it validates.

### Verification

Report:

```text
Build:
Tests:
Lint:
Static Analysis:
```

Do not claim a command passed unless it was actually executed successfully.

### Deferred Work

Explicitly identify items intentionally deferred to later phases.

### Architecture Compliance

Confirm:

- No feature dependency
- No Room dependency
- No JSON dependency
- No renderer dependency
- No circular dependency
- Material 3 foundation preserved
- Design tokens centralized
- Existing modules preserved

---

# 77. Phase Dependency

```text
Phase 01
   │
   ▼
Phase 02 – core-common
   │
   ▼
Phase 03 – core-ui foundation
   │
   ▼
Phase 04 – core-designsystem
   │
   ▼
Phase 05 – JSON / SDUI Foundation
```

Phase 05 must consume the typed contracts established here rather than recreating another styling system.

---

# 78. Phase Completion Checklist

| Requirement | Status |
|---|---|
| Material 3 Foundation | ✅ Planned |
| Light Theme | ✅ Planned |
| Dark Theme | ✅ Planned |
| Semantic Colors | ✅ Planned |
| Typography Tokens | ✅ Planned |
| Spacing Tokens | ✅ Planned |
| Shape Tokens | ✅ Planned |
| Elevation Tokens | ✅ Planned |
| Size Tokens | ✅ Planned |
| Border Model | ✅ Planned |
| Icon Catalog | ✅ Planned |
| Token Resolvers | ✅ Planned |
| Theme Defaults | ✅ Planned |
| Accessibility Rules | ✅ Planned |
| SDUI Compatibility Boundary | ✅ Defined |
| `core-ui` Integration | ✅ Defined |
| Unit Testing | ✅ Planned |
| Existing File Protection | ✅ Mandatory |
| Stable Dependency Policy | ✅ Mandatory |
| Advanced AI Prompt | ✅ Complete |

---

# Next Document

**33_PHASE_05_CORE_JSON.md**

Phase 05 should implement the `core-json` foundation and establish the typed SDUI contract before Room or rendering is introduced.

It should cover:

```text
Kotlinx Serialization configuration
        ↓
SDUI DTO contracts
        ↓
Polymorphic component strategy
        ↓
Screen / Section / Component models
        ↓
Props and style contracts
        ↓
Action JSON contracts
        ↓
Parser
        ↓
Normalization
        ↓
Validation pipeline
        ↓
Version compatibility
        ↓
Parser/validator tests
```

A major requirement of Phase 05 should be ensuring that **every widget defined in `26_COMPLETE_WIDGET_CATALOG.md` can be represented safely by the JSON model without coupling `core-json` to Compose, Room, or the renderer implementation.**