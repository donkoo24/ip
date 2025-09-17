package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable command that represents an unrecognized user input.
 */
public class UnknownCommand implements Command {

    /**
     * Executes this command by signalling that the user input was invalid.
     *
     * @param tasks the current taskList; not used by this command
     * @param ui    the UI instance for interacting with the user; not used by this command
     * @return never returns normally; always throws an exception
     * @throws NoCommandException   If the command is not recognized
     * @throws NoDescriptionException never thrown by this implementation
     */

    @Override
    public String execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException {
        throw new NoCommandException("bruh, idk what this mean. If you are using a valid command,"
                + " make sure to have the necessary descriptions.");
    }
}
