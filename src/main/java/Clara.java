import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clara {
  public void greet() {
    String greetingLine = "\n[Clara] Hello. I'm Clara. How can I assist you?";
    System.out.println(greetingLine);
  }

  public void end() {
    String exitLine = "[Clara] Goodbye. See you again.";
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

  private void addAndPrintTask(List<Task> tasks, final String[] userInput) {
    String taskName = String.join(" ", userInput);
    tasks.add(new Task(taskName));
    String echoString = "\n[Clara] added: " + taskName;
    System.out.println(echoString);
  }

  private void markTask(List<Task> tasks, final int taskIndex) throws ClaraException {
    if (taskIndex <= 0 || taskIndex > tasks.size()) {
      throw new ClaraException("Index out of bounds: given is " + Integer.toString(taskIndex));
    }
    Task theTask = tasks.get(taskIndex - 1);
    if (theTask.getIsDone()) {
      throw new ClaraException(
          "Task "
              + Integer.toString(taskIndex)
              + " has already been marked:\n| "
              + theTask.getTaskName());
    }
    theTask.setIsDone(true);
    String echoString = "\n[Clara] marked task " + taskIndex + ":\n| " + theTask.toString();
    System.out.println(echoString);
  }

  private void unmarkTask(List<Task> tasks, final int taskIndex) throws ClaraException {
    if (taskIndex <= 0 || taskIndex > tasks.size()) {
      throw new ClaraException("Index out of bounds: given is " + Integer.toString(taskIndex));
    }
    Task theTask = tasks.get(taskIndex - 1);
    if (!theTask.getIsDone()) {
      throw new ClaraException(
          "Task "
              + Integer.toString(taskIndex)
              + " has already been unmarked:\n| "
              + theTask.getTaskName());
    }
    theTask.setIsDone(false);
    String echoString = "\n[Clara] unmarked task " + taskIndex + ":\n| " + theTask.toString();
    System.out.println(echoString);
  }

  private static void assertArgumentCount(final String[] userInput, final int correctNumber)
      throws ClaraException {
    if (userInput.length - 1 != correctNumber) {
      throw new ClaraException(
          "Incorrect number of arguments passed to "
              + userInput[0]
              + ":\n| expected "
              + Integer.toString(correctNumber)
              + ", got: "
              + Integer.toString(userInput.length - 1));
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Clara clara = new Clara();
    clara.greet();

    List<Task> tasks = new ArrayList<>(100);

    boolean terminate = false;
    while (!terminate) {
      System.out.print(">> ");
      final String[] userInput = scanner.nextLine().split(" ");

      try {
        switch (userInput[0]) {
          case "bye" -> {
            assertArgumentCount(userInput, 0);
            terminate = true;
          }
          case "list" -> {
            assertArgumentCount(userInput, 0);
            clara.printList(tasks);
          }
          case "mark" -> {
            assertArgumentCount(userInput, 1);
            int taskIdxToMark = Integer.parseInt(userInput[1]);
            clara.markTask(tasks, taskIdxToMark);
          }
          case "unmark" -> {
            assertArgumentCount(userInput, 1);
            int taskIdxToUnmark = Integer.parseInt(userInput[1]);
            clara.unmarkTask(tasks, taskIdxToUnmark);
          }
          default -> {
            clara.addAndPrintTask(tasks, userInput);
          }
        }
      } catch (ClaraException ex) {
        System.out.println("[Clara] Something went wrong: " + ex + "\nTry again.");
        // INFO: AI-assisted invalid-index input validation. See CITATIONS.md [C-001].
      } catch (NumberFormatException ex) {
        System.out.println(
            "[Clara] Something went wrong: task number must be an integer.\nTry again.");
      }
    }

    clara.end();
  }
}
