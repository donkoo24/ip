package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to mark tasks.
 */
public class MarkCommand implements Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        return tasks.markTask(taskIndex, ui);
    }
}
