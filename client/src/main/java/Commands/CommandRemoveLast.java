package Commands;

import Server.CollectionManager;

import java.util.Scanner;

public class CommandRemoveLast implements Command{

    CollectionManager manager;

    public CommandRemoveLast(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandRemoveLast() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.remove_last();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
