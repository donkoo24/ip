package lux.parser;

import java.util.Arrays;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to delete tasks.
 */
public class DeleteCommand implements Command {
    private final String taskIndices;

    /**
     * Constructs a DeleteCommand with the specified indices.
     * @param taskIndices a comma-separated string of task indices.
     */
    public DeleteCommand(String taskIndices) {
        this.taskIndices = taskIndices;
    }

    /**
     * Deletes specified tasks in the taskList.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @return a confirmation or error message for task deletion.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) {
        int[] taskIndices = Arrays
                .stream(this.taskIndices.split(",\\s*")).mapToInt(x -> Integer.parseInt(x)).toArray();
        return tasks.massOrSingleOps(taskIndices, TaskList.MassTaskAction.DELETE, ui);
    }
}
