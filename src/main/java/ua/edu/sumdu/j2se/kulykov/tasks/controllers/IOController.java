package ua.edu.sumdu.j2se.kulykov.tasks.controllers;

import ua.edu.sumdu.j2se.kulykov.tasks.models.ArrayTaskList;
import ua.edu.sumdu.j2se.kulykov.tasks.models.TaskIO;
import ua.edu.sumdu.j2se.kulykov.tasks.views.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOController extends Controller {
    private static final Path PATH_TO_BIN = Paths.get("res/bin");
    private static final Path PATH_TO_JSON = Paths.get("res");
    private static final String BIN_FILE_NAME = "/binary.bin";
    private final ArrayTaskList taskList = Main.taskList;

    public void writeBin() {
        File file = new File(PATH_TO_BIN.toString(), BIN_FILE_NAME);
        try {
            if (!Files.exists(PATH_TO_BIN)) {
                Files.createDirectories(PATH_TO_BIN);
                Files.createFile(Path.of(PATH_TO_BIN + BIN_FILE_NAME));
            }
            TaskIO.writeBinary(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTxt(String fileName) {
        fileName = '/' + fileName;
        File file = new File(PATH_TO_JSON.toString(), fileName);
        try {
            if (!Files.exists(PATH_TO_JSON)) {
                Files.createDirectories(PATH_TO_JSON);
                Files.createFile(Path.of(PATH_TO_JSON + fileName));
            }
            TaskIO.writeText(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readTxt(String fileName) {
        fileName = '/' + fileName;
        File file = new File(PATH_TO_JSON.toString(), fileName);
        if (!Files.exists(Path.of(PATH_TO_JSON + fileName))) {
            return false;
        }
        else {
            taskList.getStream().forEach(taskList::remove);
            TaskIO.readText(taskList, file);
            return true;
        }
    }

    public void readBin() {
        File file = new File(PATH_TO_BIN.toString(), BIN_FILE_NAME);
        try {
            if (!Files.exists(PATH_TO_BIN)) {
                Files.createDirectories(PATH_TO_BIN);
                Files.createFile(Path.of(PATH_TO_BIN + BIN_FILE_NAME));
            }
            TaskIO.readBinary(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
