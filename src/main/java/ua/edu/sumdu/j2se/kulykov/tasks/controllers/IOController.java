package ua.edu.sumdu.j2se.kulykov.tasks.controllers;


import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.TaskIO;
import ua.edu.sumdu.j2se.kulykov.tasks.views.IOView;
import ua.edu.sumdu.j2se.kulykov.tasks.views.ShowTasksView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller class for writing or reading binary/json files.
 */
public class IOController extends Controller {
    private static final Logger log = Logger.getLogger(IOController.class);
    private static final Path PATH_TO_BIN = Paths.get("res/bin");
    private static final Path PATH_TO_JSON = Paths.get("res");
    private static final String BIN_FILE_NAME = "/binary.bin";
    private ArrayTaskList taskList = null;
    private IOView ioView = null;

    /**
     * Default constructor.
     */
    public IOController() {
        super();
    }

    /**
     * Constructor which initialize only taskList.
     * @param taskList list of tasks.
     */
    public IOController(ArrayTaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Constructor which initialize taskList and ioView.
     * @param taskList list of tasks.
     * @param ioView IOView.
     */
    public IOController(ArrayTaskList taskList, IOView ioView) {
        this.taskList = taskList;
        this.ioView = ioView;
    }

    /**
     * Method writes the binary file where program finishes from TaskListController.
     */
    public void writeBin() {
        File file = new File(PATH_TO_BIN.toString(), BIN_FILE_NAME);
        try {
            if (!Files.exists(PATH_TO_BIN)) {
                Files.createDirectories(PATH_TO_BIN);
                Files.createFile(Path.of(PATH_TO_BIN + BIN_FILE_NAME));
            }
            TaskIO.writeBinary(taskList, file);
            log.info("File " + file.getName() + " was written by program");
        } catch (IOException e) {
            log.error("File could not be written");
        }
    }

    /**
     * Method writes the JSON file.
     */
    private void writeTxt() {
        ioView.message("\n* * * File saving * * *\n");
        String fileName;

        fileName = '/' + ioView.getFileNameInput();
        File file = new File(PATH_TO_JSON.toString(), fileName);
        try {
            if (!Files.exists(PATH_TO_JSON)) {
                Files.createDirectories(PATH_TO_JSON);
            }
            TaskIO.writeText(taskList, file);
            log.info("File " + file.getName() + " was written");
            ioView.message("File saved successfully\n");
        } catch (IOException e) {
            ioView.message("File did not save. Check logfile for details.");
            log.error(e.getMessage());
        }
    }

    /**
     * Method reads the JSON file.
     */
    private void readTxt() {
        ioView.message("\n* * * File uploading * * *\n");
        String fileName;

        fileName = '/' + ioView.getFileNameInput();
        File file = new File(PATH_TO_JSON.toString(), fileName);
        if (!Files.exists(Path.of(PATH_TO_JSON + fileName))) {
            log.warn("Entered nonexistent name of the file");
            ioView.message("File \"" + file.getName() + "\" does not exists\n");
        }
        else {
            taskList.getStream().forEach(taskList::remove);
            TaskIO.readText(taskList, file);
            log.info("File " + file.getName() + " was read");
            ioView.message("File uploaded successfully\n");
        }
    }

    /**
     * Method reads the binary file where program starts.
     */
    public void readBin() {
        File file = new File(PATH_TO_BIN.toString(), BIN_FILE_NAME);
        try {
            if (!Files.exists(PATH_TO_BIN)) {
                Files.createDirectories(PATH_TO_BIN);
                Files.createFile(Path.of(PATH_TO_BIN + BIN_FILE_NAME));
            }
            TaskIO.readBinary(taskList, file);
            log.info("File " + file.getName() + " was read by program");
        } catch (IOException e) {
            log.error("File not found");
        }
    }

    public void showMenu() {
        int key;
        key = ioView.menu();
        switch (key) {
            case 1 -> write();
            case 2 -> read();
            case 3 -> toTaskListController();
        }
    }

    private void write() {
        writeTxt();
        showMenu();
    }

    private void read() {
        readTxt();
        showMenu();
    }

    private void toTaskListController() {
        new TaskListController(taskList, new ShowTasksView())
                .showMenu();
    }
}
