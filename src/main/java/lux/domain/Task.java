package lux.domain;

public class Task {
    private String taskName;
    private boolean isCompleted;
    private static int numberOfTasks = 0;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isCompleted = false;
        numberOfTasks++;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public void unmarkCompleted() {
        this.isCompleted = false;
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static void reduceTaskCount() {
        numberOfTasks--;
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
