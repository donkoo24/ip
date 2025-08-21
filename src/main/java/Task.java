public class Task {
    private int taskNumber;
    private String taskName;
    private boolean isCompleted;

    public Task(int taskNumber, String taskName) {
        this.taskNumber = taskNumber;
        this.taskName = taskName;
        this.isCompleted = false;
    }

    private void markCompleted() {
        this.isCompleted = true;
    }

    private void unmarkCompleted() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        if (isCompleted) {
            return String.format("%d.[X] %s", taskNumber, taskName);
        } else {
            return String.format("%d.[ ] %s", taskNumber, taskName);
        }
    }
}
