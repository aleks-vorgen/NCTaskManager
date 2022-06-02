package ua.edu.sumdu.j2se.kulykov.tasks;

/**
 * Class declared some Tasks.
 * @author Kulykov
 * @version 1.0
 */
public class Task {

    private String title;
    private int time;
    private int time_start;
    private int time_end;
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
    public Task(String title, int time) {
        if (time < 0)
            throw new IllegalArgumentException("Task cannot take negative time");

        this.title = title;
        this.time = time;
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
    public Task(String title, int time_start, int time_end, int time_interval_repeat) {
        if (time_start < 0 || time_end < 0 || time_interval_repeat <= 0)
            throw new IllegalArgumentException("Task cannot take negative time. Interval must be greater than 0");

        this.title = title;
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
    public int getTime() {
        if (isRepeated) return time_start;
        else return time;
    }

    /**
     * Method sets the time for the task and make it not repetitive.
     * @param time execution time
     */
    public void setTime(int time) {
        if (time < 0)
            throw new IllegalArgumentException("Task cannot take negative time.");

        this.time = time;
        if (isRepeated) {
            isRepeated = false;
        }
    }

    /**
     * Method sets the time when action starts and time when action ends
     * for the not repetitive task.
     */
    public void setTime(int time_start, int time_end, int time_interval_repeat) {
        if (time_start < 0 || time_end < 0 || time_interval_repeat <= 0)
            throw new IllegalArgumentException("Task cannot take negative time. Interval must be greater than 0");

        this.time_start = time_start;
        this.time_end = time_end;
        this.time_interval_repeat = time_interval_repeat;
        if (!isRepeated) isRepeated = true;
    }

    /**
     * Method returns execution time if the task is not repetitive
     * or returns time when action starts if the task is repetitive.
     */
    public int getStartTime() {
        return isRepeated ? time_start : time;
    }
    public void setStartTime(int time_start) { this.time_start = time_start; }

    /**
     * Method returns execution time if the task is not repetitive
     * or returns time when action ends if the task is repetitive.
     */
    public int getEndTime() {
        return isRepeated ? time_end : time;
    }
    public void setTime_end(int time_end) { this.time_end = time_end; }

    /**
     * Method returns repetition interval if the task is repetitive
     * or returns 0 if the task is not repetitive.
     */
    public int getRepeatInterval() {
        return isRepeated ? time_interval_repeat : 0;
    }
    public void setTime_interval_repeat(int time_interval_repeat) {
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
    public int nextTimeAfter(int current) {
        if(!isActive) return -1;

        int next_time;
        if (isRepeated) {
            next_time = time_start;
        }
        else {
            next_time = time;
            return next_time > current ? next_time : -1;
        }

        while (next_time <= current)
            next_time += time_interval_repeat;

        return next_time < time_end ? next_time : -1;
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
}
