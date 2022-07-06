package ua.edu.sumdu.j2se.kulykov.tasks.controlles;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Tasks;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class TaskListController extends Controller {

    public ArrayTaskList createTaskList() {
        return new ArrayTaskList();
    }

    public ArrayTaskList createTaskList(Task[] tasks) {
        ArrayTaskList taskList = new ArrayTaskList(tasks.length);
        for (Task task : tasks) {
            taskList.add(task);
        }
        return taskList;
    }

    public String getTaskList(ArrayTaskList tasks) {
        if (tasks == null || tasks.size() == 0)
            return "Список пуст";
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            str.append(i + 1).append(". Title: ").append(tasks.getTask(i).getTitle()).append('\n');
            if (tasks.getTask(i).isRepeated()) {
                str.append("   Start time: ").append(tasks.getTask(i).getStartTime()).append('\n');
                str.append("   End time: ").append(tasks.getTask(i).getEndTime()).append('\n');
                str.append("   Interval: ").append(tasks.getTask(i).getRepeatInterval()).append('\n');
            }
            else {
                str.append("   Time: ").append(tasks.getTask(i).getTime()).append('\n');
            }
            str.append("   Is active: ").append(tasks.getTask(i).isActive() ? "active" : "inactive").append('\n');
        }
        return str.toString();
    }

    public String getCalendar(ArrayTaskList tasks) {
        LocalDateTime min = tasks.getTask(0).getStartTime();
        LocalDateTime max = tasks.getTask(0).getStartTime();
        for (Task task : tasks) {
            if (min.compareTo(task.getStartTime()) > 0)
                min = task.getStartTime();
            if (max.compareTo(task.getStartTime()) < 0)
                max = task.getStartTime();
        }
        //TODO не возвращаются таски
        SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(tasks, min, max);
        return map.toString();
    }
}
