package ua.edu.sumdu.j2se.kulykov.tasks;

import java.time.LocalDateTime;

public class Main {

	public static final LocalDateTime NOW = LocalDateTime.now();
	public static final LocalDateTime TODAY = NOW.withHour(0).withMinute(0).withSecond(0);
	public static final LocalDateTime TOMORROW = TODAY.plusDays(1);
	public static final LocalDateTime ALMOST_TOMORROW = TOMORROW.minusSeconds(1);
	public static final LocalDateTime FROM_NOW_10 = NOW.plusSeconds(10);

	public static void main(String[] args) {
		Task t1 = new Task("A", NOW);
		Task t2 = new Task("B", FROM_NOW_10);

		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());

		System.out.println(t1);
		System.out.println(t2);

		t1.setTitle("some task");
		t2.setTitle("some task");

		t1.setTime(NOW);
		t2.setTime(NOW);

		t1.setActive(true);
		t2.setActive(true);

		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());

		System.out.println(t1);
		System.out.println(t2);
	}
}
