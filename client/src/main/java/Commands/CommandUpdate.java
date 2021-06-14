package Commands;

import Client.Commander;
import content.Flat;
import javafx.scene.paint.Color;
import tools.Checker;

public class CommandUpdate implements Command{
    private static final long serialVersionUID = -8375499694740192662L;

    Flat flat;
    String id;
    String username;

    public CommandUpdate() {
        this.username = Commander.getUsername();
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    @Override
    public boolean validate(String argument) {
        if (!Checker.isLong(argument)) {
            Client.Main.showWindow(300,500,"Ошибка! 'id' должен быть целым положительным числом.\n Повторите ввод команды.", Color.RED);
            return false;
        }
        id = argument;
        return true;
    }


}

