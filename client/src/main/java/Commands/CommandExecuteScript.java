package Commands;

import Client.Commander;

import java.io.Serializable;

public class CommandExecuteScript implements Command, Serializable {
    private static final long serialVersionUID =  7699342124433530839L;
    String argument;
    String username;


    public CommandExecuteScript() {
        username = Commander.getUsername();
    }

    @Override
    public boolean validate(String argument) {
        this.argument = argument;
        return true;
    }
}
