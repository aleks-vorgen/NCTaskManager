package ua.edu.sumdu.j2se.kulykov.tasks;


import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {

    protected abstract Task getTask(int index);
    protected abstract int size();
    protected abstract void add(Task task);
    protected abstract boolean remove(Task task);
    protected abstract ListTypes.types getType();
    public abstract Stream<Task> getStream();

    public final AbstractTaskList incoming(int from, int to) {
        AbstractTaskList res = TaskListFactory.createTaskList(getType());
        Stream<Task> stream = getStream();
        stream.filter(task -> task.nextTimeAfter(from) > from
                && task.nextTimeAfter(to) < to).forEach(res::add);
        return res;
    }
}
