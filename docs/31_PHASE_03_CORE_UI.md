# 31_PHASE_03_CORE_UI.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 03 – Core UI Foundation
>
> **Module:** `core-ui`
>
> **Architecture:** Clean Architecture + Material 3 + Jetpack Compose
>
> **Status:** Implementation Phase 03
>
> **Estimated Time:** 5–8 Hours
>
> **Priority:** Critical
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – core-common

---

# Phase Objective

This phase builds the **UI foundation** of the application.

Unlike `feature-home` or `feature-server`, this module **does not contain business screens**.

Instead, it provides reusable UI building blocks that every feature module can use.

The module should contain only generic, reusable Compose components.

---

# Module Responsibilities

The `core-ui` module provides:

- Base Screen Layouts
- Scaffold
- Top App Bar
- Loading Components
- Error Components
- Empty Components
- Snackbar Host
- Dialog Components
- Generic Buttons
- Progress Indicators
- UI Utilities
- Animation Helpers
- Compose Extensions
- Preview Infrastructure

It must **never contain feature-specific UI**.

---

# Architecture Position

```text
feature-home
feature-server
feature-landing

↓

core-ui

↓

Material3
```

---

# Module Structure

```text
core-ui/

src/main/kotlin/

com.assignment.core.ui/

├── scaffold/
│
├── appbar/
│
├── button/
│
├── card/
│
├── dialog/
│
├── snackbar/
│
├── loading/
│
├── error/
│
├── empty/
│
├── progress/
│
├── animation/
│
├── modifier/
│
├── preview/
│
├── state/
│
├── util/
│
└── extension/
```

---

# Package Responsibilities

## scaffold/

Contains

- AppScaffold
- BaseScaffold

---

## appbar/

Contains

- AppTopBar
- SimpleTopBar
- CenterAlignedTopBar

---

## button/

Contains reusable buttons.

Examples

- PrimaryButton
- SecondaryButton
- TextButton
- IconButton

No business actions.

---

## card/

Contains reusable cards.

Examples

- ElevatedCard
- SurfaceCard
- OutlinedCard

---

## dialog/

Contains

- ConfirmationDialog
- ErrorDialog
- InfoDialog
- LoadingDialog

---

## snackbar/

Contains

- AppSnackbarHost
- SnackbarController
- SnackbarState

---

## loading/

Contains

- FullScreenLoading
- CenterLoading
- InlineLoading
- LoadingOverlay
- SkeletonPlaceholder (future-ready)

---

## error/

Contains

- ErrorView
- RetryView
- ErrorCard

---

## empty/

Contains

- EmptyState
- EmptyList
- EmptyContent

---

## progress/

Contains

- LinearProgress
- CircularProgress
- StepProgress (future-ready)

---

## animation/

Contains reusable animation helpers.

Examples

- Fade
- Scale
- Visibility
- Crossfade

No feature animations.

---

## modifier/

Contains reusable modifiers.

Examples

- SafeClickable
- DefaultPadding
- NoRippleClickable
- FillMaxWidthIf

---

## preview/

Contains

- PreviewTheme
- PreviewData
- PreviewParameterProvider

---

## extension/

Contains Compose extensions.

---

# UI Design Principles

Every component must be

- Stateless
- Reusable
- Previewable
- Testable
- Theme-aware

Never depend on

- Repository
- ViewModel
- Navigation
- Room

---

# Scaffold Design

Create

## AppScaffold

Supports

- TopBar
- BottomBar (future)
- Snackbar
- FAB (future)
- Content

Every feature screen should use AppScaffold.

---

# Top App Bar

Support

- Title
- Subtitle (optional)
- Navigation icon
- Action icons

Material 3 only.

---

# Button System

Create reusable buttons.

## PrimaryButton

Filled button.

---

## SecondaryButton

Outlined button.

---

## TextButton

Text action.

---

## IconButton

Icon only.

---

## LoadingButton (future-ready)

Reserved for async actions.

---

# Card System

Support

- Elevated
- Filled
- Outlined

Cards must respect Design Tokens.

---

# Dialog System

Support

## Confirmation

Buttons

- Confirm
- Cancel

---

## Error

Single dismiss action.

---

## Information

Acknowledgement only.

---

## Loading

Non-dismissible.

---

# Snackbar System

Provide centralized snackbar support.

Flow

```text
UiEvent

↓

SnackbarController

↓

SnackbarHost

↓

Compose
```

No direct Snackbar usage in features.

---

# Loading Components

Required

## FullScreenLoading

Used during initial screen load.

---

## CenterLoading

Small loading indicator.

---

## InlineLoading

Inside cards or rows.

---

## OverlayLoading

Blocks interaction.

---

# Error Components

Support

- Error title
- Description
- Retry action
- Icon

Reusable across all features.

---

# Empty Components

Support

- Illustration (optional)
- Title
- Description
- CTA button

Used by Home and Server Panel.

---

# Progress Components

Provide

- Circular
- Linear

No business logic.

---

# Animation Helpers

Support

- AnimatedVisibility
- Crossfade
- FadeIn
- FadeOut

Avoid custom animation engines.

---

# Modifier Library

Reusable modifiers only.

Examples

- defaultPadding()
- safeClickable()
- noRippleClickable()
- conditionalModifier()

---

# Compose Extensions

Allowed

- Modifier extensions
- Padding helpers
- Alignment helpers

Avoid feature-specific extensions.

---

# Preview Infrastructure

Every reusable component must include Preview.

Provide

- Light
- Dark (future-ready)
- Empty
- Error
- Loading

---

# Material 3 Rules

Always use

- MaterialTheme
- Material Components
- Material Typography
- Material Shapes

Never hardcode

- Colors
- Typography
- Elevation

---

# Accessibility

Every component should support

- ContentDescription
- Focus order
- Minimum touch target
- Dynamic font scaling
- Material semantics

---

# Dependency Rules

Allowed

- Compose
- Material 3
- core-common

Not Allowed

- Room
- Navigation
- ViewModel
- Hilt
- Repository
- Feature modules

---

# Testing Strategy

Unit Tests

- UI helper logic
- Modifier extensions

Compose UI Tests

- Buttons
- Dialogs
- Snackbar
- Loading
- Error
- Empty State
- AppBar

Preview verification for all reusable components.

---

# Best Practices

Always

- Stateless Composables
- Parameter-driven UI
- Material 3
- Immutable models
- Small reusable components

Never

- Access Repository
- Launch coroutines
- Hold business state
- Use feature-specific strings
- Hardcode colors

---

# Acceptance Criteria

Phase 03 is complete when

- AppScaffold implemented
- TopBar implemented
- Button library complete
- Dialog system complete
- Snackbar system complete
- Loading components complete
- Error components complete
- Empty components complete
- Progress components complete
- Modifier library complete
- Preview support complete
- Tests passing

---

# Common Pitfalls

Avoid

- Feature-specific UI
- ViewModel references
- Navigation calls
- Business logic
- Room usage
- Mutable state inside reusable components
- Missing previews
- Hardcoded styling

---

# Definition of Done

- Module builds successfully
- All components reusable
- Material 3 compliant
- Previewable
- Theme-aware
- Tested
- Architecture boundaries maintained

---

# Production AI Prompt

## Objective

Implement the complete **core-ui** module.

### Create Packages

- scaffold
- appbar
- button
- card
- dialog
- snackbar
- loading
- error
- empty
- progress
- animation
- modifier
- preview
- extension
- util

### Generate

#### Scaffold

- AppScaffold
- BaseScaffold

#### App Bars

- AppTopBar
- CenterAlignedTopBar
- SimpleTopBar

#### Buttons

- PrimaryButton
- SecondaryButton
- TextButton
- AppIconButton

#### Cards

- ElevatedCard
- OutlinedCard
- FilledCard

#### Dialogs

- ConfirmationDialog
- ErrorDialog
- InfoDialog
- LoadingDialog

#### Snackbar

- AppSnackbarHost
- SnackbarController

#### Loading

- FullScreenLoading
- CenterLoading
- InlineLoading
- LoadingOverlay

#### Error

- ErrorView
- RetryView

#### Empty

- EmptyState
- EmptyContent

#### Progress

- AppCircularProgress
- AppLinearProgress

#### Modifiers

- SafeClickable
- NoRippleClickable
- DefaultPadding

#### Preview

Generate previews for every reusable component.

### Requirements

- Material 3 only
- Stateless Composables
- Immutable parameters
- Preview support
- Accessibility support
- Theme-aware
- No feature-specific code
- No business logic
- Compatible with Design System
- Compatible with Home and Server features

### Tests

Generate

- Compose UI tests
- Modifier tests
- Dialog tests
- Button tests
- Loading state tests
- Error state tests
- Empty state tests

### Output Summary

Provide

- Files created
- Files modified
- Component catalog
- Preview coverage
- Test coverage
- Architecture compliance checklist

---

# Phase Dependency

```text
Phase 01
    │
    ▼
Phase 02 (core-common)
    │
    ▼
Phase 03 (core-ui)
    │
    ▼
Phase 04 (core-designsystem)
```

The `core-ui` module must be fully implemented and stable before introducing the Design System, SDUI Renderer, or feature modules.

---

# Phase Completion Checklist

| Item | Status |
|------|--------|
| Scaffold System | ✅ Planned |
| App Bars | ✅ Planned |
| Button Library | ✅ Planned |
| Card Library | ✅ Planned |
| Dialog System | ✅ Planned |
| Snackbar System | ✅ Planned |
| Loading Components | ✅ Planned |
| Error Components | ✅ Planned |
| Empty Components | ✅ Planned |
| Progress Components | ✅ Planned |
| Modifier Library | ✅ Planned |
| Preview Infrastructure | ✅ Planned |
| UI Testing Strategy | ✅ Planned |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**32_PHASE_04_CORE_DESIGN_SYSTEM.md**

This phase will implement the `core-designsystem` module, including:

- Material 3 theme foundation
- Design tokens
- Color system
- Typography system
- Shape system
- Spacing tokens
- Elevation tokens
- Theme resolver
- SDUI style mapping
- Preview theme infrastructure
- Production AI prompt for implementing the complete Design System module.