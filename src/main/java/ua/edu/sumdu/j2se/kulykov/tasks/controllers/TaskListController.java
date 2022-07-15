package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Tasks;
import ua.edu.sumdu.j2se.kulykov.tasks.views.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

/**
 * Controller for showing task list in selected format.
 */
public class TaskListController extends Controller {
    private final ArrayTaskList tasks = Main.taskList;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    /**
     * Method shows all tasks in task list.
     * @return task list.
     */
    public String getTaskList() {
        if (tasks.size() == 0)
            return "\nNothing to show\n";

        StringBuilder str = new StringBuilder();
        int counter = 0;
        for (Task task : tasks) {
            buildString(str, counter, task);
            counter++;
        }
        return str.toString();
    }

    /**
     * Method shows all tasks in task list as calendar.
     * @return task list as calendar.
     */
    public String getCalendar() {
        if (tasks == null || tasks.size() == 0)
            return "\nNothing to show\n";

        LocalDateTime min = tasks.getTask(0).getStartTime();
        LocalDateTime max = tasks.getTask(0).getStartTime();
        for (Task task : tasks) {
            if (min.compareTo(task.getStartTime()) > 0)
                min = task.getStartTime();
            if (max.compareTo(task.getStartTime()) < 0)
                max = task.getStartTime();
        }
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(tasks, min, max);

        if (calendar.isEmpty())
            return "\nNothing to show\n";

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
        return str.toString();
    }

    /**
     * Method shows incoming tasks in task list.
     * @param start start date time.
     * @param end end date time.
     * @return incoming tasks as task list.
     */
    public String getIncoming(LocalDateTime start, LocalDateTime end) {
        if (tasks == null || tasks.size() == 0)
            return "\nNothing to show\n";

        ArrayTaskList incoming = (ArrayTaskList) Tasks.incoming(tasks, start, end);
        if (incoming.size() == 0)
            return "\nNothing to show\n";

        StringBuilder str = new StringBuilder();
        int counter = 0;
        for (Task task : incoming) {
            buildString(str, counter, task);
            counter++;
        }

        return str.toString();
    }

    /**
     * Method clears the task list.
     */
    public void clear() {
        tasks.getStream().forEach(tasks::remove);
    }

    /**
     * Method builds a string to show it in view.
     * @param str building string.
     * @param counter tasks id to show as list.
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
}
