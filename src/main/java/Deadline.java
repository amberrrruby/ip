public class Deadline extends Task {
  private String deadlineTime;

  public Deadline(String taskName, String deadlineTime) {
    this.deadlineTime = deadlineTime;
    super(taskName);
  }

  public String getDeadlineTime() {
    return this.deadlineTime;
  }

  public void setDeadlineTime(String newDeadlineTime) {
    this.deadlineTime = newDeadlineTime;
  }

  @Override
  public String toString() {
    return "[D]" + super.toString() + " (by: " + this.deadlineTime + ")";
  }
}
