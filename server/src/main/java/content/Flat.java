package content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, объекты которого будут храниться в коллекции
 */
public class Flat implements Comparable<Flat>, Serializable {
    private static long static_id = 0;

    public Flat(String name, Coordinates coordinates, Long area, Integer numberOfRooms, long livingSpace, View view, Transport transport, House house) {
        this.id = getNewId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now(); //LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss"))
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.livingSpace = livingSpace;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }

    public Flat(long id, String name, Coordinates coordinates, LocalDateTime creationDate, Long area, Integer numberOfRooms, long livingSpace, View view, Transport transport, House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.livingSpace = livingSpace;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }


    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Long area; //Поле не может быть null, Значение поля должно быть больше 0
    private final Integer numberOfRooms; //Значение поля должно быть больше 0
    private final long livingSpace; //Значение поля должно быть больше 0
    private final View view; //Поле не может быть null
    private final Transport transport; //Поле может быть null
    private final House house; //Поле может быть null
    private final String user;

    public static long getNewId() {
        static_id += 1;
        return static_id;
    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getLivingSpace() {
        return livingSpace;
    }

    public View getView() {
        return view;
    }

    public House getHouse() {
        return house;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getArea() {
        return area;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Transport getTransport() {
        return transport;
    }

    public String getUser() {
        return user;
    }

    @Override
    public int compareTo(Flat flat) {
        return this.name.length() - flat.getName().length();
    }

    public String niceToString() {
        return "\t\t\t\t\t\tКВАРТUРА " + id + "\n" +
                "Номер квартиры: " + id +
                ", имя собственника: " + name  +
                ", координаты квартиры: (" + coordinates.getX() + ", " + coordinates.getY() + ")" +
                ",\n время добавления квартиры в коллекцию: " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")) +
                ",\n жил.площадь: " + area + " кв.м." +
                ", число комнат: " + numberOfRooms +
                ", площадь жилых комнат: " + livingSpace + " кв.м." +
                ", вид из окна: " + view +
                ", транспорт:" + transport +
                ",\n данные дома {Название - " + house.getName() + ", год постройки - " + house.getYear() + ", число квартир на этаже - " + house.getNumberOfFlatsOnFloor() + "}";
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", numberOfRooms=" + numberOfRooms +
                ", livingSpace=" + livingSpace +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house +
                '}';
    }

    /**
     * Проверка, правильно ли был заполнен объект из файла
     * @return true/false
     */
    public boolean isEmpty(){
        return name == null || name.equals("") || coordinates == null || coordinates.getX() == null || coordinates.getY() > 368 || area == null || area < 0 || numberOfRooms == null || numberOfRooms < 0 || livingSpace < 0 || view == null || transport == null || house == null || house.getName() == null || house.getNumberOfFlatsOnFloor() < 0 || house.getYear() == null || house.getYear() < 0;
    }


}