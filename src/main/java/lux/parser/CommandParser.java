package lux.parser;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logic unit for recognising and handling command that is called by user.
 * This class uses regex to help with pattern recognition.
 */
public class CommandParser {
    private static final Pattern MARK_PATTERN = Pattern.compile(
            "(^mark)\\s(\\d+(?:,\\s*\\d+)*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern UNMARK_PATTERN = Pattern.compile(
            "^(unmark)\\s(\\d+(?:,\\s*\\d+)*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern TODO_PATTERN = Pattern.compile("(todo)\\s(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern DEADLINE_PATTERN = Pattern.compile(
            "(deadline)\\s(.*)\\s/by\\s(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern EVENT_PATTERN = Pattern.compile(
            "(event)\\s(.*)\\s/from\\s(.*)\\s/to\\s(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern DELETE_PATTERN = Pattern.compile("(delete)\\s(\\d+(?:,\\s*\\d+)*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern FIND_PATTERN = Pattern.compile("(^find)\\s(.*)", Pattern.CASE_INSENSITIVE);
    private record Entry(Pattern pattern,
                         Function<Matcher, Command> factory) {}

    private static final List<Entry> ENTRIES = List.of(
            new Entry(Pattern.compile("(?i)^bye$"),
                    m -> new ByeCommand()),
            new Entry(Pattern.compile("(?i)^list$"),
                    m -> new ListCommand()),
            new Entry(MARK_PATTERN, m -> new MarkCommand(m.group(2))),
            new Entry(UNMARK_PATTERN, m -> new UnmarkCommand(m.group(2))),
            new Entry(DELETE_PATTERN, m -> new DeleteCommand(m.group(2))),
            new Entry(TODO_PATTERN, m -> new ToDoCommand(m.group(2))),
            new Entry(DEADLINE_PATTERN,
                    m -> new DeadlineCommand(m.group(2), m.group(3))),
            new Entry(EVENT_PATTERN,
                    m -> new EventCommand(m.group(2), m.group(3), m.group(4))),
            new Entry(FIND_PATTERN, m -> new FindCommand(m.group(2)))
    );

    /**
     * Constructs a CommandParser.
     */
    public CommandParser() {}

    /**
     * Parses a raw command input line into a Command. Validation of command arguments happen within the Command.
     *
     * @param command The raw input command from user to instruct Lux what to do.
     * @return a Command representing what the user wants to do, executing it applies the effects.
     */
    public Command parse(String command) {
        for (Entry e : ENTRIES) {
            Matcher matcher = e.pattern.matcher(command);
            if (matcher.find()) {
                return e.factory.apply(matcher);
            }
        }
        return new UnknownCommand();
    }
}
