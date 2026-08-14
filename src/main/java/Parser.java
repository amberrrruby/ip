public class Parser {
  public static void requireNoArguments(final String command, final String arguments)
      throws ClaraException {
    if (!arguments.isEmpty()) {
      throw new ClaraException(command + " does not accept arguments");
    }
  }

  public static int parseTaskIdx(String arguments) throws NumberFormatException {
    return Integer.parseInt(arguments);
  }

  // NOTE: AI-assisted task-command input validation. See CITATIONS.md [C-002].
  public static Todo parseTodo(String arguments) throws ClaraException {
    if (arguments.isBlank()) {
      throw new ClaraException("A todo needs a description.");
    }
    return new Todo(arguments);
  }

  public static Deadline parseDeadline(String arguments) throws ClaraException {
    String[] nameAndTime = arguments.split(" /by ", 2);
    if (nameAndTime.length != 2 || nameAndTime[0].isBlank() || nameAndTime[1].isBlank()) {
      throw new ClaraException("Use: deadline <name> /by <time>.");
    }
    return new Deadline(nameAndTime[0], nameAndTime[1]);
  }

  public static Event parseEvent(String arguments) throws ClaraException {
    String[] nameAndRest = arguments.split(" /from ", 2);
    if (nameAndRest.length != 2 || nameAndRest[0].isBlank()) {
      throw new ClaraException("Use: event <name> /from <time> /to <time>.");
    }

    String[] fromTimeAndToTime = nameAndRest[1].split(" /to ", 2);
    if (fromTimeAndToTime.length != 2
        || fromTimeAndToTime[0].isBlank()
        || fromTimeAndToTime[1].isBlank()) {
      throw new ClaraException("Use: event <name> /from <time> /to <time>.");
    }
    return new Event(nameAndRest[0], fromTimeAndToTime[0], fromTimeAndToTime[1]);
  }
}
