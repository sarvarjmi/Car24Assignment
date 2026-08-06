# 20_SERVER_PANEL_DESIGN.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Room Local SDUI Server
>
> **Rendering:** Dynamic JSON Editing & Live Preview
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - 00–19 Documentation
> - JSON Schema Design
> - Room Database Design
> - Home Screen Design
> - State Management
> - Error Handling

---

# Document Objective

This document defines the **Local Server Panel**, which simulates a backend CMS/Admin Panel inside the application.

Unlike a normal settings screen, this module acts as a **Local SDUI Server**, allowing developers/interviewers to:

- Edit JSON
- Validate JSON
- Save JSON into Room
- Preview generated JSON
- Instantly update Home Screen
- Simulate backend changes

This is the most important module after the SDUI Renderer.

---

# 1. Server Panel Philosophy

The Server Panel behaves like a lightweight backend CMS.

Instead of

```text id="server01"
Backend

↓

REST API

↓

App
```

The assignment uses

```text id="server02"
Server Panel

↓

Repository

↓

Room

↓

Renderer
```

The UI becomes editable without changing Kotlin code.

---

# 2. Responsibilities

The Server Panel is responsible for

- Selecting Screen
- Selecting Section
- Selecting Component
- Editing Component JSON
- Validating JSON
- Saving JSON
- Previewing JSON
- Triggering live updates

It never renders Home directly.

---

# 3. Module Structure

```text id="server03"
feature-server/

presentation/

├── screen/

├── component/

├── editor/

├── selector/

├── preview/

├── state/

├── intent/

├── reducer/

├── event/

├── viewmodel/

└── navigation/
```

---

# 4. Screen Layout

```text id="server04"
Scaffold

↓

Top App Bar

↓

Screen Selector

↓

Section Selector

↓

Component Selector

↓

JSON Editor

↓

Validation Result

↓

Action Buttons

↓

JSON Preview
```

---

# 5. User Flow

```text id="server05"
Landing

↓

Server Panel

↓

Select Screen

↓

Select Section

↓

Select Component

↓

Edit JSON

↓

Validate

↓

Save

↓

Room Updated

↓

Home Updated
```

---

# 6. Initial Screen

When opened

Display

- Current Screen
- Current Section
- Current Component
- Current JSON

Automatically load existing data from Room.

---

# 7. Screen Selector

Purpose

Select SDUI Screen.

Current assignment

```text id="server06"
Home
```

Future

```text id="server07"
Search

Profile

Details

Checkout
```

---

# 8. Section Selector

Displays all sections belonging to selected screen.

Example

```text id="server08"
Header

Search

Banner

Categories

Featured

CTA

Footer
```

Dynamic from Room.

---

# 9. Component Selector

Displays components within selected section.

Example

```text id="server09"
Banner

Search

ChipGroup

CarCard

Button
```

---

# 10. JSON Editor

Editor allows

- Multi-line editing
- Pretty formatting
- Cursor preservation
- Syntax highlighting (future-ready)
- Scroll support

Editor always edits the **selected component JSON**, not the entire screen.

---

# 11. Validation Engine

Every edit can be validated.

Validation stages

```text id="server10"
Syntax

↓

Schema

↓

Business Rules

↓

Renderer Rules
```

Validation occurs before Save.

---

# 12. Validation Feedback

Show

- Success
- Warning
- Error

Examples

```text id="server11"
✓ JSON Valid

✗ Missing title

✗ Invalid action

✗ Duplicate id
```

---

# 13. Save Workflow

```text id="server12"
Edit

↓

Validate

↓

Repository

↓

Transaction

↓

Room

↓

Flow

↓

Home Updated
```

Save button disabled when JSON is invalid.

---

# 14. Reset Workflow

Reset restores the last persisted JSON.

```text id="server13"
Edit

↓

Reset

↓

Reload From Room
```

Unsaved edits are discarded.

---

# 15. JSON Preview

Displays formatted JSON.

Purpose

- Verify output
- Debug payload
- Compare changes

Preview is read-only.

---

# 16. Live Update

After successful save

```text id="server14"
Room

↓

Flow

↓

HomeViewModel

↓

Renderer

↓

Updated Home
```

No restart.

---

# 17. State Management

ServerUiState contains

```text id="server15"
Current Screen

Current Section

Current Component

Editable JSON

Validation Result

Saving

Success

Failure
```

Immutable.

---

# 18. Intents

Supported

```text id="server16"
Load

SelectScreen

SelectSection

SelectComponent

UpdateJson

Validate

Save

Reset

CopyJson (Future)

ExportJson (Future)
```

---

# 19. Events

One-time events

```text id="server17"
Snackbar

Dialog

NavigateBack
```

---

# 20. Reducer

Reducer manages

- Loading
- Validation
- Saving
- Success
- Error

Pure and deterministic.

---

# 21. JSON Validation Rules

Validate

- Syntax
- Schema version
- Required fields
- Duplicate IDs
- Unknown widget
- Unknown action
- Invalid style
- Invalid layout

Save blocked on failure.

---

# 22. Editing Rules

Editable

- Text
- Numbers
- Boolean
- Arrays
- Objects

Not editable

- Database IDs
- Internal timestamps
- Seed metadata

---

# 23. Undo Strategy

Current assignment

```text id="server18"
Reset

↓

Reload Room
```

Future

```text id="server19"
Undo Stack
```

---

# 24. Export Strategy

Future-ready

Export

```text id="server20"
Component JSON

Section JSON

Screen JSON
```

Useful for backend integration.

---

# 25. Import Strategy

Future-ready

```text id="server21"
Paste JSON

↓

Validate

↓

Preview

↓

Save
```

No overwrite without validation.

---

# 26. Error Handling

Possible errors

- Invalid JSON
- Invalid schema
- Validation failure
- Transaction failure
- Unknown widget
- Unknown action

Recovery

↓

Keep previous valid state.

---

# 27. Security Rules

Reject

- Kotlin code
- SQL
- JavaScript
- Reflection metadata
- Unsupported fields

Only JSON configuration is accepted.

---

# 28. Performance Guidelines

The Server Panel should naturally:

- Avoid reparsing unchanged JSON
- Validate incrementally where feasible
- Update only the edited component
- Persist only changed rows
- Avoid unnecessary recompositions

---

# 29. Accessibility

Support

- Screen reader labels
- Accessible dropdowns
- Large touch targets
- Keyboard-friendly JSON editor
- Material 3 semantics

---

# 30. Testing Strategy

Unit Tests

- Validation
- Reducer
- ViewModel

Integration Tests

- Save workflow
- Repository updates
- Live updates

UI Tests

- Screen selector
- Section selector
- Component selector
- JSON editor
- Save
- Reset
- Validation messages
- Preview

---

# 31. Assignment Workflow

```text id="server22"
Landing

↓

Server Panel

↓

Select Banner

↓

Edit Title

↓

Validate

↓

Save

↓

Room Updated

↓

Home Updated Automatically
```

---

# 32. Acceptance Criteria

The Server Panel is complete when:

- Screen selector works
- Section selector works
- Component selector works
- JSON editor works
- Validation works
- Save works
- Reset works
- Preview works
- Home updates automatically
- Clean Architecture is preserved

---

# 33. Production AI Prompt

## Objective

Generate the complete `feature-server` module.

### Generate

#### Screens

- ServerScreen
- ServerContent

#### Components

- ScreenSelector
- SectionSelector
- ComponentSelector
- JsonEditor
- ValidationCard
- JsonPreview
- ActionBar

#### ViewModel

- ServerViewModel

#### MVI

- ServerUiState
- ServerIntent
- ServerEvent
- ServerReducer

#### Business Flow

- Load selected component
- Edit JSON
- Validate JSON
- Save through Repository
- Observe Room updates
- Reset to persisted state

### Requirements

- Material 3
- StateFlow
- Immutable state
- Clean Architecture
- MVI
- Room as Local SDUI Server
- Validation before save
- Transaction-safe updates
- No DAO access from Presentation
- No JSON parsing inside Composables
- Live update support
- Compatible with generic SDUI platform

### Tests

Generate

- ViewModel tests
- Reducer tests
- JSON validation tests
- Save workflow tests
- Reset workflow tests
- Compose UI tests
- Live update integration tests

### Output Summary

Provide

- Files created
- Files modified
- Deferred files
- Server Panel workflow diagram
- Validation pipeline
- Architecture compliance checklist

---

# Server Panel Documentation Status

| Section | Status |
|----------|--------|
| Server Architecture | ✅ Complete |
| JSON Editor | ✅ Complete |
| Validation Pipeline | ✅ Complete |
| Save & Reset Workflow | ✅ Complete |
| Live Update Flow | ✅ Complete |
| MVI Contracts | ✅ Complete |
| Error Handling | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**21_SDUI_RENDERER_ENGINE.md**

This document will define the heart of the assignment:

- Generic Renderer Engine
- Component Registry
- Widget Factory
- Renderer Pipeline
- Container rendering
- Nested component rendering
- Dynamic style resolver
- Action Engine integration
- Unknown widget fallback
- Generic widget registration
- Renderer performance strategy
- Production AI prompt for implementing the complete SDUI rendering engine