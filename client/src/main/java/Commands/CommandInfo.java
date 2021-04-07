package Commands;

import Server.CollectionManager;

import java.util.Scanner;

public class CommandInfo implements Command {

    CollectionManager manager;

    public CommandInfo() {
    }

    public CommandInfo(CollectionManager manager) {
        this.manager = manager;
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
