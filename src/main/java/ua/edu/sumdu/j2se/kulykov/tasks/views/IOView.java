package ua.edu.sumdu.j2se.kulykov.tasks.views;

import java.util.Scanner;

/**
 * View class for writing or reading json files.
 */
public class IOView extends View {
    private final Scanner in = new Scanner(System.in);

    /**
     * Method gets the name of file input.
     * @return string name of file.
     */
    public String getFileNameInput(){
        String input;
        message("Enter the name of the file: ");
        input = in.nextLine().trim();
        while (true) {
            try {
                if ("".equals(input))
                    throw new IllegalArgumentException("Field was empty\n");
                for (char c : input.toCharArray()) {
                    if (c == ' ')
                        throw new IllegalArgumentException("The name of the file can`t contains spaces\n");
                }
                break;
            } catch (IllegalArgumentException e) {
                message(e.getMessage());
                message("Enter the name of the file: ");
                input = in.nextLine().trim();
            }
        }
        return input;
    }

    /**
     * Method shows the menu for managing files.
     */
    public int menu() {
        while (true) {
            header();
            message("1. Save task list to text file\n");
            message("2. Upload task list from text file\n");
            message("3. Back to main menu\n");
            message("Type your choice: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                default:
                    message("This option does not exist\n\n");
            }
        }
    }

    @Override
    protected void header() {
        message("\n* * * * * Task writer * * * * *\n");
    }
}
