package Commands;

import Main.CollectionManager;
import tools.Checker;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandLogin implements Command {
    private static final long serialVersionUID = -1649624206992473477L;


    String username;
    String password;
    CollectionManager manager;

    @Override
    public void execute() {manager.login(username,password);}

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

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
