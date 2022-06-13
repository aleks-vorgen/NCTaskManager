package ua.edu.sumdu.j2se.kulykov.tasks;

import java.util.*;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Cloneable {

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
    protected ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }

    @Override
    public Stream<Task> getStream() { return Stream.of(thisToArray()); }

    public Task[] thisToArray() {
        Task[] res = new Task[taskAmount];

        for (int i = 0; i < taskAmount; i++)
            res[i] = getTask(i);

        return res;
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
            int index;
            Task task;
            @Override
            public boolean hasNext() {
                return index < taskAmount;
            }

            @Override
            public Task next() {
                task = getTask(index);
                index++;
                return task;
            }

            @Override
            public void remove() {
                if (index < 0 || task == null)
                    throw new IllegalStateException();
                ArrayTaskList.this.remove(task);
                index--;
            }
        };
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "taskList=" + Arrays.toString(taskList) +
                ", size=" + size +
                ", taskAmount=" + taskAmount +
                '}';
    }

    @Override
    public Object clone() {
        try {
            ArrayTaskList clone = (ArrayTaskList) super.clone();
            clone.taskList = Arrays.copyOf(taskList, size);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList taskList1 = (ArrayTaskList) o;
        return size == taskList1.size && taskAmount == taskList1.taskAmount && Arrays.equals(taskList, taskList1.taskList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, taskAmount);
        result = 31 * result + Arrays.hashCode(taskList);
        return result;
    }
}
