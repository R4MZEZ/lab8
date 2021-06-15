package Commands;

import Client.Commander;
import Client.Main;
import javafx.scene.paint.Color;
import tools.Checker;

public class CommandRemoveById implements Command{
    private static final long serialVersionUID = 4540012222739611587L;
    String argument;
    String username;

    public CommandRemoveById() {
        this.username = Commander.getUsername();
    }

    @Override
    public boolean validate(String argument) {
        if (!Checker.isLong(argument)) {
            Main.showWindow(200,600,Main.getBundle().getString("idErr"), Color.RED);
            return false;
        }
        this.argument = argument;
        return true;
    }
}

