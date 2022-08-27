package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Notificator extends Thread {
    private final ArrayTaskList taskList;
    private final ShowTasksView stv;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
    private static final Logger log = Logger.getLogger(Notificator.class);
    private final static int DELAY = 30000;
    private static final int MIN_5 = 5;
    private static final int MIN_1 = 1;

    public Notificator(ArrayTaskList taskList, ShowTasksView stv) {
        this.taskList = taskList;
        this.stv = stv;
        setDaemon(true);
    }

    @Override
    public void run() {
        String old = null;
        while (true) {
            try {
                if (taskList != null) {
                    StringBuilder str = new StringBuilder();
                    LocalDateTime ldt = LocalDateTime.now().plusMinutes(MIN_5);
                    ArrayTaskList incomingTaskList = (ArrayTaskList) Tasks.incoming(taskList, ldt, ldt.plusMinutes(MIN_1));
                    int counter = 1;

                    str.append('\n').append(format.format(ldt.minusMinutes(MIN_5)));
                    for (Task task : incomingTaskList) {
                        str.append('\n').append(counter).append(". \"").append(task.getTitle()).append("\" starts at ")
                                .append(format.format(ldt.plusMinutes(MIN_1)));
                        counter++;
                    }
                    str.append('\n');

                    if (!"\n".contentEquals(str)
                            && !str.toString().equals(old)
                            && !('\n' + format.format(ldt.minusMinutes(MIN_5)) + '\n').equals(str.toString()))
                        stv.message(str.toString());

                    old = str.toString();
                }
                sleep(DELAY);
            } catch (InterruptedException e) {
                log.warn("Thread was interrupted");
            }
        }
    }
}