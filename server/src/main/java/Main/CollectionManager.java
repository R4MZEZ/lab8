package Main;

import content.*;


import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс, обеспечивающий доступ к коллекции
 */

public class CollectionManager {
    private static LinkedList<Flat> flats = new LinkedList<>();
    private final static LocalDateTime initDate = LocalDateTime.now();
    private Handler handler;
    private static final Set<String> pathList = new HashSet<>();
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

    public static LocalDateTime getInitDate() {
        return initDate;
    }

    /**
     * Получить информацию о командах
     */
    public void help() {
        String res = "help: Вывести справку по доступным командам\n" +
                      "info: Вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                      "show: Вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                      "add {element}: Добавить новый элемент в коллекцию\n" +
                      "update id {element}: Обновить значение элемента коллекции, id которого равен заданному\n" +
                      "remove_by_id id: Удалить элемент коллекции по его id\n" +
                      "clear: Очистить коллекцию\n" +
//                      "save: Сохранить коллекцию в файл\n" +
                      "execute_script file_name: Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                      "exit: Завершить программу (без сохранения в файл)\n" +
                      "remove_at index: Удалить элемент, находящийся в заданной позиции коллекции (index)\n" +
                      "remove_last: Удалить последний элемент из коллекции\n" +
                      "shuffle: Перемешать элементы коллекции в случайном порядке\n" +
                      "average_of_living_space: Вывести среднее значение поля livingSpace для всех элементов коллекции\n" +
                      "max_by_house: Вывести любой объект из коллекции, значение поля house которого является максимальным\n" +
                      "filter_less_than_view view: Вывести элементы, значение поля view которых меньше заданного";
        connector.send(res);
    }

    /**
     * Получить информацию о коллекции
     */
    public void info() {
        flats = databaseHandler.loadCollectionFromDB();
        String info = "Тип - " + flats.getClass().getName() +
                "\nДата инициализации - " + getInitDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")) +
                "\nКоличество элементов - " + flats.size();
        connector.send(info);
    }

    /**
     * Показать элементы коллекции
     */
    public void show() {
        flats = databaseHandler.loadCollectionFromDB();
        flats.forEach(flat -> connector.send(flat.niceToString()));
    }

    /**
     * Добавить элемент коллекции
     */
    public void add(Flat flat){
        databaseHandler.addFlatToDB(flat);
        connector.send("===================================\nЭлемент успешно добавлен.");
    }

    /**
     * Обновить существующий элемент коллекции по его id
     * @param id : id (не индекс) нужного элемента
     */
    public void update(String id, Flat argument) {
        try {
            if (databaseHandler.getUserById(Long.parseLong(id)) == null){
                connector.send("Элемента с указанным id не существует.");
                return;
            }
            if (!databaseHandler.getUserById(Long.parseLong(id)).equals(argument.getUser())){
                connector.send("У вас недостаточно прав для модификации этого элемента.");
                return;
            }
            databaseHandler.updateFlatToDB(argument);
            connector.send("Элемент успешно обновлён.");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Отключение пользователя
     */
    public void exit(){
        connector.send("---Сервер выключен---");
        handler.isExit = true;
    }

    /**
     * Удалить элемент коллекции по его id
     *
     * @param id id(не индекс) нужного элемента
     */
    public void remove_by_id(String id) {
        if (flats.stream().anyMatch(flat -> flat.getId() == Long.parseLong(id))) {
            flats.remove(flats.stream().filter(flat -> flat.getId() == Long.parseLong(id)).findFirst().orElse(null));
            connector.send("Элемент успешно удалён.");
        } else connector.send("Элемента с id = '" + id + "' не найдено.");
    }

    /**
     * Очистить коллекцию
     */
    public void clear() {
        flats.clear();
        connector.send("Коллекция успешно очищена.");
    }

    /**
     * Сохранить коллекцию в файл
     */
    public void save(){
        //TODO
    }

    /**
     * Удалить элемент коллекции по его индексу
     * @param index : индекс нужного элемента
     */
    public void remove_at(String index) {
            try {
                flats.remove(Integer.parseInt(index));
                connector.send("Элемент коллекции успешно удалён.");
            } catch (IndexOutOfBoundsException ex) {
                connector.send("Ошибка. Элемента с таким индексом не существует.");
            }
        }

    /**
     * Удалить последний элемент коллекции
     */
    public void remove_last() {
        try {
            flats.removeLast();
            connector.send("Последний элемент коллекции успешно удален.");
        } catch (NoSuchElementException ex) {
            connector.send("Ошибка. Невозможно удалить последний элемент коллекции, т.к. она пуста.");
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
    public void execute_script(String path) throws FileNotFoundException {
        InputStream stream;
        try {
            if (path.startsWith("/dev")) {
                connector.send("Файл для извлечения скрипта не найден. Проверьте путь и права доступа к файлу.");
                return;
            }
        }catch (StringIndexOutOfBoundsException ignored){

        }
        try{
            stream = new BufferedInputStream(new FileInputStream(path));
        }catch (FileNotFoundException e){
            connector.send("Файл для извлечения скрипта не найден. Проверьте путь и права доступа к файлу.");
            return;
        }
        if (handler.getStream().equals(System.in)) {
            pathList.clear();
        } else {
            if (pathList.contains(path)) {
                connector.send("#############################################\nОшибка! Один или несколько скриптов зациклены.\n#############################################");
                return;
            }
        }
        //Проверка на зацикленность
        pathList.add(path);
        connector.send("====  Начало выполнения скрипта по адресу " + path + "  ====");
        handler.interactiveMod(stream);
        connector.send("====  Скрипт " + path + " успешно выполнен  ====\n");
        pathList.remove(path);
    }

    /**
     * Получить объект коллекции
     * @return коллекцию
     */
    public LinkedList<Flat> getFlats() {
        return flats;
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
            e.printStackTrace();}
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
            e.printStackTrace();}
    }

}

