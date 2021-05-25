package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandShuffle implements Command{
    private static final long serialVersionUID = -3236822596483462976L;

    CollectionManager manager;

    public CommandShuffle(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandShuffle() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.shuffle();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
