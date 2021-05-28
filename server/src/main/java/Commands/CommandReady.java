package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandReady  implements Command {
    private static final long serialVersionUID = 2070506952673629004L;

    CollectionManager manager;
    String username;

    @Override
    public void setUsername(String username) {
        this.username = username;
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
