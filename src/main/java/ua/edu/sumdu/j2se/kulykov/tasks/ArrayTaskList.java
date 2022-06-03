package ua.edu.sumdu.j2se.kulykov.tasks;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayTaskList extends AbstractTaskList {

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

    @Override
    public Task getTask(int index) {
        if (index > size)
            throw new IndexOutOfBoundsException("Index is outside the list size");

        return taskList[index];
    }

    @Override
    public int size() {
        return taskAmount;
    }

    /**
     * Method adds the task to the task list.
     * If the number of tasks is greater than the size of array
     * the size of array is doubled.
     */
    @Override
    public void add(Task task) {
        if (task == null)
            throw new NullPointerException("You can`t add a null element to the list");

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
    @Override
    public boolean remove(Task task) {
        for (int i = 0; i < size; i++) {
            if (taskList[i].equals(task)) {
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

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < taskAmount && taskList[index] != null;
            }

            @Override
            public Task next() {
                return taskList[index++];
            }
        };
    }
}
