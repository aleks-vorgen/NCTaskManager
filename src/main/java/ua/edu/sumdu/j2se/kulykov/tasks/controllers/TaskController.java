package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.views.AddEditRemoveView;
import ua.edu.sumdu.j2se.kulykov.tasks.views.ShowTasksView;

import java.time.LocalDateTime;

/**
 * Controller class for adding, removing or editing tasks in the task list.
 */
public class TaskController extends Controller {
    private final ArrayTaskList taskList;
    private final AddEditRemoveView editingView;

    /**
     * Constructor which initialize taskList and editingView.
     * @param taskList list of tasks.
     * @param editingView AddEditRemoveView.
     */
    public TaskController(ArrayTaskList taskList, AddEditRemoveView editingView) {
        this.taskList = taskList;
        this.editingView = editingView;
    }

    /**
     * Method adds the task to the task list.
     */
    private void addTask() {
        String title;
        LocalDateTime time;
        LocalDateTime timeStart;
        LocalDateTime timeEnd;
        int interval;
        boolean isRepeated;
        title = editingView.getTitleInput(false);
        isRepeated = editingView.getIsRepeatedInput();

        if (isRepeated) {
            timeStart = editingView.getDateTimeInput(" start", false);
            timeEnd = editingView.getDateTimeInput(" end", false);
            interval = editingView.getIntervalInput(false);

            taskList.add(new Task(title, timeStart, timeEnd, interval));
        }
        else {
            time = editingView.getDateTimeInput("", false);

            taskList.add(new Task(title, time));
        }
        editingView.message("Task \"" + title + "\" was added successfully\n");
    }

    /**
     * Method edits the task in the task list.
     */
    private void editTask() {
        editingView.message("\n* * * Task editing * * *\n");
        Task task;
        String title;
        LocalDateTime timeStart;
        LocalDateTime timeEnd;
        LocalDateTime time;
        boolean isRepeated;
        boolean isActive;
        int interval;

        task = taskFinder("edit");

        editingView.message("Are you sure you want to edit \"" + task.getTitle() + "\" task?\n");
        if (!editingView.getYesNoInput())
            return;

        title = editingView.getTitleInput(true);
        if (title == null)
            title = task.getTitle();

        editingView.message("Your task is " +
                (task.isRepeated() ? "repetitive" : "not repetitive") +
                ". Do you want to change this?\n");
        if (editingView.getYesNoInput())
            isRepeated = !task.isRepeated();
        else
            isRepeated = task.isRepeated();

        editingView.message("Your task is " +
                (task.isActive() ? "active" : "inactive") +
                ". Do you want to change this?\n");
        if (editingView.getYesNoInput())
            isActive = !task.isActive();
        else
            isActive = task.isActive();

        if (isRepeated) {
            LocalDateTime tmpDateTime;
            int tmpInterval;
            tmpDateTime = editingView.getDateTimeInput(" start", true);
            timeStart = tmpDateTime == null ? task.getStartTime() : tmpDateTime;

            tmpDateTime = editingView.getDateTimeInput(" end", true);
            timeEnd = tmpDateTime == null ? task.getEndTime() : tmpDateTime;

            tmpInterval = editingView.getIntervalInput(true);
            interval = tmpInterval == -1 ? task.getRepeatInterval() : tmpInterval;

            task.setTitle(title);
            task.setTime(timeStart, timeEnd, interval);
        }
        else {
            LocalDateTime tmpDateTime;
            tmpDateTime = editingView.getDateTimeInput("", true);
            time = tmpDateTime == null ? task.getTime() : tmpDateTime;

            task.setTitle(title);
            task.setTime(time);
        }
        task.setActive(isActive);
        editingView.message("Task \"" + title + "\" was edited successfully\n");
    }

    /**
     * Method removes the task from the task list.
     */
    private void removeTask() {
        Task task;
        task = taskFinder("remove");
        editingView.message("Are you sure you want to remove \"" + task.getTitle() + "\" task?\n");
        if (editingView.getYesNoInput()) {
            taskList.remove(task);
            editingView.message("Task \"" + task.getTitle() + "\" was removed successfully\n");
        }
    }

    /**
     * Method finds the task in task list using entered ID of the task.
     * @param editRemove string for edit or remove task.
     * @return found task.
     */
    private Task taskFinder(String editRemove) {
        int id;
        id = editingView.getIdInput(editRemove);
        while ( id < 0 || id > taskList.size()) {
            editingView.message("Task with this id does not exists\n");
            id = editingView.getIdInput(editRemove);
        }

        return taskList.getTask(id);
    }

    public void showMenu() {
        int key;
        key = editingView.menu();
        switch (key) {
            case 1 -> add();
            case 2 -> edit();
            case 3 -> remove();
            case 4 -> toTaskListController();
        }
    }

    private void add() {
        addTask();
        showMenu();
    }

    private void edit() {
        editTask();
        showMenu();
    }

    private void remove() {
        removeTask();
        showMenu();
    }

    private void toTaskListController() {
        new TaskListController(taskList, new ShowTasksView())
                .showMenu();
    }
}
