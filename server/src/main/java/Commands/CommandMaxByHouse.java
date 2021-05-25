package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandMaxByHouse implements Command{
    private static final long serialVersionUID = -3649928705995173479L;

    CollectionManager manager;

    public CommandMaxByHouse(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandMaxByHouse() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.max_by_house();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
