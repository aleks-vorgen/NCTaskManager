package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for showing task list in selected format using TaskListController.
 */
public class ShowTasksView extends View {

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
        System.out.printf("Enter%s date in format yyyy-mm-dd: ", startEnd);
        input = in.nextLine();
        while(true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.printf("Enter%s date in format yyyy-mm-dd: ", startEnd);
                input = in.nextLine();
            }
        }

        return dateTime;
    }

    /**
     * Method shows the menu for showing task list in different formats.
     */
    public int menu() {
        while(true) {
            header();
            System.out.println("1. Show all tasks");
            System.out.println("2. Show calendar of tasks");
            System.out.println("3. Show incoming tasks");
            System.out.println("4. Clear task list");
            System.out.println("5. Task editor");
            System.out.println("6. Task writer");
            System.out.println("7. Exit");
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
                case "5":
                    return 5;
                case "6":
                    return 6;
                case "7":
                    return 7;
                default:
                    System.out.println("This option does not exist\n");
            }
        }
    }

    @Override
    protected void header() {
        System.out.println("\n* * * * * Show tasks * * * * *");
    }
}
