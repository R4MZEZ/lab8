package Main;

import content.*;
import tools.ServerLogger;


import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс, обеспечивающий доступ к коллекции
 */

public class CollectionManager {
    private static List<Flat> flats = Collections.synchronizedList(new LinkedList<>());
    private final static LocalDateTime initDate = LocalDateTime.now();
    private Handler handler;
    static final Set<String> pathList = new HashSet<>();
    Connector connector;
    DatabaseHandler databaseHandler;

    public CollectionManager() {
    }


    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public static void setFlats(List<Flat> flats) {
        CollectionManager.flats = flats;
    }

    public static LocalDateTime getInitDate() {
        return initDate;
    }

    /**
     * Получить информацию о командах
     */
    public void help() {
        String res = "help: Вывести справку по доступным командам\n" +
                      "info: Вывести информацию о коллекции \n" +
                      "show: Вывести все элементы коллекции\n" +
                      "add {element}: Добавить новый элемент в коллекцию\n" +
                      "update id {element}: Обновить значение элемента коллекции по ID\n" +
                      "remove_by_id id: Удалить элемент коллекции по ID\n" +
                      "clear: Очистить коллекцию\n" +
//                      "save: Сохранить коллекцию в файл\n" +
                      "execute_script file_name: Считать и исполнить скрипт из указанного файла.\n" +
                      "exit: Завершить программу\n" +
                      "remove_at index: Удалить элемент, находящийся в заданной позиции коллекции\n" +
                      "remove_last: Удалить последний элемент из коллекции\n" +
 //                     "shuffle: Перемешать элементы коллекции в случайном порядке\n" +
                      "average_of_living_space: Вывести среднее значение поля livingSpace\n" +
                      "max_by_house: Вывести квартиру с максимальным годом постройки дома\n" +
                      "filter_less_than_view view: Вывести элементы, view которых меньше заданного";
        connector.send(res);
    }

    /**
     * Получить информацию о коллекции
     */
    public void info() {
//        flats = databaseHandler.loadCollectionFromDB();
//        if (flats==null){
//            connector.send("Произошла ошибка при загрузке коллекции из базы данных.");
//            return;
//        }
        String info = "Тип - " + flats.getClass().getName() +
                "\nДата инициализации - " + getInitDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")) +
                "\nКоличество элементов - " + flats.size();
        connector.send(info);
    }

    /**
     * Показать элементы коллекции
     */
    public void show() {
//        flats = databaseHandler.loadCollectionFromDB();
//        if (flats==null){
//            connector.send("Произошла ошибка при загрузке коллекции из базы данных.");
//            return;
//        }
//        flats.forEach(flat -> connector.send(flat.niceToString()));
        connector.send(flats);
    }

    /**
     * Добавить элемент коллекции
     */
    public void add(Flat flat, String username){
        flat.setUser(username);
        databaseHandler.addFlatToDB(flat);
        connector.send("===================================\nЭлемент успешно добавлен.");
        flats.add(flat);

    }

    /**
     * Обновить существующий элемент коллекции по его id
     * @param id : id (не индекс) нужного элемента
     */
    public void update(String id, Flat argument, String username) {
        try {
            if (databaseHandler.getUserById(Long.parseLong(id)) == null){
                connector.send("Элемента с указанным id не существует.");
                return;
            }
            if (!databaseHandler.getUserById(Long.parseLong(id)).equals(username)){
                connector.send("У вас недостаточно прав для модификации этого элемента.");
                return;
            }
            databaseHandler.updateFlatToDB(argument);
            connector.send("Элемент успешно обновлён.");

            for (Flat flat : flats) {
                if (flat.getId() == Long.parseLong(id)) {
                    flats.set(flats.indexOf(flat), argument);
                    flats.get(flats.indexOf(flat)).setId(flat.getId());
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Отключение пользователя
     */
    public void exit(){
        handler.isExit = true;
    }

    /**
     * Удалить элемент коллекции по его id
     *
     * @param id id(не индекс) нужного элемента
     */
    public void remove_by_id(String username, String id) {
        try {
            if (databaseHandler.deleteById(username, Long.parseLong(id))){
                connector.send("Элемент успешно удалён.");
                flats.remove(flats.stream().filter(flat -> flat.getId() == Long.parseLong(id)).findFirst().orElse(null));
                return;
            }
            connector.send("Элемента с id = '" + id + "' не найдено, либо этот элемент принадлежит не вам.");
        } catch (SQLException e) {
            System.err.println("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);
            connector.send("Ошибка доступа к базе данных.");
        }
    }

    /**
     * Очистить коллекцию
     */
    public void clear(String username) {
        try {
            databaseHandler.deleteByUsername(username);
//            flats = databaseHandler.loadCollectionFromDB();
            connector.send("Элементы коллекции, принадлежащие вам, успешно очищены.");
            flats.removeIf(flat -> flat.getUser().equals(username));
        } catch (SQLException e) {
            System.err.println("Ошибка доступа к базе данных.");
            connector.send("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);
        }
    }

    /**
     * Удалить элемент коллекции по его индексу
     * @param index : индекс нужного элемента
     */
    public void remove_at(String username, String index) {
        try {
            connector.send(databaseHandler.removeAt(username, Integer.parseInt(index)));
            if (flats.get(Integer.parseInt(index)).getUser().equals(username)) flats.remove(Integer.parseInt(index));
        } catch (SQLException e) {
            System.err.println("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);
            connector.send("Ошибка доступа к базе данных.");
        }

    }

    /**
     * Удалить последний элемент коллекции
     */
    public void remove_last(String username) {
        try {
            connector.send(databaseHandler.removeLast(username));
            if (flats.get(flats.size()-1).getUser().equals(username)) flats.remove(flats.size()-1);
        } catch (SQLException e) {
            System.err.println("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);
            connector.send("Ошибка доступа к базе данных.");

        }

    }

    /**
     * Перемешать элементы коллекции
     */
    public void shuffle() {
        Collections.shuffle(flats);
        connector.send("Элементы коллекции успешно перемешаны.");
    }

    /**
     * Команда-маркер подключения к серверу
     */
    public void ready(){
        connector.send("Подключение установлено.");
    }

    /**
     * Вывести среднее значение поля livingSpace для всех элементов коллекции
     */
    public void average_of_living_space() {
        if (flats.size() > 0) {
            connector.send("Cреднее значение поля livingSpace равно " + flats.stream().mapToLong(Flat::getLivingSpace).average().getAsDouble());
        } else connector.send("Ошибка. Коллекция пуста.");
    }

    /**
     * Вывести элемент коллекции с максимальным значением годом постройки дома
     */
    public void max_by_house() {
        if (flats.stream().findAny().isPresent())
            connector.send(flats.stream().max(Comparator.comparing(Flat::getHouse)).get().niceToString());
        else connector.send("Ошибка. Коллекция пуста.");
    }

    /**
     * Вывести элементы коллекции, у которых значение поля View меньше заданного
     * @param view : объект Enum'a View, относительно которого нужно фильтровать
     */
    public void filter_less_than_view(String view) {
        if (flats.stream().anyMatch(flat -> flat.getView().compareTo(View.valueOf(view)) < 0))
            flats.stream().filter(flat -> flat.getView().compareTo(View.valueOf(view)) < 0).forEach(flat -> connector.send(flat.niceToString()));
        else connector.send("Не найдено элементов со значением поля view меньше заданного.");
    }

    /**
     * Выполнить скрипт из заданного файла
     * @param path Путь до файла
     * @throws FileNotFoundException если файл недоступен/не найден
     */
    public void execute_script(String path, String username){
        InputStream stream;

        try{
            stream = new BufferedInputStream(new FileInputStream(path));
        }catch (FileNotFoundException e){
            connector.send("Файл для извлечения скрипта не найден. Проверьте путь и права доступа к файлу.");
            return;
        }

        if (pathList.contains(path)) {
            connector.send("#############################################\nОшибка! Один или несколько скриптов зациклены.\n#############################################");
            return;
        }

        //Проверка на зацикленность
        pathList.add(path);
        handler.interactiveMod(stream, username);
        connector.send("====  Скрипт " + path + " успешно выполнен  ====\n");
        pathList.remove(path);
    }


    public void login(String username, String password){
        try {
            if (!databaseHandler.userExists(username)){
                connector.send("Пользователя с таким именем не найдено.");
                return;
            }
            if (!databaseHandler.loginUser(username, password)) {
                connector.send("Неверный пароль.");
                return;
            }
            connector.send("С возвращением, " + username + "!");
        } catch (SQLException e) {
            connector.send("Ошибка доступа к базе данных.");
            System.err.println("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);}
    }

    public void register(String username, String password){
        try {
            if (databaseHandler.userExists(username)){
                connector.send("Пользователь с таким именем уже существует.");
                return;
            }
            databaseHandler.registerUser(username, password);
            connector.send("Добро пожаловать, " + username + "!");
        } catch (SQLException e) {
            connector.send("Ошибка доступа к базе данных.");
            System.err.println("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);}
    }

}

