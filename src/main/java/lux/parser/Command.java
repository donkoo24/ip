package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Executable unit that will be returned by CommandParser.
 */
public interface Command {
    /**
     * Runs this command using the given TaskList and Ui.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @throws NoDescriptionException If required arguments like description, deadline, start, or end is missing.
     * @throws NoCommandException If user gives an unknown command.
     */
    void execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException;

    /**
     * Indicates whether to terminate programme code when a command is executed.
     *
     * @return true if application should be terminated, false otherwise.
     */
    default boolean isExit() {
        return false;
    }
}
