package ua.edu.sumdu.j2se.kulykov.tasks.models;

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
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private int interval;
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
        timeStart = time;
        timeEnd = time;
        interval = 0;
        isActive = true;
        isRepeated = false;
    }

    /**
     * Constructor describing the Task with title,
     * interval of action and repetition.
     * @param title title of the task
     * @param timeStart time when action starts
     * @param timeEnd time when action ends
     * @param interval task repetition interval
     */
    public Task(String title, LocalDateTime timeStart, LocalDateTime timeEnd, int interval) {
        if (timeStart == null || timeEnd == null || interval <= 0)
            throw new IllegalArgumentException();

        this.title = title;
        time = timeStart;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.interval = interval;
        isActive = true;
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
        return isRepeated ? timeStart : time;
    }

    /**
     * Method sets the time for the task and make it not repetitive.
     * @param time execution time
     */
    public void setTime(LocalDateTime time) {
        if (time == null)
            throw new IllegalArgumentException();

        this.time = time;
        this.timeStart = time;
        this.timeEnd = time;
        if (isRepeated) {
            isRepeated = false;
        }
    }

    /**
     * Method sets the time when action starts and time when action ends
     * for the not repetitive task.
     */
    public void setTime(LocalDateTime timeStart, LocalDateTime timeEnd, int interval) {
        if (timeStart == null || timeEnd == null || interval <= 0
            || timeStart.isAfter(timeEnd) || timeEnd.isBefore(timeStart))
            throw new IllegalArgumentException();

        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.interval = interval;
        if (!isRepeated) isRepeated = true;
    }

    /**
     * Method returns execution time if the task is not repetitive
     * or returns time when action starts if the task is repetitive.
     */
    public LocalDateTime getStartTime() {
        return isRepeated ? timeStart : time;
    }
    public void setStartTime(LocalDateTime timeStart) {
        if (timeStart == null || timeStart.isAfter(timeEnd))
            throw new IllegalArgumentException();

        this.timeStart = timeStart;
    }

    /**
     * Method returns execution time if the task is not repetitive
     * or returns time when action ends if the task is repetitive.
     */
    public LocalDateTime getEndTime() {
        return isRepeated ? timeEnd : time;
    }
    public void setTimeEnd(LocalDateTime timeEnd) {
        if (timeEnd == null || timeEnd.isBefore(timeStart))
            throw new IllegalArgumentException();

        this.timeEnd = timeEnd; }

    /**
     * Method returns repetition interval if the task is repetitive
     * or returns 0 if the task is not repetitive.
     */
    public int getRepeatInterval() {
        return isRepeated ? interval : 0;
    }
    public void setInterval(int interval) {
        if (interval <= 0)
            throw new IllegalArgumentException();

        this.interval = interval;
    }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    public boolean isRepeated() { return isRepeated; }
    public void setRepeated(boolean isRepeated) { this.isRepeated = isRepeated; }

    /**
     * Method returns the next task execution time.
     * If the task is not repetitive, returns -1.
     * If the next task execution time is greater than current, returns this nextTime
     * else returns -1.
     * If next time greater than task completion time, returns -1
     * else returns nextTime.
     * @param current current time
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if(!isActive) return null;

        LocalDateTime nextTime;
        if (isRepeated) {
            nextTime = timeStart;
        }
        else {
            nextTime = time;
            return nextTime.isAfter(current) ? nextTime : null;
        }

        while (nextTime.isBefore(current) || nextTime.equals(current))
            nextTime = nextTime.plusSeconds(interval);



        return nextTime.isBefore(timeEnd) || nextTime.equals(timeEnd) ? nextTime : null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", interval=" + interval +
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
                && timeStart.equals(task.timeStart)
                && timeEnd.equals(task.timeEnd)
                && interval == task.interval
                && isActive == task.isActive
                && isRepeated == task.isRepeated
                && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return time.hashCode() + timeStart.hashCode() + timeEnd.hashCode()
                + title.hashCode() + Objects.hash(isActive, isRepeated, interval);
    }
}
