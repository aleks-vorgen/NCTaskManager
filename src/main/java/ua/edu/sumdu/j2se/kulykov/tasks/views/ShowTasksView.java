package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controllers.TaskListController;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ShowTasksView extends View {

    private final TaskListController tlc = new TaskListController();
    private final Scanner in = new Scanner(System.in);

    private void getTaskList() {
        System.out.println("\n* * * List of tasks * * *");
        System.out.println(tlc.getTaskList());
    }

    private void getCalendar() {
        System.out.println("\n* * * Calendar * * *");
        System.out.println(tlc.getCalendar());
    }

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

    protected void menu() {
        while(true) {
            header();
            System.out.println("1. Show all tasks");
            System.out.println("2. Show calendar of tasks");
            System.out.println("3. Show incoming tasks");
            System.out.println("4. Task editing");
            System.out.println("5. Exit");
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
                    AddEditView addEditView = new AddEditView();
                    addEditView.menu();
                    break;
                case "5":
                    in.close();
                    return;
                default:
                    System.out.println("This option does not exist\n");
            }
        }
    }

    @Override
    protected void header() {
        System.out.println("* * * * * Show tasks * * * * *");
    }
}
