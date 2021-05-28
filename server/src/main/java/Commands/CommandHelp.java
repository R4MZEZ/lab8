package Commands;

import Main.CollectionManager;

import java.io.Serializable;
import java.util.Scanner;

public class CommandHelp implements Command, Serializable {
    private static final long serialVersionUID = -4848347544087315082L;

    CollectionManager manager;
    String username;

    public CommandHelp(CollectionManager manager) {
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
        manager.help();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
