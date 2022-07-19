package ua.edu.sumdu.j2se.kulykov.tasks.views;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.controllers.IOController;
import ua.edu.sumdu.j2se.kulykov.tasks.controllers.TaskListController;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;

public class Main {
	private static final Logger log = Logger.getLogger("ProgramStartsAppender");

	public static void main(String[] args) {
		log.info("Program was started");

		ArrayTaskList taskList = new ArrayTaskList();
		new IOController(taskList).readBin();

		Notificator notificator = new Notificator(taskList, new ShowTasksView());
		notificator.start();

		TaskListController tlc = new TaskListController(taskList, new ShowTasksView());

		tlc.showMenu();
	}
}
