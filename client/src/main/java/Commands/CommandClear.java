package Commands;

import Client.Commander;

import java.io.Serializable;

public class CommandClear implements Command, Serializable {
    private static final long serialVersionUID = 935077734379123140L;
    String username;

    public CommandClear() {
        username = Commander.getUsername();
        System.out.println(Commander.getUsername());

    }

    @Override
    public boolean validate(String argument) {
        return true;
    }
}

