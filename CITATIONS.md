# CITATIONS.md — Citation To-Do List

Tracks items requiring citation under the course reuse policy.
Rules and agent protocol are in CITATION_GUIDE.md.

Entries can be added by the agent (upon approval) or by the user directly.
If you add an entry yourself, the agent can help place the citation in code or documentation —
just point to the entry by ID.

---

## Entry Format

```
### [C-NNN] Short description
- **Type:** AI-generated | Adapted | Library | Reused
- **Location:** `path/to/file.ext` — function or line reference
- **Source:** [tool name, URL, repo, or description of origin]
- **Citation format:** inspired by | adapted from | @@author-reused | README/DG | comment only
- **Notes:** [any relevant detail — e.g. extent of AI use, which parts were modified]
- **Status:** Pending | Done
```

---

## Pending

---

## Done


### [C-001] Invalid task-index input validation
- **Type:** AI-generated
- **Location:** `src/main/java/Clara.java` — command error handling
- **Source:** Codex AI assistance
- **Citation format:** comment only
- **Notes:** Small change to show a user-facing error for non-numeric `mark` or `unmark` indexes.
- **Status:** Approved, done

### [C-002] Task-command syntax validation
- **Type:** AI-generated
- **Location:** `src/main/java/Parser.java` — task command parsing methods
- **Source:** Codex AI assistance
- **Citation format:** comment only
- **Notes:** Validation for blank descriptions and malformed deadline/event delimiters.
- **Status:** Approved, done

### [C-003] Consistent leading newline for Clara messages
- **Type:** AI-generated
- **Location:** `src/main/java/Clara.java` — farewell and error output
- **Source:** Codex AI assistance
- **Citation format:** comment only
- **Notes:** Adds a leading newline to remaining Clara-prefixed messages.
- **Status:** Approved, done

### [C-004] Task persistence and saved-task parsing
- **Type:** AI-generated
- **Location:** `src/main/java/TodoFileHandler.java` — task serialization, parsing, loading, and saving methods
- **Source:** Codex AI assistance and https://chatgpt.com/share/6a801443-b818-83ec-9c19-d1e74a1bc7e9
- **Citation format:** comment only
- **Notes:** Assistance with the delimited save-file format, validation, and Java NIO buffered file I/O.
- **Status:** Approved, done

### [C-005] `LocalDateTime` methods and patterns
- **Type:** AI-generated
- **Location:** `src/main/java/Parser.java` — parsing user input into `LocalDateTime`; `src/main/java/Deadline.java`, `src/main/java/Event.java` — formatting `LocalDateTime`
- **Source:** https://chatgpt.com/share/6a8013d9-a11c-83ec-9940-edc0bf614544
- **Citation format:** comment only
- **Notes:** Assistance with looking up class methods and demonstrating implementation patterns.
- **Status:** Done

### [C-006] Task find implementation
- **Type:** AI-generated
- **Location:** `src/main/java/Clara.java` — task finding method
- **Source:** https://chatgpt.com/share/6a8023ed-204c-83ec-a9a8-3b4df596f31d
- **Citation format:** comment only
- **Notes:** Assistance with implementing task find and functional pattern.
- **Status:** Done
