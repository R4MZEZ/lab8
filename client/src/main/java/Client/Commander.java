package Client;


import Commands.*;
import tools.ClientLogger;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, обеспечивающий обработку пользовательских команд
 */
public class Commander {

    Invoker invoker = new Invoker();
    static Connector connector;
    static boolean isAuth;
    static String username;

    public static Connector getConnector() {
        return connector;
    }

    private String password;

    public Commander(CommandReady commandReady, int PORT){
        connector = new Connector(PORT);
        connector.send(commandReady);

        invoker.register("help", new CommandHelp());
        invoker.register("info", new CommandInfo());
        invoker.register("show", new CommandShow());
        invoker.register("remove_by_id", new CommandRemoveById());
        invoker.register("add", new CommandAdd());
        invoker.register("update", new CommandUpdate());
        invoker.register("clear", new CommandClear());
        invoker.register("execute_script", new CommandExecuteScript());
        invoker.register("remove_at", new CommandRemoveAt());
        invoker.register("remove_last", new CommandRemoveLast());
        invoker.register("shuffle", new CommandShuffle());
        invoker.register("average_of_living_space", new CommandAverage());
        invoker.register("max_by_house", new CommandMaxByHouse());
        invoker.register("filter_less_than_view", new CommandFilter());
        invoker.register("exit", new CommandExit());
        invoker.register("login", new CommandLogin());
        invoker.register("register", new CommandRegister());


    }

    /**
     * Включить интерактивный режим
     * @param stream : поток ввода
     */
    public void interactiveMod(InputStream stream) {
        String fullUserCommand = "";
        if (stream.equals(System.in))System.out.println("Для выполнения команд необходима авторизация: используйте login/register <username>\n***\tНачало работы. Для просмотра доступных команд напишите 'help'\t***");
        try (Scanner commandReader = new Scanner(stream)) {
            do{
                fullUserCommand = commandReader.nextLine();
                String[] command = fullUserCommand.trim().split(" ");
                if (invoker.contains(command[0])) {
                    if (isAuth || command[0].equals("login") || command[0].equals("register")) {
                        try {
                            if (invoker.validate(command[0], command[1], commandReader)) {
                                connector.send(invoker.getCommandMap().get(command[0]));
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            if (invoker.validate(command[0], "", commandReader)) {
                                connector.send(invoker.getCommandMap().get(command[0]));
                            }
                        }
                    }else System.err.println("Вы не авторизованы!");
                } else System.err.println("Неопознанная команда! Введите 'help' для просмотра доступных команд.");

            }while (!fullUserCommand.equals("exit") && commandReader.hasNext());
        }catch (NoSuchElementException e){ClientLogger.logger.error("Ошибка в интерактивном режиме", e);}
        System.out.println("***\tВыход из интерактивного режима\t***");
        connector.send(invoker.getCommandMap().get("exit"));
        Connector.isExit = true;
        ClientLogger.logger.info("Отключение от сервера");
    }

    public static void setUsername(String username) {
        Commander.username = username;
    }

    public static String getUsername() {
        return username;
    }
}
