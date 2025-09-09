package lux.repo;

import java.util.ArrayList;
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
        taskList.add(t);
        String reply = "Got it. I've added this task:\n"
                + t.toString()
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
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            return "Invalid Number";
        } else {
            Task actionTask = taskList.get(taskNumber - 1);
            actionTask.markCompleted();
            String reply = "Nice! I've marked this task as done:\n" + actionTask + "\n";
            ui.speak(reply);
            return reply;
        }
    }

    /**
     * Unmarks the specified task as uncompleted.
     *
     * @param taskNumber The index of task to be unmarked.
     * @param ui The Ui instance to notify user of task unmarking.
     */
    public String unmarkTask(int taskNumber, Ui ui) {
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            return "Invalid Number";
        } else {
            Task actionTask = taskList.get(taskNumber - 1);
            actionTask.unmarkCompleted();
            String reply = "Ok, I've marked this task as not done yet:\n" + actionTask + "\n";
            ui.speak(reply);
            return reply;
        }
    }

    /**
     * Deletes the specified task.
     *
     * @param taskNumber The index of task to delete.
     * @param ui The Ui instance to notify user of task deletion.
     */
    public String deleteTask(int taskNumber, Ui ui) {
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            return "Invalid Number";
        } else {
            Task removedTask = taskList.get(taskNumber - 1);
            taskList.remove(taskNumber - 1);
            Task.reduceTaskCount();
            String reply = "Noted, I've removed this task:\n"
                    + removedTask.toString()
                    + "\n"
                    + "Now you have "
                    + Task.getNumberOfTasks()
                    + " task in the list"
                    + "\n";
            ui.speak(reply);
            return reply;
        }
    }

    /**
     * Filters the list of tasks that contains the given keyword and displays it.
     *
     * @param taskName The keyword to search for.
     * @param ui The Ui instance to notify user of matching tasks.
     */
    public String findTask(String taskName, Ui ui) {
        Stream<Task> temp = taskList.stream();
        List<Task> possibleTasks = temp.filter(x -> x.getTaskName().toLowerCase().contains(taskName)).toList();
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
