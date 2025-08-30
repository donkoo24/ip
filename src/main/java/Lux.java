import UI.Ui;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lux {
    private static List<Task> userList = new ArrayList<>();
    private final static Pattern MARK_PATTERN = Pattern.compile("(^mark)\\s(\\d+)", Pattern.CASE_INSENSITIVE);
    private final static Pattern UNMARK_PATTERN = Pattern.compile("^(unmark)\\s(\\d+)", Pattern.CASE_INSENSITIVE);
    private final static Pattern TODO_PATTERN = Pattern.compile("(todo)\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern DEADLINE_PATTERN = Pattern.compile("(deadline)\\s(.*)\\s/by\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern EVENT_PATTERN = Pattern.compile("(event)\\s(.*)\\s/from\\s(.*)\\s/to\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern DELETE_PATTERN = Pattern.compile("(delete)\\s(.*)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws IOException {
        Ui ui = new Ui();

        ui.greet();
        loadTask();
        handleConvo(ui);
        endConvo(ui);
    }

    /*private static void greet() {
        System.out.println("Hello! I'm Lux\nWhat can I do for you?\n");
    }*/

    private static void handleConvo(Ui ui) {
        String userInputInfo = ui.readline();

        while (!userInputInfo.equalsIgnoreCase("bye")) {
            if (userInputInfo.equalsIgnoreCase("list")) {
                showList();
            } else {
                Matcher markMatcher = MARK_PATTERN.matcher(userInputInfo);
                Matcher unmarkMatcher = UNMARK_PATTERN.matcher(userInputInfo);
                Matcher deleteMatcher = DELETE_PATTERN.matcher(userInputInfo);
                if (markMatcher.find()) {
                    markTask(Integer.parseInt(markMatcher.group(2)));
                } else if (unmarkMatcher.find()) {
                    unmarkTask(Integer.parseInt(unmarkMatcher.group(2)));
                } else if (deleteMatcher.find()) {
                    deleteTask(Integer.parseInt(deleteMatcher.group(2)));
                }

                else {
                    try {
                        addListItem(userInputInfo);
                    } catch (NoDescriptionException e) {
                        ui.println(e.getMessage());
                    } catch (NoCommandException e) {
                        ui.println(e.getMessage());
                    }
                }
            }
            userInputInfo = ui.readline();
        }
    }

    private static void endConvo(Ui ui) {
        StringBuilder saveData = new StringBuilder();

        for (int i = 0; i < userList.size(); i++) {
            saveData.append(userList.get(i)).append(System.lineSeparator());
        }

        try {
            SaveFileManager.updateSaveFile(saveData.toString());
        } catch (IOException e) {
            System.out.println("Did not manage to save task data"  + e.getMessage());
        }

        ui.endConvo();
    }

    private static void addListItem(String item) throws NoDescriptionException, NoCommandException {
        Matcher toDoMatcher = TODO_PATTERN.matcher(item);
        Matcher deadlineMatcher = DEADLINE_PATTERN.matcher(item);
        Matcher eventMatcher = EVENT_PATTERN.matcher(item);

        if (toDoMatcher.find()) {
            if (toDoMatcher.group(2).isBlank()) {
                throw new NoDescriptionException("bruh, task name cannot be empty la");
            }
            Task itemToAdd = new ToDo(toDoMatcher.group(2));
            userList.add(itemToAdd);
            System.out.println("Got it. I've added this task:\n" + itemToAdd.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list" + "\n");
        } else if (deadlineMatcher.find()) {
            if (deadlineMatcher.group(2).isBlank()) {
                throw new NoDescriptionException("bruh, task name cannot be empty la");
            } else if (deadlineMatcher.group(3).isBlank()) {
                throw new NoDescriptionException("bruh, deadline cannot be empty la, if not go use todo");
            }

            Task itemToAdd = new Deadline(deadlineMatcher.group(2), deadlineMatcher.group(3));
            userList.add(itemToAdd);
            System.out.println("Got it. I've added this task:\n" + itemToAdd.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list"+ "\n");
        } else if (eventMatcher.find()) {
            if (eventMatcher.group(2).isBlank()) {
                throw new NoDescriptionException("bruh, task name cannot be empty la");
            } else if (eventMatcher.group(3).isBlank()) {
                throw new NoDescriptionException("bruh, start field cannot be empty la, if not go use todo");
            } else if (eventMatcher.group(4).isBlank()) {
                throw new NoDescriptionException("bruh, end field cannot be empty la, if not go use deadline or todo");
            }
            Task itemToAdd = new Event(eventMatcher.group(2), eventMatcher.group(3), eventMatcher.group(4));
            userList.add(itemToAdd);
            System.out.println("Got it. I've added this task:\n" + itemToAdd.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list"+ "\n");
        } else {
            throw new NoCommandException("bruh, idk what this mean. If you are using a valid command, make sure to have the necessary descriptions.");
        }

    }

    private static void showList() {
        System.out.println("Here are the tasks in your list");
        for (int i = 0; i < userList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, userList.get(i));
        }
        System.out.println();
    }

    private static void markTask(int taskNumber) {
        if (taskNumber > userList.size() || taskNumber <= 0) {
            return;
        } else {
            Task actionTask = userList.get(taskNumber - 1);
            actionTask.markCompleted();
            System.out.println("Nice! I've marked this task as done:\n" + actionTask.toString() + "\n");
        }
    }

    private static void unmarkTask(int taskNumber) {
        if (taskNumber > userList.size() || taskNumber <= 0) {
            return;
        } else {
            Task actionTask = userList.get(taskNumber - 1);
            actionTask.unmarkCompleted();
            System.out.println("Ok, I've marked this task as not done yet:\n" +  actionTask.toString() + "\n");
        }

    }

    private static void deleteTask(int taskNumber) {
        if (taskNumber > userList.size() || taskNumber <= 0) {
            return;
        } else {
            Task removedTask = userList.get(taskNumber - 1);
            userList.remove(taskNumber - 1);
            Task.reduceTaskCount();
            System.out.println("Noted, I've removed this task:\n" + removedTask.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list"+ "\n");
        }
    }

    private static void loadTask() throws IOException {
        SaveFileManager.getOrCreateSaveFile();
        SaveFileManager.loadData(userList);
    }
}
