package Commands;

import Main.CollectionManager;

import java.io.Serializable;
import java.util.Scanner;

public class CommandExit implements Command, Serializable {
    private static final long serialVersionUID = -3985681923210095903L;

    CollectionManager manager;

    public CommandExit(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
//        new CommandSave(manager).execute();
        manager.exit();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
