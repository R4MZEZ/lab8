package Commands;

import Main.CollectionManager;
import content.View;

import java.io.Serializable;
import java.util.Scanner;

public class CommandFilter implements Command, Serializable {
    private static final long serialVersionUID = -5497970455882024000L;

    CollectionManager manager;
    String argument;
    String username;

    public CommandFilter(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void execute() {
        manager.filter_less_than_view(argument);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        try {
            View.valueOf(argument);
            this.argument = argument;
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка. Вы ввели недопустимое значение 'view'.");
            View.ViewToString();
            return false;
        }
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }
}
