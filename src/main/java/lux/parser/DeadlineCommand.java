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

    public DeadlineCommand(String taskName, String deadline) {
        this.taskName = taskName;
        this.deadline = deadline;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("bruh, task name cannot be empty la");
        } else if (this.deadline.isBlank()) {
            throw new NoDescriptionException("bruh, deadline cannot be empty la, if not go use todo");
        }
        Task itemToAdd = new Deadline(this.taskName, this.deadline);
        return tasks.addListItem(itemToAdd, ui);
    }
}
