package ua.edu.sumdu.j2se.kulykov.tasks.models;

public class TaskListFactory {

    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default:
                throw new NullPointerException("Nothing to create. Argument 'type' was null");
        }
    }
}
