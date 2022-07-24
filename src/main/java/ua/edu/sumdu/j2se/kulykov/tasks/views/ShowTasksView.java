package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for showing task list in selected format using TaskListController.
 */
public class ShowTasksView extends View {
    private static final Logger LOG = Logger.getLogger(ShowTasksView.class);
    private final Scanner in = new Scanner(System.in);

    /**
     * Method gets the date input.
     * @param startEnd string for start or end date.
     * @return LocalDateTime.
     */
    public LocalDateTime getDateInput(String startEnd) {
        Scanner in = new Scanner(System.in);
        LocalDateTime dateTime;
        String input;
        message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
        input = in.nextLine();
        while(true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                message(e.getMessage() + '\n');
                message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
                input = in.nextLine();
            }
        }

        return dateTime;
    }

    /**
     * Method shows the menu for showing task list in different formats.
     */
    public int menu() {
        header();
        message("1. Show all tasks\n");
        message("2. Show calendar of tasks\n");
        message("3. Show incoming tasks\n");
        message("4. Clear task list\n");
        message("5. Task editor\n");
        message("6. Task writer\n");
        message("7. Exit\n");
        message("Type your choice: ");
        String choice;
        choice = in.nextLine();
        while (true) {
            int tmp = 0;
            try {
                tmp = Integer.parseInt(choice);
                if (tmp < 1 || tmp > 7)
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
        message("\n* * * * * Show tasks * * * * *\n");
    }
}
