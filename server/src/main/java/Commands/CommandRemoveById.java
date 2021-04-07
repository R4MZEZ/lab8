package Commands;

import Server.CollectionManager;
import tools.Checker;

import java.util.Scanner;

public class CommandRemoveById implements Command{
    CollectionManager manager;
    String argument;

    public CommandRemoveById(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandRemoveById() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.remove_by_id(argument);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        if (!Checker.isLong(argument)) {
            System.out.println("Ошибка! 'id' должен быть целым положительным числом. Повторите ввод команды.");
            return false;
        }
        this.argument = argument;
        return true;
    }
}

