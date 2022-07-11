package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.views.Main;

public abstract class Controller {
    public int getSize() {
        return Main.taskList.size();
    }
}
