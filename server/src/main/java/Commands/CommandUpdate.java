package Commands;

import Main.CollectionManager;
import content.Flat;
import tools.Checker;

import java.util.Scanner;

public class CommandUpdate implements Command{
    private static final long serialVersionUID = -8375499694740192662L;

    CollectionManager manager;
    Flat flat;
    String id;
    String username;

    public CommandUpdate(CollectionManager manager) {
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
    public void execute() {
        manager.update(id,flat,username);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        if (!Checker.isLong(argument)) {
            System.out.println("Ошибка! 'id' должен быть целым положительным числом. Повторите ввод команды.");
            return false;
        }
        CommandAdd a = new CommandAdd();
        a.validate(argument,reader);
        this.flat = a.argument;
        id = argument;
        return true;
    }
}

