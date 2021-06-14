package Commands;

import Client.Commander;

import java.util.Scanner;

public class CommandRemoveLast implements Command{
    private static final long serialVersionUID = 2519342000003981654L;
    String username;

    public CommandRemoveLast() {
        this.username = Commander.getUsername();
    }

    @Override
    public boolean validate(String argument) {
        return true;
    }
}
