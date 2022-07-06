package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controlles.TaskListController;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;

import java.util.Scanner;

public class ShowTasksView {
    private ArrayTaskList taskList;
    private final TaskListController tlc = new TaskListController();

    {
        taskList = new ArrayTaskList();
        taskList.add(new Task("t1", Main.TODAY));
        taskList.add(new Task("t2", Main.NOW, Main.TOMORROW, 3600));
        taskList.add(new Task("t3", Main.TOMORROW));
        taskList.add(new Task("t4", Main.FROM_NOW_10.plusHours(2)));
    }

    public void getTaskList() {
        System.out.println("* * * List of tasks * * *");
        System.out.println(tlc.getTaskList(taskList));
    }

    public void getCalendar() {
        System.out.println("* * * Calendar * * *");
        System.out.println(tlc.getCalendar(taskList));
    }

    public void createTaskList(Task[] tasks) {
        Scanner in = new Scanner(System.in);
        String input;
        System.out.println("* * * * * Creating task list * * * * *");
        System.out.println("Your current list will be rewritten. \nYou sure?");
        System.out.print("Type 'yes' or 'no': ");
        input = in.nextLine();
        while (!"yes".equals(input) && !"no".equals(input)) {
            System.out.println("Invalid input");
            System.out.println("Type 'yes' or 'no': ");
            input = in.nextLine();
        }

        if (input.equals("yes"))
            taskList = tlc.createTaskList(tasks);
    }
}
