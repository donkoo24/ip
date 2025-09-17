package lux.parser;

import java.util.Arrays;

import lux.repo.TaskList;
import lux.ui.Ui;

/**
 * Executable command to unmark tasks.
 */
public class UnmarkCommand implements Command {
    private final String taskIndices;

    public UnmarkCommand(String taskIndices) {
        this.taskIndices = taskIndices;
    }

    /**
     * Unmark tasks by parsing provided indices and marking each as incomplete.
     *
     * @param tasks The taskList containing tasks to unmark; must not be null
     * @param ui    The UI instance for user feedback; must not be null
     * @return a status message from the TaskList after performing the unmark action
     */

    @Override
    public String execute(TaskList tasks, Ui ui) {
        int[] taskIndices = Arrays.stream(this.taskIndices.split(",\\s*")).mapToInt(x -> Integer.parseInt(x)).toArray();
        return tasks.massOrSingleOps(taskIndices, TaskList.MassTaskAction.UNMARK, ui);
    }
}
