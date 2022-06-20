package ua.edu.sumdu.j2se.kulykov.tasks;


import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {

    protected abstract Task getTask(int index);
    protected abstract int size();
    protected abstract void add(Task task);
    protected abstract boolean remove(Task task);
    protected abstract ListTypes.types getType();
    public abstract Stream<Task> getStream();
}
