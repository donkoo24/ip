public class Task {
    private String taskName;
    private boolean isCompleted;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isCompleted = false;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public void unmarkCompleted() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        if (isCompleted) {
            return String.format("[X] %s", taskName);
        } else {
            return String.format("[ ] %s", taskName);
        }
    }
}
