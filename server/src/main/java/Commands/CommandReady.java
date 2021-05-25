package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandReady  implements Command {
    private static final long serialVersionUID = 2070506952673629004L;

    CollectionManager manager;

    public CommandReady() {
    }

    public CommandReady(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.ready();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
