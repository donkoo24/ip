package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command to list tasks.
 */
public class ListCommand implements Command {

    /**
     * Lists all tasks in taskList.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @return a display of all tasks in taskList.
     * @throws NoCommandException If an error occurs during command execution.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        return tasks.showList(ui);
    }
}
