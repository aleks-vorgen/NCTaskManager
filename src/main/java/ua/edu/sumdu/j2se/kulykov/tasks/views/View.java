package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.util.Scanner;

/**
 * Abstract view.
 */
public abstract class View {

    /**
     * Abstract method for showing views headers.
     */
    protected abstract void header();

    /**
     * Method prints message.
     * @param msg msg to print.
     */
    public void message(String msg) {
        System.out.print(msg);
    }

    /**
     * Method prints message with new line.
     * @param msg msg to print.
     */
    public void messageln(String msg) {
        System.out.println(msg);
    }

    /**
     * Method gets yes/no input.
     * @return 'yes' or 'no' string.
     */
    public boolean getYesNoInput() {
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            message("Type 'yes' or 'no': ");
            while (!sc.hasNextLine()) {
                messageln("Invalid input");
                message("Type 'yes' or 'no': ");
                sc.next();
            }
            input = sc.nextLine();
        } while (!"yes".equals(input) && !"no".equals(input));

        return "yes".equals(input);
    }

    public int getChoiceInput() {
        Scanner sc = new Scanner(System.in);
        message("Type your choice: ");
        while (!sc.hasNextInt()) {
            messageln("This is not a number");
            message("Type your choice: ");
            sc.next();
        }

        return sc.nextInt();
    }
}
