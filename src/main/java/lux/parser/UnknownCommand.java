package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command that creates finds tasks.
 */
public class UnknownCommand implements Command {

    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        throw new NoCommandException("bruh, idk what this mean. If you are using a valid command,"
                + " make sure to have the necessary descriptions.");
    }
}
