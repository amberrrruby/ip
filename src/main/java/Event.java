public class Event extends Task {
  private String fromTime;
  private String toTime;

  public Event(String taskName, String fromTime, String toTime) {
    super(taskName);
    this.fromTime = fromTime;
    this.toTime = toTime;
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
