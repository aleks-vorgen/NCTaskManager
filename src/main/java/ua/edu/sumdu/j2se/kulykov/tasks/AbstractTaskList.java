package ua.edu.sumdu.j2se.kulykov.tasks;


public abstract class AbstractTaskList implements Iterable<Task> {

    protected abstract Task getTask(int index);
    protected abstract int size();
    protected abstract void add(Task task);
    protected abstract boolean remove(Task task);
    protected abstract ListTypes.types getType();

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList res = TaskListFactory.createTaskList(getType());
        for (int i = 0; i < size(); i++) {
            if (getTask(i).isActive() && getTask(i).nextTimeAfter(from) < to && getTask(i).nextTimeAfter(from) > from) {
                res.add(getTask(i));
            }
        }
        return res;
    }
}
