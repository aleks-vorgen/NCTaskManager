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
    private final ArrayTaskList taskList = Main.taskList;

    public void writeBin() {
        Path path = Paths.get("res/bin");
        String fileName = "/binary.bin";
        File file = new File(path.toString(), fileName);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                Files.createFile(Path.of(path + fileName));
            }
            TaskIO.writeBinary(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTxt(String fileName) {
        fileName = '/' + fileName;
        Path path = Paths.get("res");
        File file = new File(path.toString(), fileName);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                Files.createFile(Path.of(path + fileName));
            }
            TaskIO.writeText(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readTxt(String fileName) {
        fileName = '/' + fileName;
        Path path = Paths.get("res");
        File file = new File(path.toString(), fileName);
        if (!Files.exists(Path.of(path + fileName))) {
            return false;
        }
        else {
            taskList.getStream().forEach(taskList::remove);
            TaskIO.readText(taskList, file);
            return true;
        }
    }

    public void readBin() {
        Path path = Paths.get("res/bin");
        String fileName = "/binary.bin";
        File file = new File(path.toString(), fileName);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                Files.createFile(Path.of(path + fileName));
            }
            TaskIO.readBinary(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
