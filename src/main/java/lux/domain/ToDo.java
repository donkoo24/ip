package lux.domain;

/**
 * A task that only has a description (todo).
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo with a description.
     *
     * @param taskName The task description.
     */
    public ToDo(String taskName) {
        super(taskName);
    }

    /**
     * Returns the display form of the todo task,
     * indicating the type of task, completion status, description of task.
     *
     * @return String in the format: [T][X] description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
