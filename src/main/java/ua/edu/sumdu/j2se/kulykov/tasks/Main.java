package ua.edu.sumdu.j2se.kulykov.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		LinkedTaskList ltl = new LinkedTaskList();

		ltl.add(new Task("t1", 1, 3, 1));
		ltl.add(new Task("t2", 1));
		ltl.add(new Task("t3", 2));
		ltl.getTask(0).setActive(true);

		for (Task task : ltl) {
			System.out.println(task);
		}

		System.out.println();

		ArrayTaskList atl = new ArrayTaskList();

		atl.add(new Task("t1", 1, 3, 1));
		atl.add(new Task("t2", 1));
		atl.add(new Task("t3", 3));
		atl.getTask(0).setActive(true);
	}
}
