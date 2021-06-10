//package Commands;
//
//import content.View;
//
//import java.io.Serializable;
//import java.util.Scanner;
//
//public class CommandFilter implements Command, Serializable {
//    private static final long serialVersionUID = -5497970455882024000L;
//
//    String argument;
//
//    @Override
//    public boolean validate(String argument, Scanner reader) {
//        try {
//            View.valueOf(argument);
//            this.argument = argument;
//            return true;
//        } catch (IllegalArgumentException e) {
//            System.out.println("Ошибка. Вы ввели недопустимое значение 'view'.");
//            View.ViewToString();
//            return false;
//        }
//    }
//
//}
