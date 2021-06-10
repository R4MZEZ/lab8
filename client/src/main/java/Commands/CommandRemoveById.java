//package Commands;
//
//import Client.Commander;
//import tools.Checker;
//
//import java.util.Scanner;
//
//public class CommandRemoveById implements Command{
//    private static final long serialVersionUID = 4540012222739611587L;
//    String argument;
//    String username;
//
//    @Override
//    public boolean validate(String argument, Scanner reader) {
//        if (!Checker.isLong(argument)) {
//            System.out.println("Ошибка! 'id' должен быть целым положительным числом. Повторите ввод команды.");
//            return false;
//        }
//        this.argument = argument;
//        this.username = Commander.getUser();
//        return true;
//    }
//}
//
