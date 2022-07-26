package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for showing task list in selected format using TaskListController.
 */
public class ShowTasksView extends View {

    /**
     * Method gets the date input.
     * @param startEnd string for start or end date.
     * @return LocalDateTime.
     */
    public LocalDateTime getDateInput(String startEnd) {
        Scanner sc = new Scanner(System.in);
        LocalDateTime dateTime;
        String input;
        message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
        input = sc.nextLine();
        while(true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                messageln(e.getMessage());
                message("Enter" + startEnd + " date in format yyyy-mm-dd: ");
                input = sc.nextLine();
            }
        }

        return dateTime;
    }

    /**
     * Method shows the menu for showing task list in different formats.
     */
    public int menu() {
        header();
        messageln("1. Show all tasks");
        messageln("2. Show calendar of tasks");
        messageln("3. Show incoming tasks");
        messageln("4. Clear task list");
        messageln("5. Task editor");
        messageln("6. Task writer");
        messageln("7. Exit");

        return getChoiceInput();
    }

    @Override
    protected void header() {
        messageln("\n* * * * * Show tasks * * * * *");
    }
}
