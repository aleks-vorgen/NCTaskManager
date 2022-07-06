package ua.edu.sumdu.j2se.kulykov.tasks.controlles;

import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TaskController extends Controller {

    public void addTask() {
        System.out.println("* * * * * Task adding * * * * * *");
    }

    public void editTask(Task task) {
        String title;
        LocalDateTime time;
        LocalDateTime time_start;
        LocalDateTime time_end;
        int time_interval_repeat;
        boolean isActive;
        boolean isRepeated;
        String input;
        Scanner in = new Scanner(System.in);

        System.out.println("* * * * * Task editing * * * * * *");

        System.out.printf("The task is %s", task.isRepeated() ? "repetitive" : "not repetitive");
            System.out.print("Do you want to change this?\nType 'yes' or 'no': ");
            while (!in.hasNextLine() || !in.nextLine().equals("yes") || !in.nextLine().equals("no")) {
                System.out.println("Invalid input");
                System.out.println("Type 'yes' or 'no': ");
                in.next();
            }
            input = in.nextLine();
            if (input.equals("yes"))
                task.setRepeated(!task.isRepeated());

        System.out.print("Enter title: ");
        input = in.nextLine();
        title = input;

        System.out.print("Enter time: ");
        //time = in.nextLine();

    }

    public void saveTask(Task task) {

    }

    public void removeTask(Task task) {

    }

    //public Task getTask(int id) {
    //
    //
    //}
    //
    //public Task getTask(String name) {
    //
    //}
}
