package lux.domain;

import lux.parser.DateParser;
import lux.parser.ParsedDate;

/**
 * A task that has a start date and end date.
 */
public class Event extends Task {
    private ParsedDate start;
    private ParsedDate end;

    /**
     * Constructs an Event with a description, a start date, and end date.
     * <p>The start and end string is parsed using DateParser and then stored as a ParsedDate.
     * Supported formats:
     * <ul>
     *     <li>d/MM/yyyy</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>MMM d yyyy</li>
     * </ul>
     * </p>
     *
     * @param taskName The task description.
     * @param start The start string to parse.
     * @param end The end string to parse.
     */
    public Event(String taskName, String start, String end) {
        super(taskName);
        this.start = DateParser.parseDate(start);
        this.end = DateParser.parseDate(end);
    }

    /**
     * Returns the display form of the deadline task, indicating the type of task, completion status,
     * description of task, start, and end date.
     *
     * @return String in the format: [E][X] description (from: start to: end).
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + "\n(from: " + start + " to: " + end + ")";
    }
}
