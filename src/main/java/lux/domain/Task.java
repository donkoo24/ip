package lux.domain;

/**
 * A single task item with a description and completion status.
 * It has a global static counter to keep track of total number of tasks.
 */
public class Task {
    private static int numberOfTasks = 0;
    private String taskName;
    private boolean isCompleted;

    /**
     * Constructs a Task with description,
     * completion status set to uncompleted and increments the count of numberOfTasks.
     *
     * @param taskName The task description.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isCompleted = false;
        numberOfTasks++;
    }

    /**
     * Marks task as completed.
     */
    public void markCompleted() {
        this.isCompleted = true;
    }

    /**
     * Marks task as uncompleted.
     */
    public void unmarkCompleted() {
        this.isCompleted = false;
    }

    /**
     * Returns the current number of tasks.
     *
     * @return The current task count.
     */
    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    /**
     * Decrements the current task count.
     */
    public static void reduceTaskCount() {
        numberOfTasks--;
    }

    /**
     * Returns task description
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Returns the display form of the task, indicating the task description, completion status.
     *
     * @return String in the format: [X] description.
     */
    @Override
    public String toString() {
        if (isCompleted) {
            return String.format("[X] %s", taskName);
        } else {
            return String.format("[ ] %s", taskName);
        }
    }
}
