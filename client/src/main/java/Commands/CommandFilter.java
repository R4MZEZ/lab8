package Commands;

import Client.Main;
import content.View;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class CommandFilter implements Command, Serializable {
    private static final long serialVersionUID = -5497970455882024000L;

    String argument;

    @Override
    public boolean validate(String argument) {
        try {
            View.valueOf(argument);
            this.argument = argument;
            return true;
        } catch (IllegalArgumentException e) {
            Main.showWindow(200,1000,"Ошибка. Вы ввели недопустимое значение 'view'.\n"+ View.ViewToString(), Color.RED);
            return false;
        }
    }

}
