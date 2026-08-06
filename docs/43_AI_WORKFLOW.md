# 43_AI_WORKFLOW.md

> **Project:** Cars24 Mobile Engineering Assignment – Server Driven UI (SDUI)
>
> **Document:** AI Development Workflow
>
> **Version:** 1.0
>
> **Status:** Final Development Guide
>
> **Purpose:** Define a structured, production-grade workflow for implementing the entire assignment incrementally using AI while ensuring architecture consistency, zero duplicate code generation, and seamless integration between phases.

---

# 1. Objective

This document defines **how AI should be used throughout the project**.

The goal is to ensure AI behaves like a **Senior Android Engineer**, not merely a code generator.

Every AI interaction must:

- Follow Clean Architecture.
- Preserve project consistency.
- Avoid regenerating existing files.
- Maintain production-quality code.
- Integrate seamlessly with previous phases.

---

# 2. AI Development Philosophy

AI should work incrementally.

Never generate the whole project at once.

Always follow:

```text
Documentation

↓

Architecture Review

↓

Implementation Plan

↓

Generate Module

↓

Review

↓

Refactor

↓

Testing

↓

Integration

↓

Next Module
```

---

# 3. Development Order

The project **must** be implemented in the following order.

```text
Phase 01

↓

Phase 02

↓

Phase 03

↓

...

↓

Phase 13

↓

QA

↓

Submission
```

Never skip phases.

---

# 4. AI Rules

AI must always:

- Inspect existing code.
- Preserve architecture.
- Generate production code.
- Follow SOLID.
- Follow MVI.
- Follow Clean Architecture.
- Use stable libraries only.
- Generate documentation where needed.

AI must never:

- Rewrite completed modules.
- Break public APIs.
- Duplicate logic.
- Ignore project conventions.

---

# 5. Before Generating Code

Before writing any code AI must inspect:

- Existing modules
- Existing packages
- Existing Gradle setup
- Existing dependencies
- Existing interfaces
- Existing models
- Existing documentation

Only missing files should be created.

---

# 6. File Creation Policy

AI must explicitly identify:

## New Files

```text
Create

↓

New Files
```

## Existing Files

```text
Modify

↓

Only Required Sections
```

Never overwrite entire files unless requested.

---

# 7. Module Workflow

For every module:

```text
Read Documentation

↓

Review Existing Code

↓

Identify Missing Files

↓

Generate Files

↓

Generate Tests

↓

Verify Build

↓

Summarize Changes
```

---

# 8. Implementation Workflow

Every feature follows:

```text
Architecture

↓

Interfaces

↓

Models

↓

Repository

↓

UseCases

↓

ViewModel

↓

UI

↓

Tests

↓

Integration
```

Never start from UI.

---

# 9. Prompt Workflow

Every implementation prompt should contain:

- Objective
- Existing architecture
- Files to inspect
- Files to create
- Files to modify
- Coding standards
- Testing requirements
- Output summary

---

# 10. File Generation Rules

Each response must clearly state:

## Files Created

```text
core-json/

JsonParser.kt

...
```

---

## Files Modified

```text
RepositoryModule.kt

NavigationGraph.kt
```

---

## Files Left Untouched

Optional when useful.

---

# 11. Code Generation Rules

Generated code must:

- Compile
- Use meaningful names
- Include KDoc for public APIs
- Follow Kotlin conventions
- Avoid unnecessary comments
- Prefer readability over cleverness

---

# 12. Architecture Validation

Before finishing a module AI must verify:

- Clean Architecture boundaries
- Dependency direction
- SOLID compliance
- MVI consistency
- Module isolation

---

# 13. Testing Workflow

For every generated module AI should produce:

- Unit tests
- Integration tests (where applicable)
- UI tests (Compose features)

Tests are part of the implementation, not optional.

---

# 14. Build Verification

After each phase verify:

```text
Compile

↓

Run Unit Tests

↓

Run Lint

↓

Review Output
```

Do not continue if the current phase is broken.

---

# 15. Refactoring Policy

AI may refactor only when:

- Architecture improves
- Performance improves
- Readability improves
- Bugs are fixed

Never refactor unrelated modules.

---

# 16. Error Handling Workflow

Whenever implementation fails:

```text
Analyze

↓

Root Cause

↓

Minimal Fix

↓

Regression Check
```

Avoid broad rewrites.

---

# 17. Integration Workflow

After every phase:

```text
Generated Module

↓

Existing Modules

↓

Integration Check

↓

Dependency Check

↓

Build Check
```

---

# 18. AI Review Checklist

Before responding AI should verify:

- Code compiles logically
- Architecture preserved
- No duplicate files
- Naming consistent
- Stable libraries only
- Tests included

---

# 19. Prompt Template

Every implementation prompt should follow:

```text
1. Objective

2. Repository Inspection

3. Files to Create

4. Files to Modify

5. Implementation Rules

6. Testing

7. Output Summary
```

---

# 20. Phase Completion Workflow

Each phase ends with:

- Summary
- Files created
- Files modified
- Tests added
- Remaining work
- Next phase

---

# 21. AI Communication Rules

AI responses should always include:

- What was inspected
- What was created
- What changed
- Why changes were made
- Integration impact

---

# 22. Regression Prevention

AI must ensure:

- Existing public APIs remain compatible.
- Existing tests continue to pass.
- Previous phases remain functional.
- No accidental architecture drift.

---

# 23. Documentation Synchronization

Whenever implementation changes architecture:

- Update affected documentation.
- Keep README aligned.
- Keep implementation documents synchronized.

---

# 24. Version Control Workflow

Recommended Git strategy:

```text
main

↓

feature/bootstrap

↓

feature/core-json

↓

feature/database

↓

feature/home

↓

feature/server
```

Each phase should map to one or more focused commits.

Commit messages should follow Conventional Commits.

Example:

```text
feat(core-json): implement parser and validation pipeline

fix(home): prevent renderer crash on unknown widget

test(server): add JSON validation tests
```

---

# 25. AI Review Gates

Before moving to the next phase, confirm:

| Check | Required |
|--------|----------|
| Architecture valid | ✅ |
| Code compiles | ✅ |
| Tests added | ✅ |
| Documentation updated | ✅ |
| No duplicate files | ✅ |
| Integration verified | ✅ |

If any item fails, fix it before continuing.

---

# 26. AI Session Workflow

For every ChatGPT implementation session:

```text
Open Documentation

↓

Inspect Repository

↓

Implement Requested Phase

↓

Run Internal Review

↓

Provide Summary

↓

Wait For Next Phase
```

Never generate future phases unless requested.

---

# 27. Production AI Master Prompt

Use the following instructions before implementing **any** phase.

```text
You are implementing one phase of a production Android application.

Requirements:

- Inspect the existing repository first.
- Never regenerate completed files.
- Modify only files required for the requested phase.
- Follow Clean Architecture.
- Follow SOLID.
- Follow MVI.
- Use Kotlin and Jetpack Compose.
- Use only stable libraries defined in libs.versions.toml.
- Keep modules isolated.
- Generate production-ready code.
- Generate tests.
- Generate KDoc for public APIs.
- Preserve backward compatibility.
- Summarize:
  • Files inspected
  • Files created
  • Files modified
  • Tests added
  • Remaining work
```

---

# 28. Final AI Workflow

The complete development lifecycle should always be:

```text
Read Phase Document

↓

Inspect Repository

↓

Identify Missing Work

↓

Implement

↓

Test

↓

Review

↓

Integrate

↓

Summarize

↓

Wait For Next Phase
```

This workflow guarantees that the project is built incrementally, remains maintainable, and never loses architectural consistency.

---

# 29. AI Quality Checklist

Before every response, AI should verify:

- [ ] Repository inspected
- [ ] Existing code preserved
- [ ] Only requested phase implemented
- [ ] Stable libraries used
- [ ] Clean Architecture maintained
- [ ] MVI maintained
- [ ] SOLID maintained
- [ ] Tests generated
- [ ] Public APIs documented
- [ ] Integration verified
- [ ] Output summary included

---

# 30. Success Criteria

The AI-assisted development process is considered successful when:

- Every phase is implemented independently.
- No completed work is regenerated.
- Each module integrates seamlessly with previous modules.
- Documentation and implementation remain synchronized.
- The final application satisfies all assignment requirements while maintaining production-grade architecture, readability, and testability.

---

# Next Document

**44_IMPLEMENTATION_CHECKLIST.md**

This document will provide a **master implementation checklist** covering every phase, module, package, feature, test, and integration point. It will act as the final day-to-day execution tracker, allowing progress to be monitored from **0% to 100% completion** with no missing implementation tasks.