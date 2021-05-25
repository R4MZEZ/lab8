package Commands;

import Main.CollectionManager;
import tools.Checker;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandRegister implements Command {
    private static final long serialVersionUID = 3349655206396428797L;


    String username;
    String password;
    CollectionManager manager;

    @Override
    public void execute() {manager.register(username,password);}

    @Override
    public boolean validate(String argument, Scanner reader) {
        if (Checker.isNotString(argument)) {
            System.err.println("Логин должен быть непустой строкой.");
            return false;
        }
        this.username = argument;
        Console console = System.console();
        if (console != null) {
            char[] symbols = console.readPassword();
            if (symbols != null) password = String.valueOf(symbols);
        } else password = reader.nextLine();
        return true;
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }
}
