package Commands;

import Server.CollectionManager;
import content.*;
import tools.Checker;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandAdd implements Command, Serializable {

    CollectionManager manager;
    Flat argument;

    public CommandAdd(CollectionManager manager) {
        this.manager = manager;
    }

    public CommandAdd() {
    }

    @Override
    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        argument.setId(Flat.getNewId());
        manager.getFlats().add(argument);
        manager.add();
    }

    @Override
    public boolean validate(String argument, Scanner commandReader) {
        try {
            final String[] temp = new String[11];
            System.out.print("Введите имя собственника: ");
            temp[0] = commandReader.nextLine();
            while (Checker.isNotString(temp[0])) {
                System.out.println("Ошибка! Uмя должно быть непустой строкой.");
                System.out.print("Введите имя собственника: ");
                temp[0] = commandReader.nextLine();
            }

            System.out.print("Введите координату X: ");
            temp[1] = commandReader.nextLine();
            while (!Checker.isFloat(temp[1])) {
                System.out.println("Ошибка! Координата должна быть числом.");
                System.out.print("Введите координату X: ");
                temp[1] = commandReader.nextLine();
            }

            System.out.print("Введите координату Y в диапазоне (-inf,368]: ");
            temp[2] = commandReader.nextLine();
            while (!Checker.isLong(temp[2]) || Long.parseLong(temp[2]) > 368) {
                System.out.println("Ошибка! Координата Y должна быть числом в диапазоне от -9223372036854775808 до 368.");
                System.out.print("Введите координату Y в диапазоне (-inf,368]: ");
                temp[2] = commandReader.nextLine();
            }

            System.out.print("Введите жил.площадь: ");
            temp[3] = commandReader.nextLine();
            while (!Checker.isLong(temp[3]) || Long.parseLong(temp[3]) < 0) {
                System.out.println("Ошибка! Жил.площадь должна быть положительным числом в диапазоне от 0 до 9223372036854775807.");
                System.out.print("Введите жил.площадь: ");
                temp[3] = commandReader.nextLine();
            }

            System.out.print("Введите количество комнат: ");
            temp[4] = commandReader.nextLine();
            while (!Checker.isInteger(temp[4]) || Integer.parseInt(temp[4]) < 0) {
                System.out.println("Ошибка! Количество комнат должно быть целым положительным числом в диапазоне от 0 до 2147483647.");
                System.out.print("Введите количество комнат: ");
                temp[4] = commandReader.nextLine();
            }

            System.out.print("Введите площадь жилых комнат: ");
            temp[5] = commandReader.nextLine();
            while (!Checker.isLong(temp[5]) || Long.parseLong(temp[5]) < 0) {
                System.out.println("Ошибка! Площадь жилых комнат должна быть положительным числом в диапазоне от 0 до 9223372036854775807.");
                System.out.print("Введите площадь жилых комнат: ");
                temp[5] = commandReader.nextLine();
            }

            View.ViewToString();
            System.out.print("Введите вид из окна: ");
            temp[6] = commandReader.nextLine();
            while (!Checker.isView(temp[6])) {
                System.out.println("Ошибка! Введенное значение недопустимо.");
                View.ViewToString();
                System.out.print("Введите вид из окна: ");
                temp[6] = commandReader.nextLine();
            }

            Transport.TransportToString();
            System.out.print("Введите транспорт собственника: ");
            temp[7] = commandReader.nextLine();
            while (!Checker.isTransport(temp[7])) {
                System.out.println("Ошибка! Введенное значение недопустимо.");
                Transport.TransportToString();
                System.out.print("Введите транспорт собственника: ");
                temp[7] = commandReader.nextLine();
            }

            System.out.print("Введите название дома: ");
            temp[8] = commandReader.nextLine();
            while (Checker.isNotString(temp[8])) {
                System.out.println("Ошибка! Название должно быть непустой строкой.");
                System.out.print("Введите название дома: ");
                temp[8] = commandReader.nextLine();
            }

            System.out.print("Введите год постройки дома: ");
            temp[9] = commandReader.nextLine();
            while (!Checker.isInteger(temp[9]) || Integer.parseInt(temp[9]) < 0) {
                System.out.println("Ошибка! Год постройки дома должен быть целым положительным числом в диапазоне от 0 до 2147483647.");
                System.out.print("Введите год постройки дома: ");
                temp[9] = commandReader.nextLine();
            }

            System.out.print("Введите количество квартир на этаже: ");
            temp[10] = commandReader.nextLine();
            while (!Checker.isInteger(temp[10]) || Integer.parseInt(temp[10]) < 0) {
                System.out.println("Ошибка! Количество квартир на этаже должно быть целым положительным числом в диапазоне от 0 до 2147483647.");
                System.out.print("Введите количество квартир на этаже: ");
                temp[10] = commandReader.nextLine();
            }

            this.argument = new Flat(temp[0], new Coordinates(Float.parseFloat(temp[1]), Long.parseLong(temp[2])),
                            Long.parseLong(temp[3]), Integer.parseInt(temp[4]), Long.parseLong(temp[5]),
                            View.valueOf(temp[6]), Transport.valueOf(temp[7]), new House(temp[8], Integer.parseInt(temp[9]),
                            Integer.parseInt(temp[10])));
            return true;
        } catch (NoSuchElementException ex){
            System.out.println();
            System.out.println("Ошибка! Отсутствие ожидаемого аргумента.");
            return false;
        }
    }
}

