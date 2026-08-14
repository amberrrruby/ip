import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clara {
  public void greet() {
    String greetingLine = "\n[Clara] Hello. I'm Clara. How can I assist you?";
    System.out.println(greetingLine);
  }

  // AI-assisted output formatting. See CITATIONS.md [C-003]
  public void end() {
    String exitLine = "\n[Clara] Goodbye. See you again.";
    System.out.println(exitLine);
  }

  private void printList(final List<Task> tasks) {
    if (tasks.size() > 0) {
      System.out.println("\n[Clara] Here are your tasks in a list:");
      for (int i = 0; i < tasks.size(); i++) {
        String currentTask = Integer.toString(i + 1) + ". " + tasks.get(i).toString();
        System.out.println(currentTask);
      }
    } else {
      System.out.println("\n[Clara] There are no tasks. Yay...?");
    }
  }

  private void addAndPrintTask(List<Task> tasks, final Task task) {
    tasks.add(task);
    String echoString = "\n[Clara] added:\n| " + task.toString() + " (task #" + tasks.size() + ")";
    System.out.println(echoString);
  }

  private void markTask(List<Task> tasks, final int taskIndex) throws ClaraException {
    if (taskIndex <= 0 || taskIndex > tasks.size()) {
      throw new ClaraException("Index out of bounds: given is " + taskIndex);
    }
    Task theTask = tasks.get(taskIndex - 1);
    if (theTask.getIsDone()) {
      throw new ClaraException(
          "Task " + taskIndex + " has already been marked:\n| " + theTask.getTaskName());
    }
    theTask.setIsDone(true);
    String echoString = "\n[Clara] marked task " + taskIndex + ":\n| " + theTask.toString();
    System.out.println(echoString);
  }

  private void unmarkTask(List<Task> tasks, final int taskIndex) throws ClaraException {
    if (taskIndex <= 0 || taskIndex > tasks.size()) {
      throw new ClaraException("Index out of bounds: given is " + taskIndex);
    }
    Task theTask = tasks.get(taskIndex - 1);
    if (!theTask.getIsDone()) {
      throw new ClaraException(
          "Task " + taskIndex + " has already been unmarked:\n| " + theTask.getTaskName());
    }
    theTask.setIsDone(false);
    String echoString = "\n[Clara] unmarked task " + taskIndex + ":\n| " + theTask.toString();
    System.out.println(echoString);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Clara clara = new Clara();
    clara.greet();

    List<Task> tasks = new ArrayList<>(100);

    boolean terminate = false;
    while (!terminate) {
      System.out.print(">> ");
      final String[] userInput = scanner.nextLine().trim().split("\\s+", 2);
      if (userInput[0].isEmpty()) {
        continue;
      }

      try {
        String command = userInput[0];
        String arguments = userInput.length == 2 ? userInput[1] : "";
        switch (command) {
          case "bye" -> {
            Parser.requireNoArguments(command, arguments);
            terminate = true;
          }
          case "list" -> {
            Parser.requireNoArguments(command, arguments);
            clara.printList(tasks);
          }
          case "mark" -> {
            int taskIdxToMark = Parser.parseTaskIdx(command, arguments);
            clara.markTask(tasks, taskIdxToMark);
          }
          case "unmark" -> {
            int taskIdxToUnmark = Parser.parseTaskIdx(command, arguments);
            clara.unmarkTask(tasks, taskIdxToUnmark);
          }
          case "todo" -> {
            Todo todo = Parser.parseTodo(arguments);
            clara.addAndPrintTask(tasks, todo);
          }
          case "deadline" -> {
            Deadline deadline = Parser.parseDeadline(arguments);
            clara.addAndPrintTask(tasks, deadline);
          }
          case "event" -> {
            Event event = Parser.parseEvent(arguments);
            clara.addAndPrintTask(tasks, event);
          }
          default -> {
            throw new ClaraException("Unknown command: " + command);
          }
        }
      } catch (ClaraException ex) {
        System.out.println("\n[Clara] Something went wrong: " + ex.getMessage() + "\nTry again.");
      }
    }

    clara.end();
  }
}
