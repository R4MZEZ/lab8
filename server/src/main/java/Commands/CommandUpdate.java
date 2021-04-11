package Commands;

import com.CollectionManager;
import content.Flat;
import tools.Checker;

import java.util.Scanner;

public class CommandUpdate implements Command{

    CollectionManager manager;
    Flat flat;
    String id;

    public CommandUpdate(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandUpdate() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.update(id,flat);
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
        this.id = argument;
        return true;
    }
}

