package lux.parser;

import java.util.Arrays;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to delete tasks.
 */
public class DeleteCommand implements Command {
    private final String taskIndices;

    public DeleteCommand(String taskIndices) {
        this.taskIndices = taskIndices;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        int[] taskIndices = Arrays.stream(this.taskIndices.split(",\\s*")).mapToInt(x -> Integer.parseInt(x)).toArray();
        return tasks.massOrSingleOps(taskIndices, TaskList.MassTaskAction.DELETE, ui);
    }
}
