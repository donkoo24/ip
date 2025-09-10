package lux.parser;

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
        if (command.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        } else if (command.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else {
            Matcher markMatcher = MARK_PATTERN.matcher(command);
            Matcher unmarkMatcher = UNMARK_PATTERN.matcher(command);
            Matcher deleteMatcher = DELETE_PATTERN.matcher(command);
            Matcher toDoMatcher = TODO_PATTERN.matcher(command);
            Matcher deadlineMatcher = DEADLINE_PATTERN.matcher(command);
            Matcher eventMatcher = EVENT_PATTERN.matcher(command);
            Matcher findMatcher = FIND_PATTERN.matcher(command);

            if (markMatcher.find()) {
                return new MarkCommand(markMatcher.group(2));
            } else if (unmarkMatcher.find()) {
                return new UnmarkCommand(unmarkMatcher.group(2));
            } else if (deleteMatcher.find()) {
                return new DeleteCommand(deleteMatcher.group(2));
            } else if (toDoMatcher.find()) {
                return new ToDoCommand(toDoMatcher.group(2));
            } else if (deadlineMatcher.find()) {
                return new DeadlineCommand(deadlineMatcher.group(2), deadlineMatcher.group(3));
            } else if (eventMatcher.find()) {
                return new EventCommand(eventMatcher.group(2), eventMatcher.group(3), eventMatcher.group(4));
            } else if (findMatcher.find()) {
                return new FindCommand(findMatcher.group(2));
            } else {
                return new UnknownCommand();
            }
        }
    }
}
