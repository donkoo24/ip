import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Lux {
    private static List<String> userList = new ArrayList<>();

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
                for (int i = 0; i < userList.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, userList.get(i));
                }
                System.out.println();
            } else {
                userList.add(userInputInfo);
                System.out.println("added: " + userInputInfo + "\n");
            }
            userInputInfo = userInput.nextLine();
        }
    }

    private static void endConvo() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
