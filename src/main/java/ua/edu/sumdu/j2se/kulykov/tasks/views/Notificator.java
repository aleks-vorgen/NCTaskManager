package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificator extends Thread {
    private final ArrayTaskList taskList;
    private final ShowTasksView stv;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
    private static final Logger log = Logger.getLogger(Notificator.class);

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
                StringBuilder str = new StringBuilder();
                LocalDateTime ldt = LocalDateTime.now().plusMinutes(5);
                ArrayTaskList incomingTaskList = (ArrayTaskList) Tasks.incoming(taskList, ldt, ldt.plusMinutes(1));
                int counter = 1;

                for (Task task : incomingTaskList) {
                    str.append('\n').append(counter).append(". \"").append(task.getTitle()).append("\" starts at ")
                            .append(format.format(ldt.plusMinutes(1)));
                    counter++;
                }
                str.append('\n');

                if (!"\n".contentEquals(str) && !str.toString().equals(old))
                    stv.message(str.toString());

                old = str.toString();

                sleep(30000);
            } catch (NullPointerException e) {
                log.warn("Task list was empty");
            } catch (InterruptedException e) {
                log.warn("Thread was interrupted");
            }
        }
    }
}