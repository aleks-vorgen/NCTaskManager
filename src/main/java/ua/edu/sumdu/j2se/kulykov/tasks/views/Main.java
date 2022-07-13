package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controllers.IOController;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.Task;
import ua.edu.sumdu.j2se.kulykov.tasks.models.TaskIO;

import java.time.LocalDateTime;

public class Main {

	public static final LocalDateTime NOW = LocalDateTime.now();
	public static final LocalDateTime TODAY = NOW.withHour(0).withMinute(0).withSecond(0);
	public static final LocalDateTime YESTERDAY = TODAY.minusDays(1);
	public static final LocalDateTime TODAY_1H = TODAY.plusHours(1);
	public static final LocalDateTime TOMORROW = TODAY.plusDays(1);
	public static final ArrayTaskList taskList = new ArrayTaskList();

	static
	{
		new IOController().readBin();
	}

	public static void main(String[] args) {
		ShowTasksView stv = new ShowTasksView();

		stv.menu();
	}
}
