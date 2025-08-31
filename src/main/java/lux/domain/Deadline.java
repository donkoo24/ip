package lux.domain;

import lux.parser.DateParser;
import lux.parser.ParsedDate;

/**
 * A task that has a due date (deadline).
 */
public class Deadline extends Task {
    private ParsedDate deadline;

    /**
     * Constructs a Deadline with a description and a due date.
     * <p>The deadline string is parsed using DateParser and then stored as a ParsedDate.
     * Supported formats:
     * <ul>
     *     <li>d/MM/yyyy</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>MMM d yyyy</li>
     * </ul>
     * </p>
     *
     * @param taskName The task description.
     * @param deadline The deadline string to parse.
     */
    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.deadline = DateParser.parseDate(deadline);
    }

    /**
     * Returns the display form of the deadline task, indicating the type of task,
     * completion status, description of task, and deadline.
     *
     * @return String in the format: [D][X] description (by: deadline).
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
