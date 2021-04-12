package Commands;

import com.CollectionManager;

import java.util.Scanner;

public class CommandInfo implements Command {

    private static final long serialVersionUID = -4386179675728560206L;
    public CommandInfo() {
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
