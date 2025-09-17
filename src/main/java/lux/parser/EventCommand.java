package lux.parser;

import lux.domain.Deadline;
import lux.domain.Event;
import lux.domain.Task;
import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command that creates Event tasks.
 */
public class EventCommand implements Command {
    private final String taskName;
    private final String start;
    private final String end;

    /**
     * Constructs an EventCommand that takes the task name, start date, and end date of task.
     * @param taskName The name or description of the task; must not be blank.
     * @param start The start date of task.
     * @param end The end date of task.
     */
    public EventCommand(String taskName, String start, String end) {
        this.taskName = taskName;
        this.start = start;
        this.end = end;
    }

    /**
     * Adds a new event task to taskList.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @return a confirmation message from the addListItem call.
     * @throws NoDescriptionException   If the task name, start or end date is blank.
     * @throws NoCommandException       If an error occurs during command execution.
     * @throws IllegalArgumentException If date is not valid.
     */
    @Override
    public String execute(
            TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException, IllegalArgumentException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("bruh, task name cannot be empty la");
        } else if (this.start.isBlank()) {
            throw new NoDescriptionException(
                    "bruh, start field cannot be empty la, if not go use todo");
        } else if (this.end.isBlank()) {
            throw new NoDescriptionException(
                    "bruh, end field cannot be empty la, if not go use deadline or todo");
        }
        Task itemToAdd = new Event(this.taskName, this.start, this.end);
        return tasks.addListItem(itemToAdd, ui);
    }
}
