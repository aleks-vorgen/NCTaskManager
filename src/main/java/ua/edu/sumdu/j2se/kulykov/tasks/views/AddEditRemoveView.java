package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for adding, editing or removing tasks in the task list using TaskController.
 */
public class AddEditRemoveView extends View {

    /**
     * Method gets the task title input.
     * @param editing field can be empty if editing performed.
     * @return string task title.
     */
    public String getTitleInput(boolean editing) {
        Scanner sc = new Scanner(System.in);
        String input;
        message("Enter title: ");
        if (editing) {
            input = sc.nextLine();
            if ("".equals(input.trim()))
                return null;
        }
        else {
            do {
                while (!sc.hasNextLine()) {
                    messageln("Invalid input");
                    message("Enter title: ");
                    sc.next();
                }
                input = sc.nextLine();
            } while ("".equals(input));
        }
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
        Scanner sc = new Scanner(System.in);
        LocalDateTime dateTime;
        String input;
        message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
        input = sc.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        while (true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                messageln(e.getMessage());
                message("Enter" + startEnd + " start date in format yyyy-mm-dd: ");
                input = sc.nextLine();
            }
        }

        message("Enter" + startEnd + " time in format hh:mm : ");
        input = sc.nextLine();
        while(true) {
            try {
                LocalDateTime tmpTime = LocalDateTime.parse("2000-01-01T" + input + ":00");
                dateTime = dateTime.plusHours(tmpTime.getHour());
                dateTime = dateTime.plusMinutes(tmpTime.getMinute());
                break;
            } catch (DateTimeParseException e) {
                messageln(e.getMessage());
                message("Enter" + startEnd + " time in format hh:mm : ");
                input = sc.nextLine();
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
        Scanner sc = new Scanner(System.in);
        int input;
        message("Enter interval in seconds: ");
        if (editing) {
            input = sc.nextInt();
            if ("".equals(Integer.toString(input).trim()))
                return -1;
        }
        else {
            while (!sc.hasNextInt()) {
                messageln("This is not a number");
                message("Enter interval in seconds: ");
                sc.next();
            }
            input = sc.nextInt();
        }

        return input;
    }

    /**
     * Method gets the ID of the task being searched for.
     * @param editRemove string for edit or remove task
     * @return int task id in the task list.
     */
    public int getIdInput(String editRemove) {
        Scanner sc = new Scanner(System.in);
        int id;
        message("Enter an id of the task you want to " + editRemove + ": ");
        while (!sc.hasNextInt()) {
            messageln("This is not a number");
            message("Enter an id of the task you want to " + editRemove + ": ");
            sc.next();
        }
        id = sc.nextInt();

        return id - 1;
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

        return getChoiceInput();
    }

    @Override
    protected void header() {
        String header = "\n* * * * * Task editor * * * * *";
        messageln(header);
    }
}
