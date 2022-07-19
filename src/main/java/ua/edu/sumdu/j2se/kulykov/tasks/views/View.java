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
     * Method gets yes/no input.
     * @return 'yes' or 'no' string.
     */
    public boolean getYesNoInput() {
        Scanner in = new Scanner(System.in);
        String input;
        message("Type 'yes' or 'no': ");
        input = in.nextLine();
        while (!"yes".equals(input) && !"no".equals(input)) {
            message("Invalid input\n");
            message("Type 'yes' or 'no': ");
            input = in.nextLine();
        }
        return "yes".equals(input);
    }
}
