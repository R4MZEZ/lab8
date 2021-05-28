package Commands;

import Main.CollectionManager;
import tools.Checker;

import java.util.Scanner;

public class CommandRemoveById implements Command{
    private static final long serialVersionUID = 4540012222739611587L;

    CollectionManager manager;
    String argument;
    String username;


    public CommandRemoveById(CollectionManager manager) {
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
        manager.remove_by_id(username, argument);
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

