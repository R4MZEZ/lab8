package Commands;

import java.io.Serializable;
import java.util.Scanner;

public class CommandAverage implements Command, Serializable {
    private static final long serialVersionUID = 6432673919719267327L;

    @Override
    public boolean validate(String argument) {
        return true;
    }
}
