package clara.task;

/**
 * Represents a generic task that can be marked as completed or incomplete.
 */
public class Task {
    private String taskName;
    private boolean isDone;

    /**
     * Creates a task with the specified name.
     *
     * @param taskName the name of the task
     */
    public Task(String taskName) {
        assert taskName != null && !taskName.isBlank() : "Task name must not be blank";
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String newTaskName) {
        assert newTaskName != null && !newTaskName.isBlank() : "Task name must not be blank";
        this.taskName = newTaskName;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void setDone(boolean newIsDone) {
        this.isDone = newIsDone;
    }

    /**
     * Returns a string representation of the task, including its completion status.
     *
     * @return a formatted string representing this task
     */
    @Override
    public String toString() {
        String checkbox = "[" + (this.isDone ? "X" : " ") + "]";
        return checkbox + " " + this.taskName;
    }
}
