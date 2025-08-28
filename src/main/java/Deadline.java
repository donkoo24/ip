import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private ParsedDate deadline;
    private DateTimeFormatter dateFormat;

    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.deadline = DateParser.parseDate(deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
