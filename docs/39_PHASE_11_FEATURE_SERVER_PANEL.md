# 39_PHASE_11_FEATURE_SERVER_PANEL.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 11 – Local SDUI Server Panel
>
> **Module:** `feature-server`
>
> **Architecture:** Clean Architecture + MVI + Offline First + Local SDUI Server
>
> **Status:** Implementation Phase 11
>
> **Priority:** ⭐ Highest
>
> **Estimated Time:** 18–24 Hours
>
> **Dependencies**
>
> - ✅ Phase 01 – Project Bootstrap
> - ✅ Phase 02 – core-common
> - ✅ Phase 03 – core-ui
> - ✅ Phase 04 – core-designsystem
> - ✅ Phase 05 – core-json
> - ✅ Phase 06 – core-database
> - ✅ Phase 07 – data
> - ✅ Phase 08 – domain
> - ✅ Phase 10 – feature-home
> - 📖 20_SERVER_PANEL_DESIGN.md
> - 📖 21_SDUI_RENDERER_ENGINE.md
> - 📖 23_JSON_VALIDATION_AND_PARSER_ENGINE.md
> - 📖 26_COMPLETE_WIDGET_CATALOG.md

---

# 1. Phase Objective

This feature implements the **Local SDUI Server**.

Instead of connecting to a real backend, the application provides a native Compose administration panel that behaves like a lightweight CMS.

The Server Panel must allow users to:

- View current Home JSON
- Edit widgets using structured controls
- Save JSON into Room
- Validate before saving
- Preview formatted JSON
- Immediately update the Home screen through Flow

This feature is the second major requirement of the assignment.

---

# 2. Assignment Goal

The assignment requires the application to simulate a backend.

Architecture:

```text id="server01"
Server Panel

↓

Repository

↓

Room

↓

Flow

↓

Home Screen

↓

Renderer

↓

Updated UI
```

No application restart.

No manual refresh.

---

# 3. Responsibilities

The Server Panel is responsible for:

- Loading stored JSON
- Structured JSON editing
- Validation
- Pretty JSON preview
- Save
- Reset
- Live update

It is **not responsible** for:

- Parsing implementation
- Widget rendering
- Room implementation
- JSON schema implementation
- Renderer implementation

---

# 4. Architecture Position

```text id="server02"
Server Screen

↓

ServerViewModel

↓

UseCases

↓

Repository

↓

Room

↓

Flow

↓

Home Screen
```

---

# 5. Module Structure

```text id="server03"
feature-server/

src/main/kotlin/

com.assignment.feature.server/

├── ui/
│
├── component/
│
├── editor/
│
├── selector/
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
├── preview/
│
├── navigation/
│
└── di/
```

---

# 6. MVI Flow

```text id="server04"
Intent

↓

ViewModel

↓

UseCases

↓

Reducer

↓

State

↓

Compose UI
```

---

# 7. Screen Layout

The screen is divided into four logical sections.

```text id="server05"
Top App Bar

↓

Screen Selector

↓

Component Editor

↓

JSON Preview

↓

Bottom Action Bar
```

---

# 8. Screen Selector

Current assignment supports one screen.

Dropdown

```text id="server06"
Home
```

Architecture must allow future screens.

---

# 9. Section Selector

Display available sections.

Dropdown

Example

```text id="server07"
Header

Hero

Categories

Cars

CTA

Footer
```

Loaded dynamically from JSON.

---

# 10. Component Selector

Displays all components in selected section.

Example

```text id="server08"
HeroBanner

Search

ChipGroup

CarCard

Button

Footer
```

---

# 11. Widget Editor Philosophy

**Important improvement**

The assignment should **not** expose a raw JSON text editor as the primary editing experience.

Instead use a **structured form editor**.

Flow

```text id="server09"
Component

↓

Typed Form

↓

Update Model

↓

Generate JSON

↓

Validate

↓

Save
```

Raw JSON remains available for preview only.

---

# 12. Widget Editor

Editor fields depend on widget type.

Examples

## Text

```text id="server10"
Text

Typography

Color

Visibility
```

---

## Button

```text id="server11"
Label

Action

Enabled

Background

Text Color
```

---

## Image

```text id="server12"
URL

ContentScale

Shape
```

---

## Hero Banner

```text id="server13"
Title

Subtitle

Image

Button

Background
```

The editor should be extensible through widget metadata.

---

# 13. Fixed Dropdown Strategy

Where possible use predefined dropdowns instead of free text.

Examples

Typography

```text id="server14"
DisplayLarge

HeadlineMedium

TitleMedium

BodyMedium
```

Visibility

```text id="server15"
Visible

Hidden
```

Alignment

```text id="server16"
Start

Center

End
```

Button Style

```text id="server17"
Filled

Outlined

Text
```

This prevents invalid JSON.

---

# 14. Widget Metadata

Each supported widget should expose editable metadata.

Example

```text id="server18"
Editable Properties

↓

Widget Metadata

↓

Dynamic Form
```

This allows future widgets without rewriting the editor.

---

# 15. JSON Preview

Display

Pretty formatted JSON.

Read-only.

Syntax highlighting is optional.

The preview always reflects the current in-memory editor state.

---

# 16. Validation

Validation runs before save.

Pipeline

```text id="server19"
Editor

↓

Generate JSON

↓

Parser

↓

Normalizer

↓

Validator

↓

Valid?
```

If invalid

↓

Display validation errors.

Do not update Room.

---

# 17. Save Flow

```text id="server20"
Save

↓

Validate

↓

Repository

↓

Room

↓

Flow

↓

Home Updated
```

---

# 18. Reset Flow

Reset restores last persisted JSON.

```text id="server21"
Room

↓

Load

↓

Editor

↓

Discard Changes
```

---

# 19. Unsaved Changes

Maintain

```text id="server22"
hasUnsavedChanges
```

Warn before leaving.

---

# 20. ServerState

Contains

```text id="server23"
loading

selectedScreen

selectedSection

selectedComponent

editorState

jsonPreview

validationErrors

hasUnsavedChanges
```

Immutable.

---

# 21. ServerIntent

Support

```text id="server24"
Load

SelectScreen

SelectSection

SelectComponent

UpdateField

Save

Reset

Refresh

DismissError
```

---

# 22. ServerEvent

Examples

```text id="server25"
ShowSnackbar

NavigateBack

ShowValidationDialog

ShowDiscardDialog
```

---

# 23. ViewModel

Responsibilities

- Load current JSON
- Maintain editor state
- Validate
- Save
- Reset
- Emit events

Never manipulate Room directly.

---

# 24. Repository Integration

UseCases only.

```text id="server26"
LoadJsonUseCase

↓

SaveJsonUseCase

↓

ValidateJsonUseCase
```

---

# 25. Form Generation

The editor should be generated dynamically from widget metadata.

```text id="server27"
Widget Type

↓

Metadata

↓

Form Definition

↓

Compose Controls
```

No giant when-block for every screen.

---

# 26. Live Preview Strategy

Every field update immediately regenerates

```text id="server28"
Current JSON
```

without saving.

---

# 27. Save Strategy

Room is updated only after:

- Validation success
- User presses Save

Typing should never update the database.

---

# 28. Error Handling

Display friendly errors for

- Invalid property
- Invalid action
- Invalid style
- Unsupported widget
- Validation failure
- Save failure

---

# 29. Undo Readiness

Current assignment

↓

Reset only.

Architecture should later support

```text id="server29"
Undo

Redo

History
```

without redesign.

---

# 30. Dependency Rules

Allowed

- domain
- core-ui
- core-designsystem

Not Allowed

- DAO
- Entity
- Parser implementation
- Renderer implementation

---

# 31. Accessibility

Every editor control should support

- Labels
- TalkBack
- Error announcements
- Large font support

---

# 32. Performance

Avoid:

- Saving on every keystroke
- Parsing every recomposition
- Full-screen recomposition for one field change

Editor state should update granularly.

---

# 33. Testing Strategy

## ViewModel Tests

Verify

- Load
- UpdateField
- Validation
- Save
- Reset
- Error

## Compose Tests

Verify

- Dropdown selection
- Field editing
- Save button
- Reset button
- Validation messages
- JSON preview

## Integration Tests

Verify

```text id="server30"
Editor

↓

Repository

↓

Room

↓

Flow

↓

Home Updated
```

---

# 34. Best Practices

Always

- Structured editor
- Immutable state
- MVI
- Validate before save
- Repository abstraction

Never

- Direct DAO access
- Raw SQL
- JSON editing as the primary UX
- Business logic inside Composables

---

# 35. Acceptance Criteria

Phase 11 is complete when

- Server screen implemented
- Dynamic selectors work
- Widget editor works
- Validation works
- Pretty JSON preview works
- Save updates Room
- Reset restores persisted state
- Home updates automatically
- Tests passing

---

# 36. Common Pitfalls

Avoid

- Editing raw JSON directly
- Bypassing validation
- Updating Room during typing
- Coupling editor to specific widgets
- Renderer dependencies

---

# 37. Definition of Done

- Local Server operational
- JSON saved safely
- Home auto-refresh verified
- Validation enforced
- Structured editing implemented
- Tests pass

---

# 38. Production AI Prompt

## Objective

Implement the complete **feature-server** module.

### Mandatory First Step

Inspect

- Existing Home feature
- Domain UseCases
- Repository contracts
- Widget catalog
- Parser
- Validation pipeline

Never regenerate completed files.

---

### Create Packages

- ui
- component
- editor
- selector
- state
- intent
- event
- reducer
- viewmodel
- preview
- navigation
- di

---

### Generate

#### UI

- ServerScreen
- ServerContent

#### ViewModel

- ServerViewModel

#### MVI

- ServerState
- ServerIntent
- ServerEvent
- ServerReducer

#### Components

- ScreenSelector
- SectionSelector
- ComponentSelector
- WidgetEditor
- JsonPreview
- ValidationPanel
- ActionBar

---

### Requirements

- Compose only
- MVI
- Structured form editor
- Read-only JSON preview
- Validation before save
- Repository-only access
- Live Home updates
- Accessibility support
- Material 3
- Design System compliant

---

### Tests

Generate

- ViewModel tests
- Compose tests
- Repository integration tests
- Save/Reset tests
- Validation tests
- Flow update tests

---

### Output Summary

Provide

- Files created
- Files modified
- Widget editor architecture
- State flow
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 39. Assignment Validation Checklist

This phase satisfies the assignment only if:

- [ ] Server Panel is native Compose
- [ ] JSON is edited through structured controls
- [ ] Dropdowns prevent invalid values where applicable
- [ ] Pretty JSON preview is available
- [ ] Validation runs before save
- [ ] Room is updated only after successful validation
- [ ] Home updates automatically through Flow
- [ ] No application restart is required
- [ ] No renderer logic exists in the editor
- [ ] Repository abstraction is preserved

---

# 40. Phase Dependency

```text id="server31"
Phase 10 – Home
        │
        ▼
Phase 11 – Local Server Panel
        │
        ▼
Phase 12 – Navigation Integration
```

---

# 41. Phase Completion Checklist

| Item | Status |
|------|--------|
| Server Screen | ✅ Planned |
| MVI Architecture | ✅ Planned |
| Screen Selector | ✅ Planned |
| Section Selector | ✅ Planned |
| Component Selector | ✅ Planned |
| Dynamic Widget Editor | ✅ Planned |
| JSON Preview | ✅ Planned |
| Validation | ✅ Planned |
| Save/Reset Flow | ✅ Planned |
| Live Home Update | ✅ Planned |
| Accessibility | ✅ Planned |
| UI Tests | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# 42. Architecture Improvements

To make the assignment closer to a real production SDUI platform, the following enhancements should be implemented during this phase:

### Widget Metadata Registry

Instead of hardcoding editor fields:

```text id="server32"
Widget Type

↓

WidgetMetadata

↓

Editor Definition

↓

Dynamic Form
```

Each widget exposes editable properties through metadata.

This allows adding new widgets without changing the editor UI.

---

### Form Field Types

Support reusable editor controls:

```text id="server33"
TextField

NumberField

BooleanSwitch

Dropdown

ColorTokenDropdown

TypographyDropdown

IconDropdown

ActionEditor

VisibilitySwitch
```

The editor composes these dynamically.

---

### Validation Panel

Instead of showing only a Snackbar, display a dedicated validation panel containing:

- Error message
- Property name
- Severity
- Suggested correction (when available)

This provides a much better developer experience.

---

### Editor Separation

Separate the editor into:

```text id="server34"
Selection Panel

↓

Property Editor

↓

Validation Panel

↓

JSON Preview

↓

Action Bar
```

This keeps the UI scalable as additional widgets are added.

---

### Future Extensibility

The architecture should allow future support for:

- Multiple SDUI screens
- Import JSON
- Export JSON
- Draft mode
- Undo/Redo
- Widget duplication
- Widget reordering
- Section creation
- Theme editing

without requiring a redesign of the Server Panel.

---

# Next Document

**40_PHASE_12_NAVIGATION_AND_APP_INTEGRATION.md**

This phase will integrate the complete application, including:

- Navigation Graph
- App Entry
- Hilt integration
- Landing → Home → Server navigation
- Back stack behavior
- Deep-link readiness
- App startup flow
- Initial database seeding trigger
- Global Snackbar handling
- Final application integration tests
- Production AI implementation prompt

This phase will connect all previously implemented modules into a single working application ready for final validation and polishing.