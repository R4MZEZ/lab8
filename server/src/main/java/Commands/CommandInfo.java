package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandInfo implements Command {
    private static final long serialVersionUID = 2935568159828483139L;

    CollectionManager manager;
    String username;

    public CommandInfo(CollectionManager manager) {
        this.manager = manager;
    }

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
        manager.info();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
