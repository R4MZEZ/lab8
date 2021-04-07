package Commands;

import Server.CollectionManager;

import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CommandSave implements Command{

    CollectionManager manager;

    public CommandSave(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandSave() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.save(manager);

    }

    @Override
    public boolean validate(String argument, Scanner reader) {
        return true;
    }
}

