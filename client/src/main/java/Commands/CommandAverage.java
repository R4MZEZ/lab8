package Commands;

import com.CollectionManager;

import java.io.Serializable;
import java.util.Scanner;

public class CommandAverage implements Command, Serializable {

    CollectionManager manager;

    public CommandAverage(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandAverage() {
    }

    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.average_of_living_space();
    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}
