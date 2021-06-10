package Commands;


import java.io.Serializable;

public class CommandHelp implements Command, Serializable {
    private static final long serialVersionUID = -4848347544087315082L;



    @Override
    public boolean validate(String argument) {
        return true;
    }
}
