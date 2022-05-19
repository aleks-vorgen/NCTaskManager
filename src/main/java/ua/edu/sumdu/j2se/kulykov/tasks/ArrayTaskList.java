package ua.edu.sumdu.j2se.kulykov.tasks;

import java.util.Arrays;

public class ArrayTaskList {

    private Task[] taskList;
    private int size;
    private int taskAmount;

    public ArrayTaskList() {
        size = 0;
        taskAmount = 0;
        taskList = new Task[0];
    }

    public ArrayTaskList(int size) {
        this.size = size;
        taskAmount = 0;
        taskList = new Task[size];
    }

    public Task getTask(int index) {
        return taskList[index];
    }

    public int size() {
        return taskAmount;
    }

    /**
     * Method adds the task to the task list.
     * If the number of tasks is greater than the size of array
     * the size of array is doubled.
     */
    public void add(Task task) {
        taskAmount++;

        if (taskAmount > size) {
            if (size == 0) size++;
            else size *= 2;

            Task[] temp = taskList;
            taskList = Arrays.copyOf(temp, size);
        }

        for (int i = 0; i < size; i++) {
            if (taskList[i] == null) {
                taskList[i] = task;
                break;
            }
        }
    }

    /**
     * Method removes the task from the task list.
     * The array is sorted.
     */
    public boolean remove(Task task) {
        for (int i = 0; i < size; i++) {
            if (taskList[i] == task) {
                taskList[i] = null;
                sortTaskList();
                taskAmount--;
                return true;
            }
        }

        return false;
    }

    /**
     * Method sorts the array, if the array has null element between objects.
     */
    private void sortTaskList() {
        for (int i = 0; i < taskAmount - 1; i++) {
            if (taskList[i] == null && taskList[i + 1] != null) {
                taskList[i] = taskList[i + 1];
                taskList[i + 1] = null;
            }
        }
    }

    /**
     * Method returns the subset wit tasks
     * which will be executed in a given period of time.
     */
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList res = new ArrayTaskList();
        for (int i = 0; i < taskAmount; i++) {
            if (taskList[i].isActive() && taskList[i].nextTimeAfter(from) < to && taskList[i].nextTimeAfter(from) > from) {
                res.add(taskList[i]);
            }
        }
        return res;
    }
}
