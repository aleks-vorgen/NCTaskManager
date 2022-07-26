package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.util.Scanner;

/**
 * Abstract view.
 */
public abstract class View {

    protected static final Scanner SCANNER = new Scanner(System.in);

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
        String input;
        do {
            message("Type 'yes' or 'no': ");
            while (!SCANNER.hasNextLine()) {
                messageln("Invalid input");
                message("Type 'yes' or 'no': ");
                SCANNER.next();
            }
            input = SCANNER.nextLine();
        } while (!"yes".equals(input) && !"no".equals(input));

        return "yes".equals(input);
    }
}
