package ua.edu.sumdu.j2se.kulykov.tasks;

public class Main {

	public static void main(String[] args) {
		ArrayTaskList taskList = new ArrayTaskList();
		Task t1 = new Task("A", 0);
		Task t2 = new Task("B", 1);
		Task t3 = new Task("C", 2);
		t3.setActive(true);

		taskList.add(t1);
		taskList.add(t2);
		taskList.add(t3);


		ArrayTaskList res = taskList.incoming(0, 1000);
		System.out.println();
	}
}
