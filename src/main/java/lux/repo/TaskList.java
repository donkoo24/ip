package lux.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import lux.domain.Task;
import lux.ui.Ui;

/**
 * Manages a list of Task objects.
 * Has operations for modifying and displaying the tasks and task related information.
 */
public class TaskList {
    private static List<Task> taskList = new ArrayList<>();

    /**
     * Represents set of possible mass operation.
     */
    public enum MassTaskAction {
        DELETE,
        MARK,
        UNMARK
    }


    /**
     * Constructs a TaskList.
     */
    public TaskList() {}

    /**
     * Adds a task to the list and notifies user.
     *
     * @param t The task object to add.
     * @param ui The Ui instance to notify user.
     */
    public String addListItem(Task t, Ui ui) {
        assert t != null : "t cannot be null";
        taskList.add(t);
        String reply = "Got it. I've added this task:\n"
                + t
                + "\n"
                + "Now you have "
                + Task.getNumberOfTasks()
                + " task in the list"
                + "\n";
        ui.speak(reply);
        return reply;
    }

    /**
     * Returns the size of current list of tasks.
     *
     * @return The number of current tasks in the list.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Returns the ith task object in the list of tasks.
     *
     * @param i The position in the list of tasks.
     * @return The task object.
     */
    public Task getTask(int i) {
        assert i >= 0 && i < taskList.size() : "i cannot be out of bounds of array list";
        return taskList.get(i);
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of Task objects.
     */
    public List<Task> getList() {
        return taskList;
    }

    /**
     * Displays all tasks in the list.
     *
     * @param ui The Ui instance to display the tasks.
     */
    public String showList(Ui ui) {
        StringBuilder reply = new StringBuilder("Here are the tasks in your list" + "\n");
        ui.speak(String.valueOf(reply));
        for (int i = 0; i < taskList.size(); i++) {
            String message = String.format("%d. %s", i + 1, taskList.get(i));
            ui.speak(message);
            reply.append(String.format("\n" + "%s", message + "\n"));
        }
        return reply.toString();
    }

    /**
     * Marks the specified task as completed.
     *
     * @param taskNumber The index of task to be marked.
     * @param ui The Ui instance to notify user of task marking.
     */
    public String markTask(int taskNumber, Ui ui) {
        assert taskNumber >= 0 && taskNumber < taskList.size() : "taskNumber cannot be out of bounds";
        Task actionTask = taskList.get(taskNumber - 1);
        assert actionTask != null : "actionTask cannot be null";
        actionTask.markCompleted();
        return taskNumber + ". " + actionTask + "\n";
    }

    /**
     * Unmarks the specified task as uncompleted.
     *
     * @param taskNumber The index of task to be unmarked.
     * @param ui The Ui instance to notify user of task unmarking.
     */
    public String unmarkTask(int taskNumber, Ui ui) {
        assert taskNumber >= 0 && taskNumber < taskList.size() : "taskNumber cannot be out of bounds";
        Task actionTask = taskList.get(taskNumber - 1);
        assert actionTask != null : "actionTask cannot be null";
        actionTask.unmarkCompleted();
        return taskNumber + ". " + actionTask + "\n";
    }

    /**
     * Applies an operation on all indicated tasks.
     * This operation is limited to mark, unmark and delete.
     * This works for a single task.
     *
     * @param tasksToAct The array of tasks to act on.
     * @param mta The action type of operation.
     * @param ui The UI instance to notify user of mass operation.
     * @return The notification to user about operation.
     */

    public String massOrSingleOps(int[] tasksToAct, MassTaskAction mta, Ui ui) {
        StringBuilder reply = new StringBuilder();
        StringBuilder actionTaskReply = new StringBuilder();
        int taskListSize = taskList.size();
        tasksToAct = Arrays
                .stream(tasksToAct)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(x -> Integer.valueOf(x))
                .toArray();

        for (int taskNumber : tasksToAct) {
            if (taskNumber > taskListSize || taskNumber <= 0) {
                reply.append(taskNumber).append(" is an invalid number, no action taken.\n");
            } else {
                Task actionTask = taskList.get(taskNumber - 1);
                assert actionTask != null : "removedTask cannot be null";

                switch (mta) {
                case DELETE:
                    actionTaskReply.append(this.deleteTask(taskNumber, ui));
                    break;
                case MARK:
                    actionTaskReply.append(this.markTask(taskNumber, ui));
                    break;
                case UNMARK:
                    actionTaskReply.append(this.unmarkTask(taskNumber, ui));
                    break;
                default:
                    break;
                }
            }
        }
        reply = new StringBuilder("Noted, I've completed the following commands:\n"
                + actionTaskReply
                + "\n"
                + "Now you have "
                + Task.getNumberOfTasks()
                + " task in the list"
                + "\n");
        ui.speak(reply.toString());
        return reply.toString();
    }

    /**
     * Deletes the specified task.
     *
     * @param taskNumber The index of task to be deleted.
     * @param ui The Ui instance to notify user of task deletion.
     */
    public String deleteTask(int taskNumber, Ui ui) {
        assert taskNumber >= 0 && taskNumber < taskList.size() : "taskNumber cannot be out of bounds";
        Task removedTask = taskList.get(taskNumber - 1);
        assert removedTask != null : "removedTask cannot be null";
        taskList.remove(taskNumber - 1);
        Task.reduceTaskCount();
        return taskNumber + ". " + removedTask + "\n";
    }

    /**
     * Filters the list of tasks that contains the given keyword and displays it.
     *
     * @param taskName The keyword to search for.
     * @param ui The Ui instance to notify user of matching tasks.
     */
    public String findTask(String taskName, Ui ui) {
        Stream<Task> taskStream = taskList.stream();
        List<Task> possibleTasks = taskStream.filter(x -> x.getTaskName().toLowerCase().contains(taskName)).toList();
        StringBuilder reply = new StringBuilder("Here are the matching tasks in your list:" + "\n");

        ui.speak(reply.toString());
        for (int i = 0; i < possibleTasks.size(); i++) {
            String message = String.format("%d. %s", i + 1, possibleTasks.get(i));
            ui.speak(message);
            reply.append(String.format("\n" + "%s", message + "\n"));
        }
        return reply.toString();
    }
}
