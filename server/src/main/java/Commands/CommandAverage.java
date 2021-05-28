package Commands;

import Main.CollectionManager;

import java.io.Serializable;
import java.util.Scanner;

public class CommandAverage implements Command, Serializable {
    private static final long serialVersionUID = 6432673919719267327L;

    CollectionManager manager;
    String username;

    public CommandAverage(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandAverage() {
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
        manager.average_of_living_space();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
