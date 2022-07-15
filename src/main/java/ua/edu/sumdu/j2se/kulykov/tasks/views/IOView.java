package ua.edu.sumdu.j2se.kulykov.tasks.views;

import ua.edu.sumdu.j2se.kulykov.tasks.controllers.IOController;

import java.util.Scanner;

/**
 * Class for writing or reading json files using IOController.
 */
public class IOView extends View {
    private final IOController ioc = new IOController();
    private final Scanner in = new Scanner(System.in);

    /**
     * Method writes the JSON file to which user wants to save their task list using IOController.
     */
    private void writeTxt() {
        System.out.println("\n* * * File saving * * *");
        String fileName;
        System.out.print("Enter the name of the file: ");
        fileName = in.nextLine().trim();
        while (true) {
            try {
                for (char c : fileName.toCharArray()) {
                    if (c == ' ')
                        throw new IllegalArgumentException("Name of the file can`t contains spaces");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.print("Enter the name of the file: ");
                fileName = in.nextLine().trim();
            }
        }
        ioc.writeTxt(fileName);
        System.out.println("File was created");
    }

    /**
     * Method reads the JSON file from which user wants to load their task list using IOController.
     */
    private void readTxt() {
        System.out.println("\n* * * File uploading * * *");
        String fileName;
        System.out.print("Enter the name of the file u want to upload: ");
        fileName = in.nextLine().trim();

        if (ioc.readTxt(fileName))
            System.out.println("File uploaded");
        else
            System.out.println("File does not exists");
    }

    /**
     * Method shows the menu for managing files.
     */
    public void menu() {
        while (true) {
            header();
            System.out.println("1. Save task list to text file");
            System.out.println("2. Upload task list from text file");
            System.out.println("3. Back to main menu");
            System.out.print("Type your choice: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    writeTxt();
                    break;
                case "2":
                    readTxt();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("This option does not exist\n");
            }
        }
    }

    @Override
    protected void header() {
        System.out.println("\n* * * * * Task writer * * * * *");
    }
}
