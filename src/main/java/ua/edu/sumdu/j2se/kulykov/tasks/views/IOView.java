package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * View class for writing or reading json files.
 */
public class IOView extends View {
    private static final Logger LOG = Logger.getLogger(IOView.class);
    private final Scanner in = new Scanner(System.in);

    /**
     * Method gets the name of file input.
     * @return string name of file.
     */
    public String getFileNameInput(){
        String input;
        message("Enter the name of the file: ");
        input = in.nextLine().trim();
        while (true) {
            try {
                if ("".equals(input))
                    throw new IllegalArgumentException("Field was empty\n");
                for (char c : input.toCharArray()) {
                    if (c == ' ')
                        throw new IllegalArgumentException("The name of the file can`t contains spaces\n");
                }
                break;
            } catch (IllegalArgumentException e) {
                message(e.getMessage());
                message("Enter the name of the file: ");
                input = in.nextLine().trim();
            }
        }
        return input;
    }

    /**
     * Method shows the menu for managing files.
     */
    public int menu() {
        header();
        message("1. Save task list to text file\n");
        message("2. Upload task list from text file\n");
        message("3. Back to main menu\n");
        message("Type your choice: ");
        String choice;
        choice = in.nextLine();
        while (true) {
            int tmp = 0;
            try {
                tmp = Integer.parseInt(choice);
                if (tmp < 1 || tmp > 3)
                    throw new IllegalArgumentException("This option does not exists");
                return tmp;
            } catch (NumberFormatException e) {
                LOG.info("User entered impossible to parse to int input (" + choice + ")");
                message("Invalid input\n");
                message("Type your choice: ");
                choice = in.nextLine();
            } catch (IllegalArgumentException e) {
                LOG.info("User entered nonexistent point (" + tmp + ")");
                message(e.getMessage() + '\n');
                message("Type your choice: ");
                choice = in.nextLine();
            }
        }
    }

    @Override
    protected void header() {
        message("\n* * * * * Task writer * * * * *\n");
    }
}
