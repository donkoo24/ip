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

    public EventCommand(String taskName, String start, String end) {
        this.taskName = taskName;
        this.start = start;
        this.end = end;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
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
