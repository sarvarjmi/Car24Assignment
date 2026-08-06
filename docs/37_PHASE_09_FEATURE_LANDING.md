# 37_PHASE_09_FEATURE_LANDING.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 09 – Landing Feature
>
> **Module:** `feature-landing`
>
> **Architecture:** Clean Architecture + MVI + Jetpack Compose
>
> **Status:** Implementation Phase 09
>
> **Priority:** Critical
>
> **Estimated Time:** 4–6 Hours
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – core-common
> - ✅ Phase 03 – core-ui
> - ✅ Phase 04 – core-designsystem
> - ✅ Phase 08 – domain
> - 📖 11_NAVIGATION_DESIGN.md
> - 📖 19_HOME_SCREEN_DESIGN.md
> - 📖 20_SERVER_PANEL_DESIGN.md

---

# 1. Phase Objective

This is the **first user-visible feature** of the application.

Unlike the Home Screen, the Landing Screen is **100% native Compose UI**.

It is **not** rendered through SDUI.

Its responsibilities are very small:

- Show application branding
- Show assignment information
- Provide navigation entry points
- Navigate to Home
- Navigate to Local SDUI Server Panel

No business logic exists here.

---

# 2. Why Landing Screen Exists

The assignment specifically requires two entry buttons.

The Landing Screen provides a simple launcher for:

```text id="landing01"
+--------------------------------+

     SDUI Assignment

----------------------------------

   [ Open Home ]

   [ Open Local Server ]

+--------------------------------+
```

It intentionally remains native UI.

---

# 3. Responsibilities

The Landing Feature is responsible for

- Landing UI
- Landing MVI
- Navigation events
- Initial user entry
- Basic app information

It is **not responsible** for

- Rendering JSON
- Editing JSON
- Loading Room
- Parsing JSON
- Business rules

---

# 4. Architecture Position

```text id="landing02"
Landing Screen

↓

Landing ViewModel

↓

Navigation Event

↓

Navigation Graph

↓

Home

or

Server Panel
```

---

# 5. Module Structure

```text id="landing03"
feature-landing/

src/main/kotlin/

com.assignment.feature.landing/

├── ui/
│
├── component/
│
├── state/
│
├── intent/
│
├── event/
│
├── reducer/
│
├── viewmodel/
│
├── navigation/
│
├── preview/
│
└── di/
```

---

# 6. MVI Architecture

Landing follows the same MVI pattern used across all features.

```text id="landing04"
Intent

↓

ViewModel

↓

Reducer

↓

State

↓

Compose UI

↓

Event
```

No shortcuts.

---

# 7. Screen Layout

The screen should remain intentionally minimal.

Suggested layout:

```text id="landing05"
Logo / App Icon

↓

Assignment Title

↓

Short Description

↓

Open Home Button

↓

Open Local Server Button

↓

Version
```

No scrolling required.

---

# 8. LandingState

Contains only UI state.

Example

```text id="landing06"
loading

appVersion

buildType
```

No Home data.

No JSON.

---

# 9. LandingIntent

Supported intents

```text id="landing07"
OpenHome

OpenServer

Retry (future)
```

Very small intent set.

---

# 10. LandingEvent

Navigation events

```text id="landing08"
NavigateHome

NavigateServer
```

Events should be one-time.

---

# 11. Reducer

Reducer responsibilities

- Convert Intent → State
- Emit navigation events

No repository interaction.

---

# 12. ViewModel

LandingViewModel should

- Receive intents
- Update state
- Emit navigation events

It must not

- Access repositories
- Load JSON
- Access Room

---

# 13. Navigation

Supported destinations

```text id="landing09"
Landing

↓

Home

Landing

↓

Server Panel
```

Back navigation

```text id="landing10"
Home

↓

Landing

Server

↓

Landing
```

---

# 14. UI Components

Use reusable components from

```text id="landing11"
core-ui
```

Examples

- AppScaffold
- PrimaryButton
- AppTopBar

Do not create duplicate UI components.

---

# 15. Theme

Use

```text id="landing12"
core-designsystem
```

Never hardcode

- Colors
- Typography
- Shapes

---

# 16. Strings

Keep strings centralized.

Examples

```text id="landing13"
Open Home

Open Local Server

Assignment

Version
```

---

# 17. Icons

Use Design System icon tokens.

Suggested icons

```text id="landing14"
Home

Server

ArrowForward
```

---

# 18. Animations

Simple animations only.

Allowed

- Fade
- Scale
- AnimatedVisibility

Avoid complex transitions.

---

# 19. Accessibility

Support

- TalkBack
- ContentDescription
- Minimum touch target
- Dynamic font scaling

---

# 20. State Restoration

The screen should restore naturally after configuration changes.

No custom persistence required.

---

# 21. Error Handling

Very limited.

Possible errors

- Navigation unavailable (unlikely)

No business error UI.

---

# 22. Loading State

Loading state is optional.

The Landing Screen should appear immediately.

---

# 23. Dependency Rules

Allowed

- core-ui
- core-designsystem
- domain
- navigation

Not Allowed

- Room
- JSON parser
- Renderer
- Data layer
- DAO

---

# 24. Testing Strategy

Compose UI Tests

Verify

- Buttons displayed
- Button clicks
- Navigation events
- Accessibility labels

ViewModel Tests

Verify

- Intent handling
- Event emission

---

# 25. Preview Strategy

Generate previews for

- Default
- Dark Theme
- Large Font
- Landscape (optional)

---

# 26. Best Practices

Always

- Stateless Composables
- MVI
- One-way data flow
- Reusable UI
- Design System

Never

- Access Room
- Parse JSON
- Use repositories
- Duplicate buttons

---

# 27. Acceptance Criteria

Landing feature is complete when

- Screen implemented
- Two buttons visible
- Navigation works
- MVI complete
- Tests passing
- Accessibility verified

---

# 28. Common Pitfalls

Avoid

- Business logic
- Repository access
- Renderer calls
- JSON loading
- Mutable UI state

---

# 29. Definition of Done

- Landing screen compiles
- Navigation verified
- Buttons reusable
- MVI complete
- Tests pass

---

# 30. User Flow

```text id="landing15"
App Launch

↓

Landing

↓

Open Home

↓

SDUI Home

OR

Landing

↓

Open Local Server

↓

Server Panel
```

---

# 31. Production AI Prompt

## Objective

Implement the complete **feature-landing** module.

### Mandatory First Step

Inspect

- Navigation graph
- Existing feature modules
- core-ui components
- Design System
- MVI architecture

Never regenerate existing files.

---

### Create Packages

- ui
- component
- state
- intent
- event
- reducer
- viewmodel
- navigation
- preview
- di

---

### Generate

#### UI

- LandingScreen

#### ViewModel

- LandingViewModel

#### State

- LandingState

#### Intent

- LandingIntent

#### Event

- LandingEvent

#### Reducer

- LandingReducer

#### Navigation

- LandingDestination

---

### Requirements

- Compose only
- MVI
- Stateless UI
- Two navigation buttons
- Use core-ui
- Use Design System
- No business logic
- No Room
- No JSON
- No Renderer

---

### Tests

Generate

- Compose UI tests
- ViewModel tests
- Navigation tests
- Accessibility tests

---

### Output Summary

Provide

- Files created
- Files modified
- Navigation graph changes
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 32. Future Extensibility

The Landing screen should be designed so additional entry points can be added later without architectural changes.

Possible future buttons

```text id="landing16"
Settings

Theme Preview

Widget Catalog

Diagnostics

JSON Import

Developer Options
```

Current assignment requires only

- Home
- Local Server

---

# 33. Integration Checklist

Verify

```text id="landing17"
App

↓

Landing

↓

Home

↓

Back

↓

Landing
```

And

```text id="landing18"
Landing

↓

Server Panel

↓

Back

↓

Landing
```

Navigation should preserve expected Android back-stack behavior.

---

# 34. UI Specifications

The Landing screen should follow these visual guidelines:

### Header

- App Logo (optional placeholder)
- Assignment Title
- Short description

### Primary Action

- **Open Home**
- Uses `PrimaryButton`

### Secondary Action

- **Open Local Server**
- Uses `SecondaryButton`

### Footer

- App Version
- Build Type (Debug/Release)
- Copyright (optional)

---

# 35. Integration with Assignment Requirements

This screen satisfies the assignment requirement of providing **two native entry points**:

| Button | Destination | Purpose |
|---------|-------------|---------|
| Open Home | SDUI Home | Render the Home screen using JSON stored in Room |
| Open Local Server | Server Panel | Edit the JSON stored in Room, preview it, save it, and automatically update the Home screen |

The Landing screen itself **must never render SDUI**.

---

# 36. Phase Dependency

```text id="landing19"
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
        │
        ▼
Phase 09 – feature-landing
        │
        ▼
Phase 10 – feature-home
```

---

# 37. Phase Completion Checklist

| Item | Status |
|------|--------|
| Landing Screen | ✅ Planned |
| Landing MVI | ✅ Planned |
| Two Navigation Buttons | ✅ Planned |
| Navigation Integration | ✅ Planned |
| Design System Usage | ✅ Planned |
| Accessibility | ✅ Planned |
| UI Tests | ✅ Planned |
| ViewModel Tests | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# Next Document

**38_PHASE_10_FEATURE_HOME.md**

This will be the **largest implementation phase** of the project and will include:

- SDUI Home Feature
- Home MVI architecture
- Home ViewModel
- Home Repository integration
- Flow collection from Room
- RendererHost
- Dynamic screen rendering
- Loading/Error/Empty states
- Pull-to-refresh (local refresh)
- SDUI lifecycle handling
- Renderer integration
- State restoration
- Comprehensive UI and ViewModel tests
- Production-grade AI implementation prompt

This phase is where the application will **render the complete Home screen dynamically from the JSON stored in Room**, making the Local SDUI Server fully functional.