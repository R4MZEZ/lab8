package Commands;

import Client.Commander;
import Client.Main;
import javafx.scene.paint.Color;
import tools.Checker;

import java.io.Serializable;

public class CommandExecuteScript implements Command, Serializable {
    private static final long serialVersionUID =  7699342124433530839L;
    String argument;
    String username;


    public CommandExecuteScript() {
        username = Commander.getUsername();
    }

    @Override
    public boolean validate(String argument) {
        if (Checker.isNotString(argument)){
            Main.showWindow(200,500,Main.getBundle().getString("scriptErr"), Color.RED);
            return false;
        }
        this.argument = argument;
        return true;
    }
}
