package Commands;

import Main.CollectionManager;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class CommandExecuteScript implements Command, Serializable {
    private static final long serialVersionUID =  7699342124433530839L;

    CollectionManager manager;
    String argument;
    String username;

    public CommandExecuteScript(CollectionManager manager) {
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
    public void execute(){
        manager.execute_script(argument, username);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        this.argument = argument;
        return true;
    }
}
