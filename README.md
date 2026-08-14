# Project Clara

**TODO: This will be further populated as the codebase grows.**

A simple command-line personal assistant chatbot built in Java as part of the NUS CS2103/T Individual Project.

## Getting Started

### Requirements

* Java 25

### Running the Application

The application currently consists of a single Java source file:

```text
src/
└── main/
    └── java/
        └── Clara.java
```

Run (or, compile and run) `Clara.java` from your IDE or using the Java 25 compiler/runtime:

```bash
javac Clara.java
java Clara
```

At the current stage, the program starts by printing a greeting message, echoes the command that the user typed, then prints an ending message when typed `bye`.

## Project Structure

The project will gradually be expanded as new functionality is introduced.

```text
.
├── docs/
│   └── README.md/      # Guide targeted for users
├── src/
│   └── main/
│       └── java/       # Application source code
├── .gitignore
├── AGENTS.md           # Instructions for AI coding agents
├── CLAUDE.md           # For Claude Code (redirects to `AGENTS.md`)
├── CONTRIBUTORS.md     # List of contributors to the project
├── PROJECT.md          # Description of the project, **intended for AI coding agents**
└── README.md           # Project documentation
```

Up till Level 1, `src/main/java/` contains only `Clara.java`. The other directories and files will be introduced as the project grows.

### Project Guidelines

When modifying the project, keep the existing structure and naming conventions consistent. New functionality should be accompanied by appropriate tests as the test suite is introduced.

The [CS2103/T Project Duke specification](https://nus-cs2103-ay2627-s1.github.io/website/projectDuke/index.html) is the authoritative source for project requirements and progression.

## Status

This project is currently at: **Level 1**. It will be developed incrementally throughout the iP.


*Level 0 README: Generated and modified from a [ChatGPT chat thread](https://chatgpt.com/share/6a7dd837-21bc-83ec-824e-6ab1b746ac1f).*
