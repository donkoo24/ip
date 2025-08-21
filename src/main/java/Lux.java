import java.util.Scanner;

public class Lux {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Hello! I'm Lux\nWhat can I do for you?\n");

        String userInputInfo = userInput.nextLine();

        while (!userInputInfo.equalsIgnoreCase("bye")) {
            System.out.printf("%s\n\n", userInputInfo);
            userInputInfo = userInput.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
