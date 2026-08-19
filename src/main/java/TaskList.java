import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

// NOTE: AI-assisted OOP refactoring. See CITATIONS.md [C-007].

/**
 * Represents Clara's collection of tasks and provides operations to manipulate them.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    /**
     * Constructs a task list initialized with the given tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns whether the task list contains no tasks.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Returns the task at the specified 1-based task index.
     *
     * @param taskIndex the one-based index of the task to retrieve
     * @return the task at the specified index
     * @throws ClaraException if the task index is out of bounds
     */
    public Task getTask(final int taskIndex) throws ClaraException {
        if (taskIndex <= 0 || taskIndex > this.tasks.size()) {
            throw new ClaraException("Index out of bounds: given is " + taskIndex);
        }
        return this.tasks.get(taskIndex - 1);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add
     */
    public void addTask(final Task task) {
        this.tasks.add(task);
    }

    /**
     * Marks the specified task as completed.
     *
     * @param taskIndex the one-based index of the task to mark
     * @return the marked task
     * @throws ClaraException if the task index is invalid or the task is already marked
     */
    public Task markTask(final int taskIndex) throws ClaraException {
        if (taskIndex <= 0 || taskIndex > this.tasks.size()) {
            throw new ClaraException("Index out of bounds: given is " + taskIndex);
        }
        Task theTask = this.tasks.get(taskIndex - 1);
        if (theTask.isDone()) {
            throw new ClaraException(
                    "Oops - Task " + taskIndex + " is already marked:\n| " + theTask.getTaskName());
        }
        theTask.setDone(true);
        return theTask;
    }

    /**
     * Marks the specified task as incomplete.
     *
     * @param taskIndex the one-based index of the task to unmark
     * @return the unmarked task
     * @throws ClaraException if the task index is invalid or the task is already unmarked
     */
    public Task unmarkTask(final int taskIndex) throws ClaraException {
        if (taskIndex <= 0 || taskIndex > this.tasks.size()) {
            throw new ClaraException("Index out of bounds: given is " + taskIndex);
        }
        Task theTask = this.tasks.get(taskIndex - 1);
        if (!theTask.isDone()) {
            throw new ClaraException(
                    "Oops - Task " + taskIndex + " is already unmarked:\n| " + theTask.getTaskName());
        }
        theTask.setDone(false);
        return theTask;
    }

    /**
     * Deletes the specified task from the task list.
     *
     * @param taskIndex the one-based index of the task to delete
     * @return the removed task
     * @throws ClaraException if the task index is invalid
     */
    public Task deleteTask(final int taskIndex) throws ClaraException {
        if (taskIndex <= 0 || taskIndex > this.tasks.size()) {
            throw new ClaraException("Index out of bounds: given is " + taskIndex);
        }
        return this.tasks.remove(taskIndex - 1);
    }

    // NOTE: AI-assisted task find implementation. See CITATIONS.md [C-006].

    /**
     * Finds the 0-based indices of tasks whose names contain the given argument.
     *
     * @param argument the search term
     * @return list of matching 0-based task indices
     */
    public List<Integer> findMatchingIndices(String argument) {
        Pattern pattern = Pattern.compile(Pattern.quote(argument));

        return IntStream.range(0, this.tasks.size())
                .filter(i -> pattern.matcher(this.tasks.get(i).getTaskName()).find())
                .boxed()
                .toList();
    }
}
