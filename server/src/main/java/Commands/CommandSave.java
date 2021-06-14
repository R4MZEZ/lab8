//package Commands;
//
//import Main.CollectionManager;
//
//import java.util.Scanner;
//
//public class CommandSave implements Command{
//
//    CollectionManager manager;
//    String username;
//
//    public CommandSave(CollectionManager manager) {
//        this.manager = manager;
//    }
//
//    @Override
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public void setManager(CollectionManager manager) {
//        this.manager = manager;
//    }
//
//    @Override
//    public void execute() {
//        manager.save();
//
//    }
//
//    @Override
//    public boolean validate(String argument, Scanner reader) {
//        return true;
//    }
//}
//
