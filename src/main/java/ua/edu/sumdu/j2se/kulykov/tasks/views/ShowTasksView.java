package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.controllers.IOController;
import ua.edu.sumdu.j2se.kulykov.tasks.controllers.TaskListController;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class for showing task list in selected format using TaskListController.
 */
public class ShowTasksView extends View {
    private static final Logger log = Logger.getLogger("ProgramStartsAppender");
    private final TaskListController tlc = new TaskListController();
    private final Scanner in = new Scanner(System.in);

    /**
     * Method shows all tasks in task list using TaskListController.
     */
    private void getTaskList() {
        System.out.println("\n* * * List of tasks * * *");
        System.out.println(tlc.getTaskList());
    }

    /**
     * Method shows all tasks in task list as calendar using TaskListController.
     */
    private void getCalendar() {
        System.out.println("\n* * * Calendar * * *");
        System.out.println(tlc.getCalendar());
    }

    /**
     * Method shows incoming tasks in task list using TaskListController.
     */
    private void getIncoming() {
        LocalDateTime start;
        LocalDateTime end;
        String input;
        System.out.println("\n* * * Incoming tasks * * *");
        System.out.print("Enter start date in format yyyy-mm-dd: ");
        input = in.nextLine();
        while(true) {
            try {
                start = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.print("Enter start date in format yyyy-mm-dd: ");
                input = in.nextLine();
            }
        }

        System.out.print("Enter end date in format yyyy-mm-dd: ");
        input = in.nextLine();
        while(true) {
            try {
                end = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.print("Enter end date in format yyyy-mm-dd: ");
                input = in.nextLine();
            }
        }
        System.out.println(tlc.getIncoming(start, end));
    }

    /**
     * Method clears the task list using TaskListController.
     */
    private void clearList() {
        System.out.println("\nAre you sure you want to clear the task list?");
        System.out.println("Type 'yes' or 'no': ");
        String input;
        input = in.nextLine();
        while (!"yes".equals(input) && !"no".equals(input)) {
            System.out.println("Invalid input");
            System.out.print("Type 'yes' or 'no': ");
            input = in.nextLine();
        }
        if ("yes".equals(input))
            tlc.clear();
    }

    /**
     * Method shows the menu for showing task list in different formats.
     */
    protected void menu() {
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
                    getTaskList();
                    break;
                case "2":
                    getCalendar();
                    break;
                case "3":
                    getIncoming();
                    break;
                case "4":
                    clearList();
                    break;
                case "5":
                    AddEditRemoveView addEditRemoveView = new AddEditRemoveView();
                    addEditRemoveView.menu();
                    break;
                case "6":
                    IOView inOutView = new IOView();
                    inOutView.menu();
                    break;
                case "7":
                    new IOController().writeBin();
                    in.close();
                    log.info("Program was finished with exit code 0");
                    return;
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
