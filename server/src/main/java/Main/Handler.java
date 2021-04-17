package Main;

import Commands.*;
import tools.ServerLogger;

import java.io.*;
import java.util.Scanner;

public class Handler implements Serializable, Runnable {
    Invoker invoker = new Invoker();
    Connector connector;
    CollectionManager manager = new CollectionManager();
    protected boolean isExit = false;
    Thread thread;

    BufferedInputStream stream;

    public Handler(Connector connector){
        this.connector = connector;
        manager.setConnector(connector);
        manager.setHandler(this);

        invoker.register("help", new CommandHelp(manager));
        invoker.register("info", new CommandInfo(manager));
        invoker.register("show", new CommandShow(manager));
        invoker.register("remove_by_id", new CommandRemoveById(manager));
        invoker.register("add", new CommandAdd(manager));
        invoker.register("update", new CommandUpdate(manager));
        invoker.register("clear", new CommandClear(manager));
        invoker.register("execute_script", new CommandExecuteScript(manager));
        invoker.register("save", new CommandSave(manager));
        invoker.register("remove_at", new CommandRemoveAt(manager));
        invoker.register("remove_last", new CommandRemoveLast(manager));
        invoker.register("shuffle", new CommandShuffle(manager));
        invoker.register("average_of_living_space", new CommandAverage(manager));
        invoker.register("max_by_house", new CommandMaxByHouse(manager));
        invoker.register("filter_less_than_view", new CommandFilter(manager));
        invoker.register("exit", new CommandExit(manager));
    }

    public BufferedInputStream getStream() {
        return stream;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    /**
     * Эмуляция интерактивного режима для выполнения скрипта
     * @param stream - поток ввода из скрипта
     */
    public void interactiveMod(InputStream stream) {
        String fullUserCommand = "";
        try (Scanner commandReader = new Scanner(stream)) {
            while (!fullUserCommand.equals("exit") && commandReader.hasNext()) {
                fullUserCommand = commandReader.nextLine();
                String[] command = fullUserCommand.trim().split(" ");
                if (invoker.contains(command[0])) {
                    try {
                        if (invoker.validate(command[0], command[1], commandReader)) {
                            invoker.execute(invoker.getCommandMap().get(command[0]));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        if (invoker.validate(command[0], "null", commandReader)) {
                            invoker.execute(invoker.getCommandMap().get(command[0]));
                        }
                    }
                }

            }
        }
    }

    /**
     * Поток засыпает и ждет, пока появится команда для выполнения и разбудит его
     * - он выполняет команду и вновь засыпает
     */
    @Override
    public void run() {
        while (!isExit){
            try {
                synchronized (thread) {
                    thread.wait();
                }
            } catch (InterruptedException e) {
                ServerLogger.logger.error("notify() произошел раньше, чем wait(): Перезапуск спящего потока");
            }
            Command command = connector.command;
            command.setManager(manager);
            invoker.execute(command);
        }
        System.out.println("Соединение с пользователем " + connector.userSocketAddress.toString() + " разорвано.");
        ServerLogger.logger.info("Соединение с пользователем " + connector.userSocketAddress.toString() + " разорвано.");
    }
}

