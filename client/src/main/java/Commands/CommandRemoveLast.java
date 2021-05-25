package Commands;

import Client.Commander;

import java.util.Scanner;

public class CommandRemoveLast implements Command{
    private static final long serialVersionUID = 2519342000003981654L;
    String username;

    @Override
    public boolean validate(String argument, Scanner reader) {

        this.username = Commander.getUsername();
        return true;
    }
}
