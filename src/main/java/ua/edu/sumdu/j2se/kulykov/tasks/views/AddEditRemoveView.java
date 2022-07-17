package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for adding, editing or removing tasks in the task list using TaskController.
 */
public class AddEditRemoveView extends View {
    private final Scanner in = new Scanner(System.in);

    /**
     * Method gets the task title input.
     * @param editing field can be empty if editing performed.
     * @return string task title.
     */
    public String getTitleInput(boolean editing) {
        String input;
        System.out.print("Enter title: ");
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
        String input;
        System.out.println("Is your task will be repetitive?");
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
        System.out.printf("Enter%s date in format yyyy-mm-dd: ", startEnd);
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        while (true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.printf("Enter%s start date in format yyyy-mm-dd: ", startEnd);
                input = in.nextLine();
            }
        }

        System.out.printf("Enter%s time in format hh:mm : ", startEnd);
        input = in.nextLine();
        while(true) {
            try {
                LocalDateTime tmpTime = LocalDateTime.parse("2000-01-01T" + input + ":00");
                dateTime = dateTime.plusHours(tmpTime.getHour());
                dateTime = dateTime.plusMinutes(tmpTime.getMinute());
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.printf("Enter%s time in format hh:mm : ", startEnd);
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
        System.out.print("Enter interval in seconds: ");
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return -1;

        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("This is not a number");
                System.out.print("Enter an interval in seconds: ");
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
        System.out.printf("Enter an id of the task you want to %s: ", editRemove);
        input = in.nextLine();
        while (true) {
            try {
                id = Integer.parseInt(input) - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("This is not a number");
                System.out.printf("Enter an id of the task you want to %s: ", editRemove);
                input = in.nextLine();
            }
        }
        return id;
    }

    /**
     * Method shows the menu for managing tasks.
     */
    public int menu() {
        while (true) {
            header();
            System.out.println("1. Create task");
            System.out.println("2. Edit task");
            System.out.println("3. Remove task");
            System.out.println("4. Back to main menu");
            System.out.print("Type your choice: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                default:
                    System.out.println("This option does not exist\n");
            }
        }
    }

    @Override
    protected void header() {
        String header = "\n* * * * * Task editor * * * * *";
        System.out.println(header);
    }
}
