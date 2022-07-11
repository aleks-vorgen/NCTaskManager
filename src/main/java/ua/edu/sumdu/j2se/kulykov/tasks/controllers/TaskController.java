package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.views.Main;

import java.time.LocalDateTime;

public class TaskController extends Controller {
    private final ArrayTaskList taskList = Main.taskList;

    public Task getTask(int id) {
        return taskList.getTask(id);
    }

    public void addTask(String title, LocalDateTime time) {
        taskList.add(new Task(title, time));
    }

    public void addTask(String title, LocalDateTime timeStart, LocalDateTime timeEnd, int interval) {
        taskList.add(new Task(title, timeStart, timeEnd, interval));
    }

    public void editTask(String title, LocalDateTime time_start, LocalDateTime time_end,
                                  int time_interval_repeat, boolean isActive, int id) {
        Task task = taskList.getTask(id);
        task.setTitle(title);
        task.setTime(time_start, time_end, time_interval_repeat);
        task.setActive(isActive);
    }

    public void editTask(String title, LocalDateTime time, boolean isActive, int id) {
        Task task = taskList.getTask(id);
        task.setTitle(title);
        task.setTime(time);
        task.setActive(isActive);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }
}
