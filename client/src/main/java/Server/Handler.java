package Server;

import Commands.*;
import content.Flat;
import content.House;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class Handler implements Serializable, Runnable {
    Invoker invoker = new Invoker();
    Connector connector;
    CollectionManager manager;
    protected boolean isExit = false;

    BufferedInputStream stream;
    JAXBContext context;
    Unmarshaller unmarshaller;

    public Handler(String filePath, Connector connector){
        manager = new CollectionManager();
        this.connector = connector;
        try {
            context = JAXBContext.newInstance(Flat.class, CollectionManager.class, House.class);
            unmarshaller = context.createUnmarshaller();
            stream = new BufferedInputStream(new FileInputStream(filePath));
            manager = (CollectionManager) unmarshaller.unmarshal(stream);
            Iterator<Flat> iterator = manager.getFlats().listIterator();
            while (iterator.hasNext()) {
                if (iterator.next().isEmpty()) {
                    connector.send("Ошибка! Одна из квартир не была добавлена в коллекцию, т.к. одно или несколько полей не были указаны, либо выходят за допустимый диапазон.");
                    iterator.remove();
                }
            }
        } catch (NumberFormatException e) {
            connector.send("Ошибка! Невозможно считать коллекцию из файла, т.к. одно или несколько полей указаны в некорректном формате (например, на месте числа - строка).");
        } catch (FileNotFoundException e) {
            connector.send("Ошибка! Файл с входными данными не найден, проверьте путь и права доступа к файлу.");
        } catch (JAXBException e) {
            connector.send("Ошибка при десериализации документа. Проверьте правильность разметки.");
        }finally {
            manager.setConnector(connector);
            manager.setHandler(this);
        }

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
                } else System.out.println("Неопознанная команда! Введите 'help' для просмотра доступных команд.");

            }
        }
    }

    @Override
    public void run() {
        while (!isExit){
            Command command = (Command) connector.receive();
            command.setManager(manager);
            invoker.execute(command);
        }
        System.out.println("Соединение с пользователем " + connector.userAddress + ":" + connector.userPort + " разорвано.");
    }
}

