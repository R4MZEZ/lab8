//package Commands;
//
//import Client.Commander;
//import content.Flat;
//import tools.Checker;
//
//import java.util.Scanner;
//
//public class CommandUpdate implements Command{
//    private static final long serialVersionUID = -8375499694740192662L;
//
//    Flat flat;
//    String id;
//    String username;
//
//    @Override
//    public boolean validate(String argument, Scanner reader) {
//        if (!Checker.isLong(argument)) {
//            System.out.println("Ошибка! 'id' должен быть целым положительным числом. Повторите ввод команды.");
//            return false;
//        }
//        CommandAdd a = new CommandAdd();
//        a.validate(argument,reader);
//        this.flat = a.argument;
//        this.id = argument;
//        this.username = Commander.getUser();
//        return true;
//    }
//
//
//}
//
