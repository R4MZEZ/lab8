package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandShow implements Command {
    private static final long serialVersionUID = -6736089393672297269L;

    CollectionManager manager;

    public CommandShow(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandShow() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.show();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
