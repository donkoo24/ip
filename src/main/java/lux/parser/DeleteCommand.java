package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to delete tasks.
 */
public class DeleteCommand implements Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        return tasks.deleteTask(taskIndex, ui);
    }
}
