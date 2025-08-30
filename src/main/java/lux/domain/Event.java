package lux.domain;

import lux.parser.DateParser;
import lux.parser.ParsedDate;

public class Event extends Task {
    private ParsedDate start;
    private ParsedDate end;

    public Event (String taskName, String start, String end) {
        super(taskName);
        this.start = DateParser.parseDate(start);
        this.end = DateParser.parseDate(end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
