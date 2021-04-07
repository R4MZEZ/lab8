package Commands;

import Server.CollectionManager;

import java.util.Scanner;

public class CommandMaxByHouse implements Command{

    CollectionManager manager;

    public CommandMaxByHouse(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandMaxByHouse() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.max_by_house();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
