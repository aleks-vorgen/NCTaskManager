package ua.edu.sumdu.j2se.kulykov.tasks.controllers;


import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.TaskIO;
import ua.edu.sumdu.j2se.kulykov.tasks.views.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller for writing or reading binary/json files.
 */
public class IOController extends Controller {
    private static final Logger log = Logger.getLogger(IOController.class);
    private static final Path PATH_TO_BIN = Paths.get("res/bin");
    private static final Path PATH_TO_JSON = Paths.get("res");
    private static final String BIN_FILE_NAME = "/binary.bin";
    private final ArrayTaskList taskList = Main.taskList;

    /**
     * Method writes the binary file where program finishes from ShowTasksView.
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
     * @param fileName name of the writing file.
     */
    public void writeTxt(String fileName) {
        fileName = '/' + fileName;
        File file = new File(PATH_TO_JSON.toString(), fileName);
        try {
            if (!Files.exists(PATH_TO_JSON)) {
                Files.createDirectories(PATH_TO_JSON);
            }
            TaskIO.writeText(taskList, file);
            log.info("File" + file.getName() + " was written");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Method reads the JSON file.
     * @param fileName name of the reading file.
     * @return false if file not exists and true if exists one.
     */
    public boolean readTxt(String fileName) {
        fileName = '/' + fileName;
        File file = new File(PATH_TO_JSON.toString(), fileName);
        if (!Files.exists(Path.of(PATH_TO_JSON + fileName))) {
            log.warn("Entered nonexistent name of the file");
            return false;
        }
        else {
            taskList.getStream().forEach(taskList::remove);
            TaskIO.readText(taskList, file);
            log.info("File " + file.getName() + " was read");
            return true;
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
}
