package Commands;

import java.util.Scanner;

public class CommandInfo implements Command {

    private static final long serialVersionUID = 2935568159828483139L;

    @Override
    public boolean validate(String argument) {
        return true;
    }
}
