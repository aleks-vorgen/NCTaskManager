package ua.edu.sumdu.j2se.kulykov.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends AbstractTaskList {
    private Node first;
    private int taskAmount;

    /**
     * Method return the required task
     * @param index index of the required task
     * @return Task or null if the task was not found
     */
    @Override
    public Task getTask(int index) {
        if (index > size())
            throw new IndexOutOfBoundsException("Index is outside the list size");

        Node currentTask = first;
        Task taskFromList;

        for (int i = 0; i <= index; i++) {
            taskFromList = currentTask.task;
            if (i == index)
                return taskFromList;
            currentTask = currentTask.linkToNext;
        }

        return null;
    }

    @Override
    public int size() {
        return taskAmount;
    }

    /**
     * Method adds the task to the end of the linked task list.
     * @param task task which should be added.
     */
    @Override
    public void add(Task task) {
        if (task == null)
            throw new NullPointerException("You can`t add a null element to the list");

        if (first == null) {
            first = new Node(task, null);
        } else {
            Node currentTask = first;
            while (currentTask.linkToNext != null)
                currentTask = currentTask.linkToNext;
            currentTask.linkToNext = new Node(task, null);
        }
        taskAmount++;
    }

    /**
     * Method removes the task from the linked task list.
     * @param task task which should be removed.
     * @return true if the task was removed or false if not.
     */
    @Override
    public boolean remove(Task task) {
        if (task == null)
            throw new NullPointerException("Your value was null");
        
        if (first == null) return false;
        else if (first.task.equals(task)) {
            first = first.linkToNext;
            taskAmount--;
            return true;
        } else {
            Node currentTask = first;
            while (currentTask.linkToNext != null) {
                if (currentTask.linkToNext.task.equals(task)) {
                    currentTask.linkToNext = currentTask.linkToNext.linkToNext;
                    taskAmount--;
                    return true;
                    }
                currentTask = currentTask.linkToNext;
                }
            }
        return false;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private Node current = first;
            private int nextIndex;

            @Override
            public boolean hasNext() {
                return nextIndex < taskAmount;
            }

            @Override
            public Task next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                Node lastReturned = current;
                current = current.linkToNext;
                nextIndex++;
                return lastReturned.task;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("You can`t remove items throw iterator");
            }
        };
    }

    private static class Node {
        Task task;
        Node linkToNext;

        private Node(Task task, Node next) {
            this.task = task;
            this.linkToNext = next;
        }
    }
}
