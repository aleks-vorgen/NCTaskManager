package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.views.Main;

import java.time.LocalDateTime;

/**
 * Controller for adding, removing or editing tasks in the task list.
 */
public class TaskController extends Controller {
    private final ArrayTaskList taskList = Main.taskList;

    /**
     * Method returns the task.
     * @param id task id in the task list.
     * @return Task.
     */
    public Task getTask(int id) {
        return taskList.getTask(id);
    }

    /**
     * Method adds the not repetitive task to the task list using Task constructor.
     * @param title task name.
     * @param time task execution time.
     */
    public void addTask(String title, LocalDateTime time) {
        taskList.add(new Task(title, time));
    }

    /**
     * Method adds the repetitive task to the task list using Task constructor.
     * @param title task name.
     * @param timeStart task execution start time.
     * @param timeEnd task execution end time.
     * @param interval interval at which the task is executed.
     */
    public void addTask(String title, LocalDateTime timeStart, LocalDateTime timeEnd, int interval) {
        taskList.add(new Task(title, timeStart, timeEnd, interval));
    }

    /**
     * Method edits the repetitive task in the task list.
     * @param title task name.
     * @param time_start task execution start time.
     * @param time_end task execution end time.
     * @param time_interval_repeat interval at which the task is executed.
     * @param isActive task is active or not.
     * @param id task id in the task list.
     */
    public void editTask(String title, LocalDateTime time_start, LocalDateTime time_end,
                                  int time_interval_repeat, boolean isActive, int id) {
        Task task = taskList.getTask(id);
        task.setTitle(title);
        task.setTime(time_start, time_end, time_interval_repeat);
        task.setActive(isActive);
    }

    /**
     * Method edits the not repetitive task in the task list.
     * @param title task name.
     * @param time task execution time.
     * @param isActive task is active or not.
     * @param id task id in the task list.
     */
    public void editTask(String title, LocalDateTime time, boolean isActive, int id) {
        Task task = taskList.getTask(id);
        task.setTitle(title);
        task.setTime(time);
        task.setActive(isActive);
    }

    /**
     * Method removes the task from the task list.
     * @param task removing task.
     */
    public void removeTask(Task task) {
        taskList.remove(task);
    }
}
