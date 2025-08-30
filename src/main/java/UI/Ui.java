package UI;

import java.util.Scanner;

public class Ui {
    private Scanner userInput;

    public Ui() {
        this.userInput = new Scanner(System.in);
    }

    public String readline() {
        return this.userInput.nextLine();
    }

    public void speak(String s){
        System.out.println(s + "\n");
    }

    public void greet() {
        System.out.println("Hello! I'm Lux\nWhat can I do for you today?\n");
    }

    public void endConvo() {
        System.out.println("Bye. Hope to see you again soon!");
    }


}
