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

    /**
     * Constructs a ToDoCommand that takes a task name.
     *
     * @param taskName The name or description of the task; must not be blank.
     */
    public ToDoCommand(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Adds a new todo task to taskList.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @return a confirmation message from the addListItem call.
     * If the task name or deadline is blank.
     * @throws NoDescriptionException   If the task name is blank.
     * @throws NoCommandException       If an error occurs during command execution.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("bruh, task name cannot be empty la");
        }
        Task itemToAdd = new ToDo(this.taskName);
        return tasks.addListItem(itemToAdd, ui);
    }
}
