package ua.edu.sumdu.j2se.kulykov.tasks.views;

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
			System.out.println("Type your choice: ");
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
					//TODO stv.incoming()
					break;
				case "4":
					System.exit(0);
			}
		}
	}
}
