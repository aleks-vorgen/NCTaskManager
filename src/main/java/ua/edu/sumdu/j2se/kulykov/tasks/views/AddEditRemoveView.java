package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for adding, editing or removing tasks in the task list using TaskController.
 */
public class AddEditRemoveView extends View {
    private static final Logger LOG = Logger.getLogger(AddEditRemoveView.class);
    private final Scanner in = new Scanner(System.in);

    /**
     * Method gets the task title input.
     * @param editing field can be empty if editing performed.
     * @return string task title.
     */
    public String getTitleInput(boolean editing) {
        String input;
        message("Enter title: ");
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        return input;
    }

    /**
     * Method gets the task type (repetitive or not).
     * @return boolean 'yes' or 'no'.
     */
    public boolean getIsRepeatedInput() {
        message("Is your task will be repetitive?\n");
        return getYesNoInput();
    }

    /**
     * Method gets date and time input.
     * @param startEnd string for start or end date time.
     * @param editing field can be empty if editing performed.
     * @return LocalDateTime.
     */
    public LocalDateTime getDateTimeInput(String startEnd, boolean editing) {
        Scanner in = new Scanner(System.in);
        LocalDateTime dateTime;
        String input;
        message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        while (true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                message(e.getMessage() + '\n');
                message("Enter" + startEnd + " start date in format yyyy-mm-dd: ");
                input = in.nextLine();
            }
        }

        message("Enter" + startEnd + " time in format hh:mm : ");
        input = in.nextLine();
        while(true) {
            try {
                LocalDateTime tmpTime = LocalDateTime.parse("2000-01-01T" + input + ":00");
                dateTime = dateTime.plusHours(tmpTime.getHour());
                dateTime = dateTime.plusMinutes(tmpTime.getMinute());
                break;
            } catch (DateTimeParseException e) {
                message(e.getMessage() + '\n');
                message("Enter" + startEnd + " time in format hh:mm : ");
                input = in.nextLine();
            }
        }
        return dateTime;
    }

    /**
     * Method gets the interval at which the task will be repeated.
     * @param editing field can be empty if editing performed.
     * @return int interval in seconds.
     */
    public int getIntervalInput(boolean editing) {
        Scanner in = new Scanner(System.in);
        String input;
        message("Enter interval in seconds: ");
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return -1;

        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                message("This is not a number\n");
                message("Enter an interval in seconds: ");
                input = in.nextLine();
            }
        }
    }

    /**
     * Method gets the ID of the task being searched for.
     * @param editRemove string for edit or remove task
     * @return int task id in the task list.
     */
    public int getIdInput(String editRemove) {
        int id;
        String input;
        message("Enter an id of the task you want to " + editRemove + ": ");
        input = in.nextLine();
        while (true) {
            try {
                id = Integer.parseInt(input) - 1;
                break;
            } catch (NumberFormatException e) {
                message("This is not a number\n");
                message("Enter an id of the task you want to " + editRemove + ": ");
                input = in.nextLine();
            }
        }
        return id;
    }

    /**
     * Method shows the menu for managing tasks.
     */
    public int menu() {
        header();
        message("1. Create task\n");
        message("2. Edit task\n");
        message("3. Remove task\n");
        message("4. Back to main menu\n");
        message("Type your choice: ");
        String choice;
        choice = in.nextLine();
        while (true) {
            int tmp = 0;
            try {
                tmp = Integer.parseInt(choice);
                if (tmp < 1 || tmp > 4)
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
        String header = "\n* * * * * Task editor * * * * *\n";
        message(header);
    }
}
