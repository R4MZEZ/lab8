package Commands;

import Main.CollectionManager;

import java.io.Serializable;
import java.util.Scanner;

public class CommandClear implements Command, Serializable {
    private static final long serialVersionUID = 935077734379123140L;

    CollectionManager manager;
    String username;


    public CommandClear(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandClear() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.clear(username);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}

