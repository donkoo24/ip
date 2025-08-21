import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;


public class Lux {
    private static List<Task> userList = new ArrayList<>();
    private final static Pattern MARKPATTERN = Pattern.compile("(^mark) (\\d+)", Pattern.CASE_INSENSITIVE);
    private final static Pattern UNMARKPATTERN = Pattern.compile("^(unmark) (\\d+)", Pattern.CASE_INSENSITIVE);
    private final static Pattern TODOPATTERN = Pattern.compile("(todo) (.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern DEADLINEPATTERN = Pattern.compile("(deadline) (.*)\\s/by\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern EVENTPATTERN = Pattern.compile("(event) (.*)\\s/from\\s(.*)\\s/to\\s(.*)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        greet();
        handleConvo();
        endConvo();
    }

    private static void greet() {
        System.out.println("Hello! I'm Lux\nWhat can I do for you?\n");
    }

    private static void handleConvo() {
        Scanner userInput = new Scanner(System.in);
        String userInputInfo = userInput.nextLine();



        while (!userInputInfo.equalsIgnoreCase("bye")) {
            if (userInputInfo.equalsIgnoreCase("list")) {
                showList();
            } else {
                Matcher markMatcher = MARKPATTERN.matcher(userInputInfo);
                Matcher unmarkMatcher = UNMARKPATTERN.matcher(userInputInfo);
                if (markMatcher.find()) {
                    markTask(Integer.parseInt(markMatcher.group(2)));
                } else if (unmarkMatcher.find()) {
                    unmarkTask(Integer.parseInt(unmarkMatcher.group(2)));
                } else {
                    addListItem(userInputInfo);
                }
            }
            userInputInfo = userInput.nextLine();
        }
    }

    private static void endConvo() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void addListItem(String item) {
        Matcher toDoMatcher = TODOPATTERN.matcher(item);
        Matcher deadlineMatcher = DEADLINEPATTERN.matcher(item);
        Matcher eventMatcher = EVENTPATTERN.matcher(item);

        if (toDoMatcher.find()) {
            Task itemToAdd = new ToDo(toDoMatcher.group(2));
            userList.add(itemToAdd);
            System.out.println("Got it. I've added this task:\n" + itemToAdd.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list" + "\n");
        } else if (deadlineMatcher.find()) {
            Task itemToAdd = new Deadline(deadlineMatcher.group(2), deadlineMatcher.group(3));
            userList.add(itemToAdd);
            System.out.println("Got it. I've added this task:\n" + itemToAdd.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list"+ "\n");
        } else if (eventMatcher.find()) {
            Task itemToAdd = new Event(eventMatcher.group(2), eventMatcher.group(3), eventMatcher.group(4));
            userList.add(itemToAdd);
            System.out.println("Got it. I've added this task:\n" + itemToAdd.toString() + "\n" + "Now you have " + Task.getNumberOfTasks() + " task in the list"+ "\n");
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
}
