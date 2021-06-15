package Commands;

import Client.Commander;
import Client.Main;
import javafx.scene.paint.Color;
import tools.Checker;

public class CommandRemoveAt implements Command{
    private static final long serialVersionUID = -5531871344777781959L;

    String argument;
    String username;


    public CommandRemoveAt() {
        username = Commander.getUsername();
    }

    @Override
    public boolean validate(String argument) {
        if (!Checker.isInteger(argument)) {
            Main.showWindow(200,500,Main.getBundle().getString("indexErr"), Color.RED);
            return false;
        }
        this.argument = argument;
        return true;
    }
}
