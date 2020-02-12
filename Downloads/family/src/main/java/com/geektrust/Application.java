package com.geektrust;

import java.io.File;
import java.nio.file.Paths;

public class Application {
    ProcessFamily processFamily;
    private String familyCreationPath;
    private String commandFilePath;

    public Application() {
        processFamily= new ProcessFamily();
    }

    public Application(String familyCreationPath, String commandFilePath) {
        this.familyCreationPath = familyCreationPath;
        this.commandFilePath = commandFilePath;
        processFamily= new ProcessFamily();
    }

    public void processfile(String path) {
        File file = new File(path);
        processFamily = new ProcessFamily();
        processFamily.constructFamily(file);
    }

    public static void main(String... args){
        String familyPath = Paths.get("construct_family.txt").toAbsolutePath().toString();
        Application application = new Application(familyPath,args[0]);
        application.constructFamily();
        application.runCommands();

    }

    private void runCommands() {
        processFamily.runCommandsForm(new File(commandFilePath));
    }

    private void constructFamily() {
        processFamily.constructFamily(new File(familyCreationPath));
    }

    public Family getFamily() {
        return processFamily.getFamily();
    }
}
