# Project Clara

A simple command-line personal assistant chatbot built in Java as part of the NUS CS2103/T Individual Project.

Clara currently supports adding, listing, marking, unmarking, and deleting tasks.

## Getting Started

### Requirements

* Java 25

### Running the Application

Run (or, compile and run) `Clara.java` from your IDE or using the Java 25 compiler/runtime:

```bash
javac Clara.java
java Clara
```

Refer to the user guide `/docs/README.md` for supported behaviour.

## Project Structure

The project will gradually be expanded as new functionality is introduced.

```text
.
├── docs/
│   └── README.md       # Guide targeted for users
├── src/
│   └── main/
│       └── java/
│           ├── Clara.java          # Application entry point and command handling
│           ├── ClaraException.java # User-input exception
│           └── Task.java           # To-do task model
├── .gitignore
├── AGENTS.md           # Instructions for AI coding agents
├── CLAUDE.md           # For Claude Code (redirects to `AGENTS.md`)
├── CONTRIBUTORS.md     # List of contributors to the project
├── CITATIONS.md        # Citation tracking
├── CITATION_RULES.md   # Citation requirements and guidance
├── PROJECT.md          # Description of the project, **intended for AI coding agents**
└── README.md           # Project documentation
```

### Project Guidelines

When modifying the project, keep the existing structure and naming conventions consistent. New functionality should be accompanied by appropriate tests as the test suite is introduced.

The [CS2103/T Project Duke specification](https://nus-cs2103-ay2627-s1.github.io/website/projectDuke/index.html) is the authoritative source for project requirements and progression.

## Status

This project is currently at: **Level 6**. It will be developed incrementally throughout the iP.


*Level 0 README: Generated and modified from a [ChatGPT chat thread](https://chatgpt.com/share/6a7dd837-21bc-83ec-824e-6ab1b746ac1f).*
