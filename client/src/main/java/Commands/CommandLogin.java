package Commands;

import Client.Commander;
import tools.Checker;
import tools.DataHasher;

import java.io.Console;
import java.util.Scanner;

public class CommandLogin implements Command{

    private static final long serialVersionUID = -1649624206992473477L;


    String username;
    String password;

    public CommandLogin() {
    }

    public CommandLogin(String username, String password) {
        this.username = username;
        this.password = DataHasher.encryptStringSHA512(password);
    }

    @Override
    public boolean validate(String argument) {
        if (Checker.isNotString(argument)){
            return false;
        }
        Commander.setUsername(username);
        return true;
    }
}
