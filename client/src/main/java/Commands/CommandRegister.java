package Commands;

import tools.Checker;
import tools.DataHasher;

import java.io.Console;
import java.util.Scanner;

public class CommandRegister implements Command{
    private static final long serialVersionUID = 3349655206396428797L;

    String username;
    String password;

    @Override
    public boolean validate(String argument, Scanner reader) {
        if (Checker.isNotString(argument)){
            System.err.println("Логин должен быть непустой строкой.");
            return false;
        }
        this.username = argument;
        Console console = System.console();
        System.out.print("Введите пароль: ");
        if (console != null){
            char[] symbols = console.readPassword();
            if (symbols != null) password = DataHasher.encryptStringSHA512(String.valueOf(symbols));
        }
        else password = reader.nextLine();
        return true;
    }
}
