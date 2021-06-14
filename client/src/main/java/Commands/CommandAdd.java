package Commands;

import Client.Commander;
import content.*;
import tools.Checker;
import gui.controllers.AddWindowController;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class CommandAdd implements Command, Serializable {
    private static final long serialVersionUID = 312956475280869092L;

    Flat argument;
    String username;
    transient AddWindowController controller;

    public CommandAdd(AddWindowController controller) {
        username = Commander.getUsername();
        this.controller = controller;
    }

    @Override
    public boolean validate(String argument) {
        boolean result = true;
        try {
            final String[] temp = argument.split("-_-");
            if (Checker.isNotString(temp[0])){
                result = false;
                controller.getNameError().setVisible(true);
            }

            if (!Checker.isFloat(temp[1])){
                result = false;
                controller.getXError().setVisible(true);
            }

            if (!Checker.isLong(temp[2]) || Long.parseLong(temp[2]) > 368){
                result = false;
                controller.getYError().setVisible(true);
            }

            if (!Checker.isLong(temp[3]) || Long.parseLong(temp[3]) < 0){
                result = false;
                controller.getAreaError().setVisible(true);
            }

            if (!Checker.isInteger(temp[4]) || Integer.parseInt(temp[4]) < 0){
                result = false;
                controller.getNumberError().setVisible(true);
            }

            if (!Checker.isLong(temp[5]) || Long.parseLong(temp[5]) < 0){
                result = false;
                controller.getLivingAreaError().setVisible(true);
            }

            if (Checker.isNotString(temp[6])){
                result = false;
                controller.getHouseNameError().setVisible(true);
            }

            if (!Checker.isInteger(temp[7]) || Integer.parseInt(temp[7]) < 0){
                result = false;
                controller.getHouseYearError().setVisible(true);
            }

            if (!Checker.isInteger(temp[8]) || Integer.parseInt(temp[8]) < 0){
                result = false;
                controller.getHouseNumberError().setVisible(true);
            }

            if (result) this.argument = new Flat(temp[0], new Coordinates(Float.parseFloat(temp[1]), Long.parseLong(temp[2])),
                            Long.parseLong(temp[3]), Integer.parseInt(temp[4]), Long.parseLong(temp[5]),
                            View.valueOf(temp[9]), Transport.valueOf(temp[10]), new House(temp[6], Integer.parseInt(temp[7]),
                            Integer.parseInt(temp[8])));
            return result;

        } catch (NoSuchElementException ex){
            return false;
        }
    }
}

