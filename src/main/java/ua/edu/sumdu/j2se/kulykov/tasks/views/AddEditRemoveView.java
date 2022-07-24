package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Class for adding, editing or removing tasks in the task list using TaskController.
 */
public class AddEditRemoveView extends View {
    private static final Logger LOG = Logger.getLogger(AddEditRemoveView.class);

    /**
     * Method gets the task title input.
     * @param editing field can be empty if editing performed.
     * @return string task title.
     */
    public String getTitleInput(boolean editing) {
        String input;
        message("Enter title: ");
        input = SCANNER.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        return input;
    }

    /**
     * Method gets the task type (repetitive or not).
     * @return boolean 'yes' or 'no'.
     */
    public boolean getIsRepeatedInput() {
        messageln("Is your task will be repetitive?");
        return getYesNoInput();
    }

    /**
     * Method gets date and time input.
     * @param startEnd string for start or end date time.
     * @param editing field can be empty if editing performed.
     * @return LocalDateTime.
     */
    public LocalDateTime getDateTimeInput(String startEnd, boolean editing) {
        LocalDateTime dateTime;
        String input;
        message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
        input = SCANNER.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        while (true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                messageln(e.getMessage());
                message("Enter" + startEnd + " start date in format yyyy-mm-dd: ");
                input = SCANNER.nextLine();
            }
        }

        message("Enter" + startEnd + " time in format hh:mm : ");
        input = SCANNER.nextLine();
        while(true) {
            try {
                LocalDateTime tmpTime = LocalDateTime.parse("2000-01-01T" + input + ":00");
                dateTime = dateTime.plusHours(tmpTime.getHour());
                dateTime = dateTime.plusMinutes(tmpTime.getMinute());
                break;
            } catch (DateTimeParseException e) {
                messageln(e.getMessage());
                message("Enter" + startEnd + " time in format hh:mm : ");
                input = SCANNER.nextLine();
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
        String input;
        message("Enter interval in seconds: ");
        input = SCANNER.nextLine();
        if (editing && "".equals(input.trim()))
            return -1;

        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                messageln("This is not a number");
                message("Enter an interval in seconds: ");
                input = SCANNER.nextLine();
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
        input = SCANNER.nextLine();
        while (true) {
            try {
                id = Integer.parseInt(input) - 1;
                break;
            } catch (NumberFormatException e) {
                messageln("This is not a number");
                message("Enter an id of the task you want to " + editRemove + ": ");
                input = SCANNER.nextLine();
            }
        }
        return id;
    }

    /**
     * Method shows the menu for managing tasks.
     */
    public int menu() {
        header();
        messageln("1. Create task");
        messageln("2. Edit task");
        messageln("3. Remove task");
        messageln("4. Back to main menu");
        message("Type your choice: ");
        String choice;
        choice = SCANNER.nextLine();
        while (true) {
            int tmp = 0;
            try {
                tmp = Integer.parseInt(choice);
                if (tmp < 1 || tmp > 4)
                    throw new IllegalArgumentException("This option does not exists");
                return tmp;
            } catch (NumberFormatException e) {
                LOG.info("User entered impossible to parse to int input (" + choice + ")");
                messageln("Invalid input");
                message("Type your choice: ");
                choice = SCANNER.nextLine();
            } catch (IllegalArgumentException e) {
                LOG.info("User entered nonexistent point (" + tmp + ")");
                messageln(e.getMessage());
                message("Type your choice: ");
                choice = SCANNER.nextLine();
            }
        }
    }

    @Override
    protected void header() {
        String header = "\n* * * * * Task editor * * * * *";
        messageln(header);
    }
}
