package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Tasks;
import ua.edu.sumdu.j2se.kulykov.tasks.views.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

/**
 * Controller for showing task list in selected format.
 */
public class TaskListController extends Controller {
    private final ArrayTaskList taskList;
    private ShowTasksView stv;
    private static final Logger log = Logger.getLogger("ProgramStartsAppender");
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public TaskListController(ArrayTaskList taskList) {
        this.taskList = taskList;
    }

    public TaskListController(ArrayTaskList taskList, ShowTasksView stv) {
        this.taskList = taskList;
        this.stv = stv;
    }

    /**
     * Method shows all tasks in task list.
     */
    private void getTaskList() {
        stv.message("\n* * * List of tasks * * *\n");
        if (taskList.size() == 0) {
            stv.message("\nNothing to show\n");
            return;
        }

        StringBuilder str = new StringBuilder();
        int counter = 0;
        for (Task task : taskList) {
            buildString(str, counter, task);
            counter++;
        }
        stv.message(str.toString());
    }

    /**
     * Method shows all tasks in task list as calendar.
     */
    private void getCalendar() {
        stv.message("\n* * * Calendar * * *\n");
        if (taskList == null || taskList.size() == 0) {
            stv.message("\nNothing to show\n");
            return;
        }

        LocalDateTime min = taskList.getTask(0).getStartTime();
        LocalDateTime max = taskList.getTask(0).getStartTime();
        for (Task task : taskList) {
            if (min.compareTo(task.getStartTime()) > 0)
                min = task.getStartTime();
            if (max.compareTo(task.getStartTime()) < 0)
                max = task.getStartTime();
        }
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(taskList, min, max);

        if (calendar.isEmpty()) {
            stv.message("\nNothing to show\n");
            return;
        }

        StringBuilder str = new StringBuilder();
        int counter = 0;
        for (LocalDateTime date : calendar.keySet()) {
            String dateFormatted = date.format(format);
            str.append("Date: ").append(dateFormatted).append('\n');
            for (Task task : calendar.get(date)) {
                buildString(str, counter, task);
                counter++;
            }
        }
        stv.message(str.toString());
    }

    /**
     * Method shows incoming tasks in task list.
     */
    public void getIncoming() {
        stv.message("\n* * * Incoming tasks * * *\n");
        if (taskList == null || taskList.size() == 0) {
            stv.message("\nNothing to show\n");
            return;
        }

        LocalDateTime start = stv.getDateInput(" start");
        LocalDateTime end = stv.getDateInput(" end");
        ArrayTaskList incoming = (ArrayTaskList) Tasks.incoming(taskList, start, end);
        if (incoming.size() == 0) {
            stv.message("\nNothing to show\n");
            return;
        }

        StringBuilder str = new StringBuilder();
        int counter = 0;
        for (Task task : incoming) {
            buildString(str, counter, task);
            counter++;
        }

        stv.message(str.toString());
    }

    /**
     * Method clears the task list.
     */
    private void clear() {
        stv.message("Are you sure you want to clear task list?\n");
        if (stv.getYesNoInput()) {
            taskList.getStream().forEach(taskList::remove);
            stv.message("Task list was cleared successfully\n");
        }
    }

    /**
     * Method builds a string to show it in view.
     * @param str building string.
     * @param counter tasks id to show them as list.
     * @param task task that converts to string.
     */
    private void buildString(StringBuilder str, int counter, Task task) {
        str.append("  ").append(counter + 1).append(". Title: ").append(task.getTitle()).append('\n');
        if (task.isRepeated()) {
            str.append("     Start time: ").append(task.getStartTime().format(format)).append('\n');
            str.append("     End time: ").append(task.getEndTime().format(format)).append('\n');
            str.append("     Interval: ").append(task.getRepeatInterval() / 3600)
                    .append(task.getRepeatInterval() / 3600 == 1 ? " hour" : " hours").append('\n');
        }
        else {
            str.append("     Time: ").append(task.getTime().format(format)).append('\n');
        }
        str.append("     Is active: ").append(task.isActive() ? "active" : "inactive").append("\n\n");
    }

    @Override
    public void showMenu() {
        int key;
        key = stv.menu();
        switch (key) {
            case 1 -> showTaskList();
            case 2 -> calendar();
            case 3 -> incoming();
            case 4 -> clearList();
            case 5 -> toTaskController();
            case 6 -> toIOController();
            case 7 -> finish();
            default -> nonexistentPoint(stv);
        }
    }

    private void showTaskList() {
        getTaskList();
        showMenu();
    }

    private void calendar() {
        getCalendar();
        showMenu();
    }

    private void incoming() {
        getIncoming();
        showMenu();
    }

    private void clearList() {
        clear();
        showMenu();
    }

    private void toTaskController() {
        new TaskController(taskList, new AddEditRemoveView())
                .showMenu();
    }

    private void toIOController() {
        new IOController(taskList, new IOView())
                .showMenu();
    }

    private void finish() {
        new IOController(taskList)
                .writeBin();
        log.info("Program was finished with exit code 0");
    }
}
