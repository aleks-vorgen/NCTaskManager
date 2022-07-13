package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controllers.IOController;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;

public class Main {

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
