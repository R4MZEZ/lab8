package Commands;

import tools.Checker;

import java.util.Scanner;

public class CommandRemoveAt implements Command{
    private static final long serialVersionUID = -5531871344777781959L;

    String argument;

    @Override
    public boolean validate(String argument, Scanner reader) {
        if (!Checker.isInteger(argument)) {
            System.out.println("Ошибка! Uндекс должен быть целым неотрицательным числом. Повторите ввод команды.");
            return false;
        }
        this.argument = argument;
        return true;
    }
}
