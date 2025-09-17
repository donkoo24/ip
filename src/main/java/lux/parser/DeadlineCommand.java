package lux.parser;

import lux.domain.Deadline;
import lux.domain.Task;
import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command that creates Deadline tasks.
 */
public class DeadlineCommand implements Command {
    private final String taskName;
    private final String deadline;

    /**
     * Constructs a DeadlineCommand with the specified task name and deadline.
     *
     * @param taskName The name or description of the task; must not be blank.
     * @param deadline The deadline string for the task; must not be blank.
     */
    public DeadlineCommand(String taskName, String deadline) {
        this.taskName = taskName;
        this.deadline = deadline;
    }

    /**
     * Adds a new deadline task to taskList.
     *
     * @param tasks The taskList to which the new task will be added; must not be null.
     * @param ui     The UI handler for user interaction; must not be null.
     * @return a confirmation message from the addListItem call.
     * @throws NoDescriptionException   If the task name or deadline is blank.
     * @throws NoCommandException       If an error occurs during command execution.
     * @throws IllegalArgumentException If date is not valid.
     */

    @Override
    public String execute(
            TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException, IllegalArgumentException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("bruh, task name cannot be empty la");
        } else if (this.deadline.isBlank()) {
            throw new NoDescriptionException("bruh, deadline cannot be empty la, if not go use todo");
        }
        Task itemToAdd = new Deadline(this.taskName, this.deadline);
        return tasks.addListItem(itemToAdd, ui);
    }
}
