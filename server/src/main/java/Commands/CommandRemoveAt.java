package Commands;

import com.CollectionManager;
import tools.Checker;

import java.util.Scanner;

public class CommandRemoveAt implements Command{

    CollectionManager manager;
    String argument;

    public CommandRemoveAt(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandRemoveAt() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.remove_at(argument);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        if (!Checker.isInteger(argument)) {
            System.out.println("Ошибка! Uндекс должен быть целым неотрицательным числом. Повторите ввод команды.");
            return false;
        }
        this.argument = argument;
        return true;
    }
}
