package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.views.View;

/**
 * Abstract Controller.
 */
public abstract class Controller {
    public abstract void showMenu();
    protected void nonexistentPoint(View view) {
        view.messageln("This point does not exists");
        showMenu();
    }
}
