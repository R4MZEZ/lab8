package Commands;

import java.io.Serializable;

public class CommandExit implements Command, Serializable {
    private static final long serialVersionUID = -3985681923210095903L;

    @Override
    public boolean validate(String argument) {
        return true;
    }
}
