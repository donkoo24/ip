package lux.parser;

import java.util.Arrays;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to mark tasks.
 */
public class MarkCommand implements Command {
    private final String taskIndices;

    /**
     * Constructs a MarkCommanad with the specified task indices.
     * @param taskIndices
     */
    public MarkCommand(String taskIndices) {
        this.taskIndices = taskIndices;
    }

    /**
     * Mark tasks by parsing provided indices and marking each as completed.
     *
     * @param tasks The collection of tasks that the user has.
     * @param ui The console I/O for user input.
     * @return confirmation of all mark actions done.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) {
        int[] taskIndices = Arrays
                .stream(this.taskIndices.split(",\\s*")).mapToInt(x -> Integer.parseInt(x)).toArray();
        return tasks.massOrSingleOps(taskIndices, TaskList.MassTaskAction.MARK, ui);
    }
}
