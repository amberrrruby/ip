public class Event extends Task {
  private String fromTime;
  private String toTime;

  public Deadline(String taskName, String fromTime, String toTime) {
    this.fromTime = fromTime;
    this.toTime = toTime;
    super(taskName);
  }

  public String getFromTime() {
    return this.fromTime;
  }

  public void setFromTime(String newFromTime) {
    this.fromTime = newFromTime;
  }

  public String getToTime() {
    return this.toTime;
  }

  public void setToTime(String newToTime) {
    this.toTime = newToTime;
  }

  @Override
  public String toString() {
    return "[E]" + super.toString() + " (from: " + this.fromTime + " to: " + this.toTime + ")";
  }
}
