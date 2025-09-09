package lux.parser;

import lux.domain.Task;
import lux.domain.ToDo;
import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command that creates ToDo tasks.
 */
public class ToDoCommand implements Command {
    private final String taskName;

    public ToDoCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("bruh, task name cannot be empty la");
        }
        Task itemToAdd = new ToDo(this.taskName);
        return tasks.addListItem(itemToAdd, ui);
    }
}
