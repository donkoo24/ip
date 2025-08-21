import java.util.Scanner;

public class Lux {
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
            System.out.printf("%s\n\n", userInputInfo);
            userInputInfo = userInput.nextLine();
        }
    }

    private static void endConvo() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
