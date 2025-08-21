import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;


public class Lux {
    private static List<Task> userList = new ArrayList<>();

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
                addListItem(userInputInfo);
            }
            userInputInfo = userInput.nextLine();
        }
    }

    private static void endConvo() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void addListItem(String item) {
        int taskId = userList.size();
        Task itemToAdd = new Task (taskId + 1, item);
        userList.add(itemToAdd);
        System.out.println("added: " + item + "\n");
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
