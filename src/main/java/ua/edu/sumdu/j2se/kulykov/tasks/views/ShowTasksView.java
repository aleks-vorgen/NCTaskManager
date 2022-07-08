package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controlles.TaskListController;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ShowTasksView extends View {
    public static final LocalDateTime NOW = LocalDateTime.now();

    public static final LocalDateTime TODAY = NOW.withHour(0).withMinute(0).withSecond(0);
    public static final LocalDateTime YESTERDAY = TODAY.minusDays(1);
    public static final LocalDateTime TODAY_1H = TODAY.plusHours(1);
    public static final LocalDateTime TOMORROW = TODAY.plusDays(1);

    private ArrayTaskList taskList;
    private final TaskListController tlc = new TaskListController();

    //TODO потом удалить
    {
        taskList = new ArrayTaskList();
        taskList.add(new Task("Daily", YESTERDAY, TOMORROW, 3600*24));
        taskList.add(new Task("Hourly", TODAY, TOMORROW, 3600));
        taskList.add(new Task("Every 3 hours", TODAY_1H, TOMORROW.plusDays(1), 3*3600));
        taskList.add(new Task("Once", TOMORROW.plusDays(1).plusHours(13)));
        taskList.add(new Task("Once2", TOMORROW.plusDays(1).plusHours(15)));
    }

    public void getTaskList() {
        header();
        System.out.println("\n* * * List of tasks * * *");
        System.out.println(tlc.getTaskList(taskList));
    }

    public void getCalendar() {
        header();
        System.out.println("\n* * * Calendar * * *");
        System.out.println(tlc.getCalendar(taskList));
    }

    public void getIncoming() {
        header();
        Scanner in = new Scanner(System.in);
        LocalDateTime start;
        LocalDateTime end;
        String input;
        System.out.println("\n* * * Incoming tasks * * *");
        System.out.print("Enter start time in format yyyy-mm-dd: ");
        input = in.nextLine();
        while(true) {
            try {
                start = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.print("Enter start time in format yyyy-mm-dd: ");
                input = in.nextLine();
            }
        }

        System.out.print("Enter end time in format yyyy-mm-dd: ");
        input = in.nextLine();
        while(true) {
            try {
                end = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.print("Enter end time in format yyyy-mm-dd: ");
                input = in.nextLine();
            }
        }
        System.out.println(tlc.getIncoming(taskList, start, end));
    }

    @Override
    protected void header() {
        System.out.println("* * * * * Show tasks * * * * *");
    }
}
