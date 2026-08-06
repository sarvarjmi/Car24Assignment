# 38_PHASE_10_FEATURE_HOME.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Phase:** Phase 10 – Home Feature (Dynamic SDUI Renderer)
>
> **Module:** `feature-home`
>
> **Architecture:** Clean Architecture + MVI + Server Driven UI + Offline First
>
> **Status:** Implementation Phase 10
>
> **Priority:** ⭐ Highest (Core Assignment)
>
> **Estimated Time:** 18–24 Hours
>
> **Dependencies**
>
> - ✅ Phase 01 – Bootstrap
> - ✅ Phase 02 – core-common
> - ✅ Phase 03 – core-ui
> - ✅ Phase 04 – core-designsystem
> - ✅ Phase 05 – core-json
> - ✅ Phase 06 – core-database
> - ✅ Phase 07 – data
> - ✅ Phase 08 – domain
> - ✅ Phase 09 – feature-landing
> - 📖 05_SDUI_ARCHITECTURE.md
> - 📖 21_SDUI_RENDERER_ENGINE.md
> - 📖 22_COMPONENT_REGISTRY_AND_WIDGET_LIBRARY.md
> - 📖 23_JSON_VALIDATION_AND_PARSER_ENGINE.md
> - 📖 26_COMPLETE_WIDGET_CATALOG.md

---

# 1. Phase Objective

This is the **heart of the assignment**.

The Home Feature must **never contain hardcoded widgets**.

Instead it renders the entire screen dynamically from JSON stored inside **Room Database**.

Flow:

```text
assets/home.json

↓

Initial Seeder

↓

Room Database

↓

Repository

↓

Domain ScreenModel

↓

RendererHost

↓

Renderer Engine

↓

Component Registry

↓

Compose Widgets
```

---

# 2. Assignment Goal

The Home screen demonstrates a complete **Server-Driven UI** implementation.

Changing JSON in the Local Server Panel should immediately update the Home screen without restarting the app.

---

# 3. Responsibilities

The Home feature is responsible for:

- Collecting Home Screen Flow
- MVI State Management
- Invoking RendererHost
- Displaying Loading/Error/Empty states
- Reacting to JSON updates
- Triggering local refresh
- Showing graceful fallback UI

The Home feature is **not responsible** for:

- Parsing JSON
- Rendering individual widgets
- Database access
- Widget registration
- Navigation implementation

---

# 4. Architecture Position

```text
Room

↓

Repository

↓

ObserveHomeScreenUseCase

↓

HomeViewModel

↓

HomeState

↓

RendererHost

↓

Renderer Engine

↓

Compose UI
```

---

# 5. Module Structure

```text
feature-home/

src/main/kotlin/

com.assignment.feature.home/

├── ui/
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
├── renderer/
│
├── preview/
│
├── navigation/
│
└── di/
```

---

# 6. MVI Flow

```text
Intent

↓

ViewModel

↓

UseCase

↓

Repository

↓

Flow<ScreenModel>

↓

Reducer

↓

HomeState

↓

Compose
```

---

# 7. Home Screen Rules

HomeScreen **must never contain**:

- Text()
- Button()
- Card()
- LazyColumn()
- LazyRow()
- Image()

except for:

- Loading UI
- Error UI
- Empty UI

Everything else comes from SDUI.

---

# 8. HomeState

```text
Loading

Success(ScreenModel)

Empty

Error
```

State should be immutable.

---

# 9. HomeIntent

Support

```text
LoadScreen

Refresh

Retry
```

No JSON editing intents.

---

# 10. HomeEvent

Examples

```text
ShowSnackbar

Navigate

ScrollTop (future)
```

---

# 11. HomeViewModel

Responsibilities

- Observe Home Flow
- Handle intents
- Reduce state
- Emit UI events

Never parse JSON directly.

---

# 12. RendererHost

RendererHost is the only SDUI entry point inside Home.

Responsibilities

- Receive ScreenModel
- Invoke Renderer Engine
- Display rendered content

---

# 13. Renderer Flow

```text
ScreenModel

↓

Sections

↓

Components

↓

Registry

↓

WidgetRenderer

↓

Compose
```

---

# 14. Home Lifecycle

```text
Launch

↓

Observe Flow

↓

Loading

↓

Success

↓

JSON Update

↓

Automatic Recomposition
```

No manual reload required.

---

# 15. Initial Screen

After first launch

Room already contains seeded JSON.

Home should immediately render.

No asset loading occurs here.

---

# 16. Loading State

Use reusable component from `core-ui`.

Displayed while waiting for first Flow emission.

---

# 17. Empty State

Display when

- Screen missing
- No sections
- No components

Use generic EmptyState.

---

# 18. Error State

Display when

- Invalid ScreenModel
- Repository failure
- Parsing failure propagated

Never crash.

---

# 19. Refresh

Refresh means

```text
Re-read current Room data
```

No network request exists.

---

# 20. State Restoration

Configuration changes should preserve:

- Current state
- Scroll position (where applicable)

Do not reload unnecessarily.

---

# 21. Scroll Strategy

Renderer controls scrolling.

Preferred structure

```text
LazyColumn

↓

Sections

↓

Widgets
```

Avoid nested LazyColumns unless required.

---

# 22. Section Rendering

Each section

↓

Visibility check

↓

Display order

↓

Render components

---

# 23. Component Rendering

Each component

↓

Registry lookup

↓

Renderer

↓

Compose

Unknown component

↓

UnknownRenderer

↓

Placeholder

---

# 24. Widget Support

Must support every widget from

```text
26_COMPLETE_WIDGET_CATALOG.md
```

including

- Text
- Button
- Image
- HeroBanner
- CarCard
- Search
- ChipGroup
- CTA
- Footer
- Layout widgets

---

# 25. Unknown Widget Strategy

Never crash.

```text
Unknown Type

↓

UnknownRenderer

↓

Placeholder

↓

Continue Rendering
```

---

# 26. Live Update Flow

```text
Server Panel

↓

Save JSON

↓

Room

↓

Flow

↓

HomeViewModel

↓

Reducer

↓

Renderer

↓

Updated UI
```

---

# 27. Navigation

Navigation events originate from:

```text
Action Engine
```

Not from Home composables.

---

# 28. Theme

All styling comes from:

```text
Design System

+

Renderer Style Resolver
```

Never hardcode colors.

---

# 29. Dependency Rules

Allowed

- domain
- core-ui
- core-designsystem
- renderer interfaces

Not Allowed

- Room
- DAO
- JSON parser
- DTOs

---

# 30. Accessibility

Every rendered widget should preserve:

- Content descriptions
- Touch targets
- Dynamic fonts
- Material semantics

Renderer should pass accessibility metadata through.

---

# 31. Performance

Although benchmarking is outside scope:

- Immutable state
- Stable parameters
- Efficient Flow collection
- Stateless renderers
- Avoid unnecessary recomposition

---

# 32. Testing Strategy

## ViewModel Tests

Verify

- Initial loading
- Success
- Error
- Retry
- Refresh

## Compose Tests

Verify

- Loading UI
- Empty UI
- Error UI
- Rendered widgets
- Dynamic updates

## Integration Tests

Verify

Room update

↓

Repository

↓

ViewModel

↓

Renderer

↓

Visible UI update

---

# 33. Best Practices

Always

- One-way data flow
- Stateless UI
- RendererHost abstraction
- Domain models only
- Immutable state

Never

- Parse JSON
- Access Room
- Hardcode widgets
- Duplicate renderer logic

---

# 34. Acceptance Criteria

Phase 10 is complete when:

- Home MVI implemented
- Flow collected
- RendererHost integrated
- Dynamic rendering works
- Live Room updates work
- Loading/Error/Empty states implemented
- Tests passing

---

# 35. Common Pitfalls

Avoid

- Manual widget creation
- Repository access from UI
- JSON parsing in ViewModel
- Direct renderer usage outside RendererHost
- Mutable state

---

# 36. Definition of Done

- Home renders from Room only
- No hardcoded widgets
- Live updates verified
- Renderer integration complete
- Tests pass

---

# 37. Production AI Prompt

## Objective

Implement the complete **feature-home** module.

### Mandatory First Step

Inspect

- Existing Renderer Engine
- Component Registry
- Domain models
- Home repository
- Navigation
- core-ui
- Design System

Never regenerate completed files.

---

### Create Packages

- ui
- state
- intent
- event
- reducer
- viewmodel
- renderer
- preview
- navigation
- di

---

### Generate

#### UI

- HomeScreen
- HomeContent
- RendererHost

#### ViewModel

- HomeViewModel

#### MVI

- HomeState
- HomeIntent
- HomeEvent
- HomeReducer

#### Integration

- ObserveHomeScreenUseCase integration
- RendererHost integration

---

### Requirements

- Compose only
- MVI
- Stateless UI
- No hardcoded widgets
- Room updates reflected automatically
- RendererHost only
- Domain models only
- Loading/Error/Empty support
- Accessibility support
- Material 3

---

### Tests

Generate

- ViewModel tests
- Compose UI tests
- RendererHost integration tests
- Flow update tests
- State restoration tests

---

### Output Summary

Provide

- Files created
- Files modified
- Renderer integration summary
- State flow diagram
- Test coverage
- Deferred work
- Architecture compliance checklist

---

# 38. Assignment Validation Checklist

This phase satisfies the assignment only if all of the following are true:

- [ ] Home renders entirely from Room-backed JSON
- [ ] No business widget is manually placed in `HomeScreen`
- [ ] Editing JSON from the Server Panel updates Home instantly
- [ ] Unknown widgets do not crash rendering
- [ ] Loading, Error, and Empty states are handled
- [ ] `RendererHost` is the single rendering entry point
- [ ] Domain models are the only models consumed by the feature
- [ ] Material 3 and Design System are respected

---

# 39. Phase Dependency

```text
Phase 09 – Landing
        │
        ▼
Phase 10 – Home
        │
        ▼
Phase 11 – Local Server Panel
```

---

# 40. Phase Completion Checklist

| Item | Status |
|------|--------|
| Home MVI | ✅ Planned |
| Home Screen | ✅ Planned |
| RendererHost | ✅ Planned |
| ObserveHomeScreen UseCase | ✅ Planned |
| Loading State | ✅ Planned |
| Empty State | ✅ Planned |
| Error State | ✅ Planned |
| Live Room Updates | ✅ Planned |
| SDUI Integration | ✅ Planned |
| Accessibility | ✅ Planned |
| UI Tests | ✅ Planned |
| AI Prompt | ✅ Complete |

---

# Next Document

**39_PHASE_11_FEATURE_SERVER_PANEL.md**

This phase implements the **Local SDUI Server Panel**, the second major assignment feature.

It will include:

- Native Compose Server Panel
- JSON editor
- Screen selector
- Section selector
- Component selector
- Widget editor
- Fixed dropdowns for widget type and state
- JSON validation
- Pretty JSON preview
- Save / Reset actions
- Repository integration
- Live Room updates
- Undo-ready architecture
- Complete MVI implementation
- UI tests
- Production-grade AI implementation prompt

This phase completes the assignment's **local backend simulation**, allowing users to modify Room-stored JSON and immediately observe changes on the SDUI Home screen.