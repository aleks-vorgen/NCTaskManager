package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controllers.TaskController;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddEditRemoveView extends View {
    private final TaskController tc = new TaskController();
    private final Scanner in = new Scanner(System.in);

    private void addTask() {
        String title;
        LocalDateTime time;
        LocalDateTime time_start;
        LocalDateTime time_end;
        int time_interval_repeat;
        boolean isRepeated;
        String input;

        System.out.println("\n* * * Task adding * * *");

        System.out.print("Enter title: ");
        input = in.nextLine();
        while (input.equals("")) {
            System.out.println("Invalid input");
            System.out.print("Enter title: ");
            input = in.nextLine();
        }
        title = input;

        System.out.println("Is your task will be repetitive?");
        System.out.print("Type 'yes' or 'no': ");
        input = in.nextLine();
        while (input.equals("") || !"yes".equals(input) && !"no".equals(input)) {
            System.out.println("Invalid input");
            System.out.print("Type 'yes' or 'no': ");
            input = in.nextLine();
        }
        isRepeated = input.equals("yes");

        if (isRepeated) {
            time_start = dateTimeInput(" start", false);
            time_end = dateTimeInput(" end", false);

            time_interval_repeat = intervalInput(false);

            tc.addTask(title, time_start, time_end, time_interval_repeat);
        } else {
            time = dateTimeInput("", false);

            tc.addTask(title, time);
        }

        System.out.println("The task was added\n");
    }

    private void editTask() {
        if (tc.getSize() == 0) {
            System.out.println("Task list is empty");
            return;
        }
        int id;
        String title;
        LocalDateTime time;
        LocalDateTime time_start;
        LocalDateTime time_end;
        int time_interval_repeat;
        boolean isActive;
        boolean isRepeated;
        Task task;
        String input;

        System.out.println("\n* * * Task editing * * *");

        System.out.print("Enter an id of the task you want to edit: ");
        id = getInputID();
        task = tc.getTask(id);

        System.out.printf("You are about to edit \"%s\" task\n", task.getTitle());
        System.out.print("You sure? (yes/no): ");
        input = getInputYesNo();
        if ("no".equals(input))
            return;

        System.out.println("*If you don`t need to change some attributes, leave it blank");

        System.out.print("Enter new title: ");
        input = in.nextLine();
        if (!"".equals(input.trim()))
            title = input;
        else
            title = task.getTitle();

        System.out.printf("Your task is %s. Do you want to change this?\n",
                task.isRepeated() ? "repetitive" : "not repetitive");
        input = getInputYesNo();
        isRepeated = "yes".equals(input) != task.isRepeated(); //If yes, isRepetitive changes

        System.out.printf("Your task is %s. Do you want to change this?\n",
                task.isActive() ? "active" : "inactive");
        input = getInputYesNo();
        isActive = "yes".equals(input) != task.isActive(); //If yes, isActive changes

        if (isRepeated) {
            LocalDateTime tmpDateTime;
            int tmpInterval;
            tmpDateTime = dateTimeInput(" start", true);
            time_start = tmpDateTime == null ? task.getStartTime() : tmpDateTime;

            tmpDateTime = dateTimeInput(" end", true);
            time_end = tmpDateTime == null ? task.getEndTime() : tmpDateTime;

            tmpInterval = intervalInput(true);
            time_interval_repeat = tmpInterval == -1 ? task.getRepeatInterval() : tmpInterval;

            tc.editTask(title, time_start, time_end, time_interval_repeat, isActive, id);
        }
        else {
            LocalDateTime tmpDateTime;
            tmpDateTime = dateTimeInput("", true);
            time = tmpDateTime == null ? task.getTime() : tmpDateTime;

            tc.editTask(title, time, isActive, id);
        }

        System.out.println("The task was edited\n");
    }

    private String getInputYesNo() {
        String input;
        System.out.print("Type 'yes' or 'no': ");
        input = in.nextLine();
        while (!"yes".equals(input) && !"no".equals(input)) {
            System.out.println("Invalid input");
            System.out.print("Type 'yes' or 'no': ");
            input = in.nextLine();
        }
        return input;
    }

    private void removeTask() {
        if (tc.getSize() == 0) {
            System.out.println("Task list is empty");
            return;
        }
        int id;
        String input;
        Task task;

        System.out.println("\n* * * Task removing * * *");

        System.out.print("Enter an id of the task you want to remove: ");
        id = getInputID();
        task = tc.getTask(id);

        System.out.printf("You are about to remove \"%s\" task\n", task.getTitle());
        System.out.print("You sure? (yes/no): ");
        input = in.nextLine();
        while (!"yes".equals(input) && !"no".equals(input)) {
            System.out.println("Invalid input");
            System.out.print("Type 'yes' or 'no': ");
            input = in.nextLine();
        }
        if ("yes".equals(input))
            tc.removeTask(task);

        System.out.println("Task was removed\n");
    }

    private LocalDateTime dateTimeInput(String startEnd, boolean editing) {
        LocalDateTime dateTime;
        String input;
        System.out.printf("Enter%s date in format yyyy-mm-dd: ", startEnd);
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return null;

        while (true) {
            try {
                dateTime = LocalDateTime.parse(input + "T00:00:00");
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.printf("Enter%s start date in format yyyy-mm-dd: ", startEnd);
                input = in.nextLine();
            }
        }

        System.out.printf("Enter%s time in format hh:mm : ", startEnd);
        input = in.nextLine();
        while(true) {
            try {
                LocalDateTime tmpTime = LocalDateTime.parse("2000-01-01T" + input + ":00");
                dateTime = dateTime.plusHours(tmpTime.getHour());
                dateTime = dateTime.plusMinutes(tmpTime.getMinute());
                break;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.printf("Enter%s time in format hh:mm : ", startEnd);
                input = in.nextLine();
            }
        }
        return dateTime;
    }

    private int intervalInput(boolean editing) {
        String input;
        System.out.print("Enter interval in seconds: ");
        input = in.nextLine();
        if (editing && "".equals(input.trim()))
            return -1;

        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("This is not a number");
                System.out.print("Enter an interval in seconds: ");
                input = in.nextLine();
            }
        }
    }

    private int getInputID() {
        int id;
        String input;
        input = in.nextLine();
        while (true) {
            try {
                id = Integer.parseInt(input) - 1;
                if (id < 0 || id >= tc.getSize())
                    throw new IndexOutOfBoundsException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("This is not a number");
                System.out.print("Enter an id of the task you want to edit: ");
                input = in.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("This id does not exist");
                System.out.print("Enter an id of the task you want to edit: ");
                input = in.nextLine();
            }
        }
        return id;
    }

    public void menu() {
        while (true) {
            header();
            System.out.println("1. Create task");
            System.out.println("2. Edit task");
            System.out.println("3. Remove task");
            System.out.println("4. Back to main menu");
            System.out.print("Type your choice: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    editTask();
                    break;
                case "3":
                    removeTask();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("This option does not exist\n");
            }
        }
    }

    @Override
    protected void header() {
        String header = "\n* * * * * Task editing * * * * *";
        System.out.println(header);
    }
}
