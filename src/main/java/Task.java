public class Task {
  private String taskName;
  private boolean isDone;

  public Task(String taskName) {
    this.taskName = taskName;
    this.isDone = false;
  }

  public String getTaskName() {
    return this.taskName;
  }

  public void setTaskName(String newTaskName) {
    this.taskName = newTaskName;
  }

  public boolean getIsDone() {
    return this.isDone;
  }

  public void setIsDone(boolean newIsDone) {
    this.isDone = newIsDone;
  }

  @Override
  public String toString() {
    String checkbox = "[" + (this.isDone ? "X" : " ") + "]";
    return checkbox + " " + this.taskName;
  }
}
