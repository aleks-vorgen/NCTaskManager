package ua.edu.sumdu.j2se.kulykov.tasks.models;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        if (tasks instanceof AbstractTaskList) {
            AbstractTaskList tasks1 = (AbstractTaskList) tasks;
            AbstractTaskList res = TaskListFactory.createTaskList(tasks1.getType());
            Stream<Task> stream = tasks1.getStream();

            stream.filter(task -> task.nextTimeAfter(start) != null)
                    .filter(task -> task.nextTimeAfter(start).isAfter(start))
                    .filter(task -> task.nextTimeAfter(start).isBefore(end)
                            || task.nextTimeAfter(start).equals(end)).forEach(res::add);

            return res;
        } else {
            List<Task> tasks1 = (List<Task>) tasks;
            Stream<Task> stream = tasks1.stream();
            List<Task> res = new ArrayList<>();

            stream.filter(task -> task.nextTimeAfter(start) != null)
                    .filter(task -> task.nextTimeAfter(start).isAfter(start))
                    .filter(task -> task.nextTimeAfter(start).isBefore(end)
                            || task.nextTimeAfter(start).equals(end)).forEach(res::add);
            return res;
        }
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        Set<Task> set;
        LocalDateTime current;
        for (Task task : tasks) {
            current = task.nextTimeAfter(start);
            while (current != null && !current.isAfter(end)) {
                if (sortedMap.containsKey(current))
                    sortedMap.get(current).add(task);
                else {
                    set = new HashSet<>();
                    set.add(task);
                    sortedMap.put(current, set);
                }
                current = task.nextTimeAfter(current);
            }
        }
        return sortedMap;
    }
}
