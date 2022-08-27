package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * View class for writing or reading json files.
 */
public class IOView extends View {
    private static final Logger LOG = Logger.getLogger(IOView.class);

    /**
     * Method gets the name of file input.
     * @return string name of file.
     */
    public String getFileNameInput(){
        Scanner sc = new Scanner(System.in);
        String input;
        message("Enter the name of the file: ");
        input = sc.nextLine().trim();
        boolean valid = false;
        while (!valid) {
            while ("".equals(input)) {
                messageln("Field was empty");
                message("Enter the name of the file: ");
                input = sc.nextLine().trim();
            }
            valid = true;
            for (char c : input.toCharArray()) {
                if (c == ' ') {
                    valid = false;
                    messageln("The name of the file can`t contains spaces");
                    message("Enter the name of the file: ");
                    input = sc.nextLine().trim();
                    break;
                }
            }
        }

        while (true) {
            try {
                if ("".equals(input))
                    throw new IllegalArgumentException("Field was empty");
                for (char c : input.toCharArray()) {
                    if (c == ' ')
                        throw new IllegalArgumentException("The name of the file can`t contains spaces");
                }
                break;
            } catch (IllegalArgumentException e) {
                messageln(e.getMessage());
                message("Enter the name of the file: ");
                input = sc.nextLine().trim();
            }
        }
        return input;
    }

    /**
     * Method shows the menu for managing files.
     */
    public int menu() {
        header();
        messageln("1. Save task list to text file");
        messageln("2. Upload task list from text file");
        messageln("3. Back to main menu");
        message("Type your choice: ");
        String choice;
        Scanner sc = new Scanner(System.in);
        choice = sc.nextLine();
        while (true) {
            int tmp = 0;
            try {
                tmp = Integer.parseInt(choice);
                if (tmp < 1 || tmp > 3)
                    throw new IllegalArgumentException("This option does not exists");
                return tmp;
            } catch (NumberFormatException e) {
                LOG.info("User entered impossible to parse to int input (" + choice + ")");
                messageln("Invalid input");
                message("Type your choice: ");
                choice = sc.nextLine();
            } catch (IllegalArgumentException e) {
                LOG.info("User entered nonexistent point (" + tmp + ")");
                messageln(e.getMessage());
                message("Type your choice: ");
                choice = sc.nextLine();
            }
        }
    }

    @Override
    protected void header() {
        messageln("\n* * * * * Task writer * * * * *");
    }
}
