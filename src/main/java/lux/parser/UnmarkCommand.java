package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to unmark tasks.
 */
public class UnmarkCommand implements Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        return tasks.unmarkTask(taskIndex, ui);
    }
}
