package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command that creates finds tasks.
 */
public class FindCommand implements Command {
    private final String taskName;

    /**
     * Constructs a FindCommand that takes a task name.
     *
     * @param taskName The name or description of the task; must not be blank
     */
    public FindCommand(String taskName) {
        this.taskName = taskName.stripTrailing().stripLeading();
    }

    /**
     * Finds tasks that matches String passed and display all matches.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @return a display of all tasks that matches.
     * @throws NoDescriptionException If no taskName indicated.
     * @throws NoCommandException If an error occurs during command execution.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("please indicate what you are searching for");
        }
        return tasks.findTask(this.taskName, ui);
    }
}
