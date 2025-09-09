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

    public FindCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        if (this.taskName.isBlank()) {
            throw new NoDescriptionException("please indicate what you are searching for");
        }
        return tasks.findTask(this.taskName, ui);
    }
}
