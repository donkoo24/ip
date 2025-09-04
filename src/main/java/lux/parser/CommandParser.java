package lux.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lux.domain.Deadline;
import lux.domain.Event;
import lux.domain.Task;
import lux.domain.ToDo;
import lux.repo.TaskList;
import lux.storage.SaveFileManager;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Logic unit for recognising and handling command that is called by user.
 * This class uses regex to help with pattern recognition.
 */
public class CommandParser {
    private static final Pattern MARK_PATTERN = Pattern.compile("(^mark)\\s(\\d+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern UNMARK_PATTERN = Pattern.compile(
            "^(unmark)\\s(\\d+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern TODO_PATTERN = Pattern.compile("(todo)\\s(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern DEADLINE_PATTERN = Pattern.compile(
            "(deadline)\\s(.*)\\s/by\\s(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern EVENT_PATTERN = Pattern.compile(
            "(event)\\s(.*)\\s/from\\s(.*)\\s/to\\s(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern DELETE_PATTERN = Pattern.compile("(delete)\\s(.*)", Pattern.CASE_INSENSITIVE);
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
            return new Command() {
                @Override
                public String execute(TaskList tasks, Ui ui) {
                    StringBuilder saveData = new StringBuilder();
                    String reply;

                    for (int i = 0; i < tasks.getSize(); i++) {
                        saveData.append(tasks.getTask(i)).append(System.lineSeparator());
                    }

                    try {
                        SaveFileManager.updateSaveFile(saveData.toString());
                    } catch (IOException e) {
                        reply = "Did not manage to save task data";
                        ui.speak(reply + e.getMessage());
                        return reply + e.getMessage();
                    }

                    return ui.endConvo();
                }
                @Override
                public boolean isExit() {
                    return true;
                }
            };
        } else if (command.equalsIgnoreCase("list")) {
            return (tasks, ui) -> tasks.showList(ui);
        } else {
            Matcher markMatcher = MARK_PATTERN.matcher(command);
            Matcher unmarkMatcher = UNMARK_PATTERN.matcher(command);
            Matcher deleteMatcher = DELETE_PATTERN.matcher(command);
            Matcher toDoMatcher = TODO_PATTERN.matcher(command);
            Matcher deadlineMatcher = DEADLINE_PATTERN.matcher(command);
            Matcher eventMatcher = EVENT_PATTERN.matcher(command);
            Matcher findMatcher = FIND_PATTERN.matcher(command);

            if (markMatcher.find()) {
                return (tasks, ui) -> tasks.markTask(Integer.parseInt(markMatcher.group(2)), ui);
            } else if (unmarkMatcher.find()) {
                return (tasks, ui) -> tasks.unmarkTask(Integer.parseInt(unmarkMatcher.group(2)), ui);
            } else if (deleteMatcher.find()) {
                return (tasks, ui) -> tasks.deleteTask(Integer.parseInt(deleteMatcher.group(2)), ui);
            } else if (toDoMatcher.find()) {
                return (tasks, ui) -> {
                    if (toDoMatcher.group(2).isBlank()) {
                        throw new NoDescriptionException("bruh, task name cannot be empty la");
                    }
                    Task itemToAdd = new ToDo(toDoMatcher.group(2));
                    return tasks.addListItem(itemToAdd, ui);
                };
            } else if (deadlineMatcher.find()) {
                return (tasks, ui) -> {
                    if (deadlineMatcher.group(2).isBlank()) {
                        throw new NoDescriptionException("bruh, task name cannot be empty la");
                    } else if (deadlineMatcher.group(3).isBlank()) {
                        throw new NoDescriptionException("bruh, deadline cannot be empty la, if not go use todo");
                    }
                    Task itemToAdd = new Deadline(deadlineMatcher.group(2), deadlineMatcher.group(3));
                    return tasks.addListItem(itemToAdd, ui);
                };
            } else if (eventMatcher.find()) {
                return (tasks, ui) -> {
                    if (eventMatcher.group(2).isBlank()) {
                        throw new NoDescriptionException("bruh, task name cannot be empty la");
                    } else if (eventMatcher.group(3).isBlank()) {
                        throw new NoDescriptionException(
                                "bruh, start field cannot be empty la, if not go use todo");
                    } else if (eventMatcher.group(4).isBlank()) {
                        throw new NoDescriptionException(
                                "bruh, end field cannot be empty la, if not go use deadline or todo");
                    }
                    Task itemToAdd = new Event(eventMatcher.group(2), eventMatcher.group(3), eventMatcher.group(4));
                    return tasks.addListItem(itemToAdd, ui);
                };
            } else if (findMatcher.find()) {
                return (tasks, ui) -> {
                    if (findMatcher.group(2).isBlank()) {
                        throw new NoDescriptionException("please indicate what you are searching for");
                    }
                    return tasks.findTask(findMatcher.group(2), ui);
                };
            } else {
                return (tasks, ui) -> {
                    throw new NoCommandException("bruh, idk what this mean. If you are using a valid command,"
                            + " make sure to have the necessary descriptions.");
                };
            }
        }
    }
}
