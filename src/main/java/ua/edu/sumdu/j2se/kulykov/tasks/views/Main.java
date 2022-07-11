package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;

import java.time.LocalDateTime;

public class Main {

	public static final LocalDateTime NOW = LocalDateTime.now();
	public static final LocalDateTime TODAY = NOW.withHour(0).withMinute(0).withSecond(0);
	public static final LocalDateTime YESTERDAY = TODAY.minusDays(1);
	public static final LocalDateTime TODAY_1H = TODAY.plusHours(1);
	public static final LocalDateTime TOMORROW = TODAY.plusDays(1);
	public static final ArrayTaskList taskList = new ArrayTaskList();

	static //TODO чтение тасков из файла
	{
		taskList.add(new Task("Daily", YESTERDAY, TOMORROW, 3600*24));
		taskList.add(new Task("Hourly", TODAY, TOMORROW, 3600));
		taskList.add(new Task("Every 3 hours", TODAY_1H, TOMORROW.plusDays(1), 3*3600));
		taskList.add(new Task("Once", TOMORROW.plusDays(1).plusHours(13)));
		taskList.add(new Task("Once2", TOMORROW.plusDays(1).plusHours(15)));
	}

	public static void main(String[] args) {
		ShowTasksView stv = new ShowTasksView();

		stv.menu();
	}
}
