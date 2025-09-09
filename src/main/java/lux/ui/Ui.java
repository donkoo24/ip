package lux.ui;

import java.util.Scanner;

/**
 * Logic unit for handling user input and displaying information.
 */
public class Ui {
    private Scanner userInput;

    /**
     * Constructs a Ui and a scanner that awaits input.
     */
    public Ui() {
        this.userInput = new Scanner(System.in);
    }

    /**
     * Returns the next user input.
     *
     * @return A string that contains the next user input.
     */
    public String readline() {
        return this.userInput.nextLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param s The message to be displayed.
     */
    public void speak(String s) {
        System.out.println(s);
    }

    /**
     * Displays a greeting message to the user.
     */
    public String greet() {
        String greeting = "Hello! I'm Lux\nWhat can I do for you today?\n";
        System.out.println(greeting);
        return greeting;
    }

    /**
     * Displays a farewell message to the user.
     */
    public String endConvo() {
        String farewell = "Bye. Hope to see you again soon!";
        System.out.println(farewell);
        return farewell;
    }


}
