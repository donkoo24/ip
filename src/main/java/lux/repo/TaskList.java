package lux.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lux.domain.Task;
import lux.ui.Ui;

public class TaskList {
    private static List<Task> taskList = new ArrayList<>();

    public TaskList() {}

    public void addListItem(Task t, Ui ui) {
        taskList.add(t);
        ui.speak("Got it. I've added this task:\n"
                + t.toString()
                + "\n"
                + "Now you have "
                + Task.getNumberOfTasks()
                + " task in the list"
                + "\n");
    }

    public int getSize() {
        return taskList.size();
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    public List<Task> getList() {
        return taskList;
    }

    public void showList(Ui ui) {
        ui.speak("Here are the tasks in your list");
        for (int i = 0; i < taskList.size(); i++) {
            String message = String.format("%d. %s", i + 1, taskList.get(i));
            System.out.println(message);
        }
        System.out.print("\n");
    }

    public void markTask(int taskNumber, Ui ui) {
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            return;
        } else {
            Task actionTask = taskList.get(taskNumber - 1);
            actionTask.markCompleted();
            ui.speak("Nice! I've marked this task as done:\n" + actionTask.toString() + "\n");
        }
    }

    public void unmarkTask(int taskNumber, Ui ui) {
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            return;
        } else {
            Task actionTask = taskList.get(taskNumber - 1);
            actionTask.unmarkCompleted();
            ui.speak("Ok, I've marked this task as not done yet:\n" + actionTask.toString() + "\n");
        }
    }

    public void deleteTask(int taskNumber, Ui ui) {
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            return;
        } else {
            Task removedTask = taskList.get(taskNumber - 1);
            taskList.remove(taskNumber - 1);
            Task.reduceTaskCount();
            ui.speak("Noted, I've removed this task:\n"
                    + removedTask.toString()
                    + "\n"
                    + "Now you have "
                    + Task.getNumberOfTasks()
                    + " task in the list"
                    + "\n");
        }
    }

    public void findTask(String taskName, Ui ui) {
        Stream<Task> temp = taskList.stream();
        List<Task> possibleTasks = temp.filter(x -> x.getTaskName().toLowerCase().contains(taskName)).toList();

        ui.speak("Here are the matching tasks in your list:");
        for (int i = 0; i < possibleTasks.size(); i++) {
            String message = String.format("%d. %s", i + 1, possibleTasks.get(i));
            System.out.println(message);
        }
        System.out.print("\n");
    }
}
