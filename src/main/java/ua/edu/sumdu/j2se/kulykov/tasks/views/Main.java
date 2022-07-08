package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

	public static final LocalDateTime NOW = LocalDateTime.now();
	public static final LocalDateTime TODAY = NOW.withHour(0).withMinute(0).withSecond(0);
	public static final LocalDateTime TOMORROW = TODAY.plusDays(1);
	public static final LocalDateTime ALMOST_TOMORROW = TOMORROW.minusSeconds(1);
	public static final LocalDateTime FROM_NOW_10 = NOW.plusSeconds(10);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ShowTasksView stv = new ShowTasksView();

		while(true) {
			System.out.println("* * * * * Main menu * * * * *");
			System.out.println("1. Show all tasks");
			System.out.println("2. Show calendar of tasks");
			System.out.println("3. Show incoming tasks");
			System.out.println("4. Exit");
			System.out.print("Type your choice: ");
			String choice = sc.nextLine();
			switch (choice) {
				case "1":
					System.out.println();
					stv.getTaskList();
					break;
				case "2":
					stv.getCalendar();
					break;
				case "3":
					stv.getIncoming();
					break;
				case "4":
					System.exit(0);
					break;
				default:
					System.out.println("This option does not exist\n");
			}
		}
	}
}
