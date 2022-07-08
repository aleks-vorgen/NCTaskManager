package ua.edu.sumdu.j2se.kulykov.tasks.controlles;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

public class TaskListController extends Controller {

    public String getTaskList(ArrayTaskList tasks) {
        if (tasks == null || tasks.size() == 0)
            return "\nNothing to show\n";

        StringBuilder str = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (int i = 0; i < tasks.size(); i++) {
            str.append(i + 1).append(". Title: ").append(tasks.getTask(i).getTitle()).append('\n');
            if (tasks.getTask(i).isRepeated()) {
                str.append("   Start time: ").append(tasks.getTask(i).getStartTime().format(format)).append('\n');
                str.append("   End time: ").append(tasks.getTask(i).getEndTime().format(format)).append('\n');
                str.append("   Interval: ").append(tasks.getTask(i).getRepeatInterval() / 3600)
                        .append(tasks.getTask(i).getRepeatInterval() / 3600 == 1 ? " hour" : " hours").append('\n');
            }
            else {
                str.append("   Time: ").append(tasks.getTask(i).getTime().format(format)).append('\n');
            }
            str.append("   Is active: ").append(tasks.getTask(i).isActive() ? "active" : "inactive").append("\n\n");
        }
        return str.toString();
    }

    public String getCalendar(ArrayTaskList tasks) {
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        int counter = 0;
        for (LocalDateTime date : calendar.keySet()) {
            String dateFormatted = date.format(format);
            str.append("Date: ").append(dateFormatted).append('\n');
            for (Task task : calendar.get(date)) {
                buildString(str, counter, task, format);
                counter++;
            }
        }
        return str.toString();
    }

    public String getIncoming(ArrayTaskList tasks, LocalDateTime start, LocalDateTime end) {
        if (tasks == null || tasks.size() == 0)
            return "\nNothing to show\n";

        ArrayTaskList incoming = (ArrayTaskList) Tasks.incoming(tasks, start, end);
        if (incoming.size() == 0)
            return "\nNothing to show\n";

        StringBuilder str = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        int counter = 0;
        for (Task task : incoming) {
            buildString(str, counter, task, format);
            counter++;
        }

        return str.toString();
    }

    private void buildString(StringBuilder str, int counter, Task task, DateTimeFormatter format) {
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
