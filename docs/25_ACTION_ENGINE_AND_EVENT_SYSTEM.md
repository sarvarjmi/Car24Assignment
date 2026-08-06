# 25_ACTION_ENGINE_AND_EVENT_SYSTEM.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Platform:** Android (Kotlin + Jetpack Compose)
>
> **Architecture:** Clean Architecture + MVI + Generic Action Engine
>
> **Module:** `core-renderer`
>
> **Document Version:** 1.0
>
> **Status:** Final
>
> **Prerequisites**
>
> - 00–24 Documentation
> - SDUI Renderer Engine
> - Component Registry
> - Navigation Design
> - State Management
> - JSON Schema Design

---

# Document Objective

This document defines the complete **Action Engine** used by the SDUI platform.

The Action Engine converts **JSON-defined actions** into application events.

Instead of writing

```kotlin
Button(
    onClick = { navController.navigate("home") }
)
```

the application follows

```text
JSON Action

↓

Action Engine

↓

Action Dispatcher

↓

ViewModel Event

↓

Navigator / UseCase

↓

UI Updated
```

Widgets never know how actions are executed.

---

# 1. Action Engine Philosophy

The renderer only renders.

The Action Engine executes.

Every widget interaction is driven by JSON.

No widget contains business logic.

---

# 2. Responsibilities

The Action Engine is responsible for

- Action parsing
- Action validation
- Action execution
- Event dispatching
- Navigation requests
- State update requests
- Safe fallback

The Action Engine never

- Accesses Room directly
- Executes SQL
- Parses raw JSON
- Modifies Compose state

---

# 3. Module Structure

```text
core-renderer/

action/

├── ActionEngine

├── ActionDispatcher

├── ActionRegistry

├── ActionValidator

├── ActionExecutor

├── ActionFactory

├── ActionResult

├── ActionMetadata

├── NavigationAction

├── SnackbarAction

├── DialogAction

├── UpdateStateAction

├── RefreshAction

├── CompositeAction

└── UnknownAction
```

---

# 4. Action Pipeline

```text
Widget Click

↓

Component Action

↓

Action Engine

↓

Validation

↓

Registry

↓

Executor

↓

UiEvent

↓

ViewModel
```

---

# 5. JSON Action Model

Example

```json
{
  "type": "navigate",
  "destination": "home"
}
```

Every action follows the same structure.

---

# 6. Action Categories

Supported categories

```text
Navigation

UI Feedback

State Update

Refresh

Composite

Custom

Analytics (Future)
```

---

# 7. Navigation Actions

Supported

```text
Navigate

NavigateBack

PopTo

Replace
```

Example

```json
{
  "type":"navigate",
  "destination":"home"
}
```

---

# 8. Snackbar Action

Example

```json
{
  "type":"snackbar",
  "message":"Saved Successfully"
}
```

Execution

↓

UiEvent

↓

SnackbarHost

---

# 9. Dialog Action

Example

```json
{
  "type":"dialog",
  "title":"Delete?",
  "message":"Confirm deletion"
}
```

Dialogs are emitted as UI events.

---

# 10. Refresh Action

Example

```json
{
  "type":"refresh"
}
```

Flow

↓

Intent

↓

ViewModel

↓

Repository

↓

Room

↓

Renderer

---

# 11. State Update Action

Used for assignment

Example

```json
{
  "type":"updateComponent",
  "componentId":"banner_title"
}
```

Server panel uses UseCases to update Room.

---

# 12. Composite Action

Multiple actions

```text
Click

↓

Snackbar

↓

Navigate

↓

Refresh
```

Executed sequentially.

---

# 13. Unknown Action

Flow

```text
Unknown Action

↓

Log Warning

↓

Ignore

↓

Continue
```

Application never crashes.

---

# 14. Action Registry

Registry maps

```text
Action Type

↓

Executor
```

Example

| Action | Executor |
|---------|----------|
| navigate | NavigationExecutor |
| snackbar | SnackbarExecutor |
| dialog | DialogExecutor |
| refresh | RefreshExecutor |
| updateComponent | UpdateComponentExecutor |

---

# 15. Action Factory

Responsibilities

- Resolve executor
- Create runtime action
- Return fallback when missing

---

# 16. Action Validation

Validate

- Type
- Required fields
- Version
- Parameters
- Destination
- Component ID

Invalid actions are rejected.

---

# 17. Action Execution Flow

```text
Widget

↓

Action

↓

Validator

↓

Executor

↓

UiEvent
```

---

# 18. Navigation Integration

The Action Engine never accesses NavController.

Flow

```text
Action

↓

UiEvent

↓

ViewModel

↓

Navigator
```

---

# 19. State Integration

Update actions

↓

UseCase

↓

Repository

↓

Room

↓

Flow

↓

Renderer

---

# 20. Result Model

Every action returns

```text
ActionResult

Success

Failure

Ignored
```

Never throw expected runtime errors.

---

# 21. Error Handling

Recoverable errors

- Unknown action
- Invalid parameters
- Missing destination
- Invalid component ID

Recovery

↓

Log

↓

Ignore

↓

Continue

---

# 22. Security Rules

Reject actions that attempt to

- Execute SQL
- Run Kotlin code
- Access filesystem
- Invoke reflection
- Execute shell commands

Only predefined actions are allowed.

---

# 23. Versioning

Every action supports

```json
{
  "actionVersion":"1.0.0"
}
```

Validator checks compatibility before execution.

---

# 24. Extending the Engine

To add a new action

1. Define JSON contract.
2. Create Action model.
3. Create Executor.
4. Register in ActionRegistry.
5. Add validation.
6. Add tests.

No renderer modifications required.

---

# 25. Testing Strategy

Unit Tests

- ActionValidator
- ActionRegistry
- ActionFactory
- Executors

Integration Tests

- Action → ViewModel
- Navigation flow
- Room update flow

UI Tests

- Navigation
- Snackbar
- Dialog
- Refresh
- Component updates

---

# 26. Assignment Action Flow

```text
User Click

↓

Renderer

↓

Action Engine

↓

Executor

↓

ViewModel

↓

Repository

↓

Room

↓

Renderer Refresh
```

---

# 27. Best Practices

Always

- Validate actions
- Keep executors stateless
- Emit UI events
- Use Result models
- Log unknown actions

Never

- Execute navigation inside widgets
- Access Room directly
- Execute arbitrary code
- Hardcode actions in screens

---

# 28. Acceptance Criteria

The Action Engine is complete when

- Generic Action Engine implemented
- Registry implemented
- Factory implemented
- Validators implemented
- Navigation actions supported
- UI feedback actions supported
- State update actions supported
- Unknown actions safely ignored
- Tests completed

---

# 29. Production AI Prompt

## Objective

Generate the complete Action Engine.

### Generate

#### Core

- ActionEngine
- ActionRegistry
- ActionFactory
- ActionDispatcher
- ActionValidator
- ActionResult

#### Executors

- NavigationExecutor
- SnackbarExecutor
- DialogExecutor
- RefreshExecutor
- UpdateComponentExecutor
- CompositeActionExecutor
- UnknownActionExecutor

#### Models

- ActionModel
- NavigationAction
- SnackbarAction
- DialogAction
- RefreshAction
- UpdateComponentAction

#### Infrastructure

- ActionMetadata
- VersionValidator
- ActionLogger

### Requirements

- Generic architecture
- Stateless executors
- MVI compatible
- Clean Architecture compliant
- No direct navigation from widgets
- No Room access from executors
- Safe fallback for unknown actions
- JSON-driven execution only

### Tests

Generate

- Registry tests
- Validator tests
- Executor tests
- Navigation action tests
- State update tests
- Composite action tests
- Unknown action tests

### Output Summary

Provide

- Files created
- Files modified
- Action execution pipeline
- Registry dependency graph
- Supported action matrix
- Architecture compliance checklist

---

# Action Engine Documentation Status

| Section | Status |
|----------|--------|
| Action Engine Architecture | ✅ Complete |
| Action Registry | ✅ Complete |
| Action Factory | ✅ Complete |
| Executor System | ✅ Complete |
| Navigation Integration | ✅ Complete |
| State Update Flow | ✅ Complete |
| Validation Strategy | ✅ Complete |
| Error Handling | ✅ Complete |
| Testing Strategy | ✅ Complete |
| AI Implementation Prompt | ✅ Complete |

---

# Next Document

**26_COMPLETE_WIDGET_CATALOG.md**

This document will become the **master widget specification** for the entire SDUI platform and will define:

- Every widget supported by the assignment
- Complete JSON schema for each widget
- Property definitions
- Required vs optional fields
- Style support
- Action support
- Validation rules
- Compose mapping
- Renderer implementation guidelines
- Widget-specific AI prompts
- Sample JSON for every widget

This will serve as the single source of truth for implementing every renderer in the project.