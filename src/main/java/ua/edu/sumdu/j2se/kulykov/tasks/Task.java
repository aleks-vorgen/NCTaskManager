package ua.edu.sumdu.j2se.kulykov.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class declared some Tasks.
 * @author Kulykov
 * @version 1.0
 */
public class Task implements Cloneable {

    private String title;
    private LocalDateTime time;
    private LocalDateTime time_start;
    private LocalDateTime time_end;
    private int time_interval_repeat;
    private boolean isActive;
    private boolean isRepeated;


    public Task() {
        super();
    }
    /**
     * Constructor describing the Task with title and time of action.
     * @param title title of the task
     * @param time execution time
     */
    public Task(String title, LocalDateTime time) {
        if (time == null)
            throw new IllegalArgumentException();

        this.title = title;
        this.time = time;
        time_start = time;
        time_end = time;
        time_interval_repeat = 0;
        isActive = false;
        isRepeated = false;
    }

    /**
     * Constructor describing the Task with title,
     * interval of action and repetition.
     * @param title title of the task
     * @param time_start time when action starts
     * @param time_end time when action ends
     * @param time_interval_repeat task repetition interval
     */
    public Task(String title, LocalDateTime time_start, LocalDateTime time_end, int time_interval_repeat) {
        if (time_start == null || time_end == null || time_interval_repeat <= 0)
            throw new IllegalArgumentException();

        this.title = title;
        time = time_start;
        this.time_start = time_start;
        this.time_end = time_end;
        this.time_interval_repeat = time_interval_repeat;
        isActive = false;
        isRepeated = true;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method returns repetition time if the task is repeated
     * or returns execution time if is.
     */
    public LocalDateTime getTime() {
        return isRepeated ? time_start : time;
    }

    /**
     * Method sets the time for the task and make it not repetitive.
     * @param time execution time
     */
    public void setTime(LocalDateTime time) {
        if (time == null)
            throw new IllegalArgumentException();

        this.time = time;
        this.time_start = time;
        this.time_end = time;
        if (isRepeated) {
            isRepeated = false;
        }
    }

    /**
     * Method sets the time when action starts and time when action ends
     * for the not repetitive task.
     */
    public void setTime(LocalDateTime time_start, LocalDateTime time_end, int time_interval_repeat) {
        if (time_start == null || time_end == null || time_interval_repeat <= 0
            || time_start.isAfter(time_end) || time_end.isBefore(time_start))
            throw new IllegalArgumentException();

        this.time_start = time_start;
        this.time_end = time_end;
        this.time_interval_repeat = time_interval_repeat;
        if (!isRepeated) isRepeated = true;
    }

    /**
     * Method returns execution time if the task is not repetitive
     * or returns time when action starts if the task is repetitive.
     */
    public LocalDateTime getStartTime() {
        return isRepeated ? time_start : time;
    }
    public void setStartTime(LocalDateTime time_start) {
        if (time_start == null || time_start.isAfter(time_end))
            throw new IllegalArgumentException();

        this.time_start = time_start;
    }

    /**
     * Method returns execution time if the task is not repetitive
     * or returns time when action ends if the task is repetitive.
     */
    public LocalDateTime getEndTime() {
        return isRepeated ? time_end : time;
    }
    public void setTime_end(LocalDateTime time_end) {
        if (time_end == null || time_end.isBefore(time_start))
            throw new IllegalArgumentException();

        this.time_end = time_end; }

    /**
     * Method returns repetition interval if the task is repetitive
     * or returns 0 if the task is not repetitive.
     */
    public int getRepeatInterval() {
        return isRepeated ? time_interval_repeat : 0;
    }
    public void setTime_interval_repeat(int time_interval_repeat) {
        if (time_interval_repeat <= 0)
            throw new IllegalArgumentException();

        this.time_interval_repeat = time_interval_repeat;
    }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    public boolean isRepeated() { return isRepeated; }
    public void setRepeated(boolean isRepeated) { this.isRepeated = isRepeated; }

    /**
     * Method returns the next task execution time.
     * If the task is not repetitive, returns -1.
     * If the next task execution time is greater than current, returns this next_time
     * else returns -1.
     * If next time greater than task completion time, returns -1
     * else returns next_time.
     * @param current current time
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if(!isActive) return null;

        LocalDateTime next_time;
        if (isRepeated) {
            next_time = time_start;
        }
        else {
            next_time = time;
            return next_time.isAfter(current) ? next_time : null;
        }

        while (next_time.isBefore(current) || next_time.equals(current))
            next_time = next_time.plusSeconds(time_interval_repeat);



        return next_time.isBefore(time_end) || next_time.equals(time_end) ? next_time : null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", time_start=" + time_start +
                ", time_end=" + time_end +
                ", time_interval_repeat=" + time_interval_repeat +
                ", isActive=" + isActive +
                ", isRepeated=" + isRepeated +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time.equals(task.time)
                && time_start.equals(task.time_start)
                && time_end.equals(task.time_end)
                && time_interval_repeat == task.time_interval_repeat
                && isActive == task.isActive
                && isRepeated == task.isRepeated
                && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return time.hashCode() + time_start.hashCode() + time_end.hashCode()
                + title.hashCode() + Objects.hash(isActive, isRepeated, time_interval_repeat);
    }
}
