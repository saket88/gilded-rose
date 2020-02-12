package com.geektrust;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ProcessFamily {
    Family family;

    public void runCommandsForm(File file) {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String command = sc.nextLine();
                    runCommand(command);
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no such file");
        }
    }

    public void constructFamily(File file) {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String command = sc.nextLine();
                initializeFamilyWith(command);

            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no such file");
        }
    }


    private void runCommand(String command) {
        String[] commandParams = command.split(" ");
        String commandResult;
        switch (commandParams[0]) {
            case "ADD_CHILD":
                commandResult = family.addChild(commandParams[1], commandParams[2], commandParams[3]);
                break;

            case "GET_RELATIONSHIP":
                commandResult = family.getRelativeFor(commandParams[1], commandParams[2]);
                break;

            default:
                commandResult = "INVALID_COMMAND";
                break;
        }

        System.out.println(commandResult);
    }


    private void initializeFamilyWith(String command) {
        String[] commandParameters = command.split(";");
        switch (commandParameters[0]) {

            case "ADD_ROOT":
               family = new Family(
                       new RootFamily(
                               new FamilyMember(commandParameters[3], commandParameters[4], null, null),
                               new FamilyMember(commandParameters[1], commandParameters[2], null, null)
                       )
               );
                break;

            case "ADD_CHILD":
                family.addChild(commandParameters[1], commandParameters[2], commandParameters[3]);
                break;

            case "ADD_SPOUSE":
                family.addSpouse(commandParameters[1], commandParameters[2], commandParameters[3]);
                break;

            default:
                System.out.println("INVALID COMMAND");
                break;
        }
    }

    public Family getFamily() {
        return family;
    }
}
