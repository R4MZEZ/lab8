//package Commands;
//
//import Client.Commander;
//import tools.Checker;
//
//import java.util.Scanner;
//
//public class CommandRemoveAt implements Command{
//    private static final long serialVersionUID = -5531871344777781959L;
//
//    String argument;
//    String username;
//
//    @Override
//    public boolean validate(String argument, Scanner reader) {
//        if (!Checker.isInteger(argument)) {
//            System.out.println("Ошибка! Uндекс должен быть целым неотрицательным числом. Повторите ввод команды.");
//            return false;
//        }
//        this.argument = argument;
//        this.username = Commander.getUser();
//        return true;
//    }
//}
