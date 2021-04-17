package Commands;

import java.util.Scanner;

public class CommandRemoveLast implements Command{
    private static final long serialVersionUID = 2519342000003981654L;

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
