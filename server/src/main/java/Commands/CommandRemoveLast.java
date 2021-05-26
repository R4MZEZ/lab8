package Commands;

import Main.CollectionManager;

import java.util.Scanner;

public class CommandRemoveLast implements Command{
    private static final long serialVersionUID = 2519342000003981654L;

    CollectionManager manager;
    String username;


    public CommandRemoveLast(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandRemoveLast() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.remove_last(username);
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
