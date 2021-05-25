package content;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс, объекты которого будут храниться в коллекции
 */

public class Flat implements Comparable<Flat>, Serializable {
    private static long static_id = 0;
    private static final long serialVersionUID = -4288824612268147150L;


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


    private final long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Long area; //Поле не может быть null, Значение поля должно быть больше 0
    private final Integer numberOfRooms; //Значение поля должно быть больше 0
    private final long livingSpace; //Значение поля должно быть больше 0
    private final View view; //Поле не может быть null
    private final Transport transport; //Поле может быть null
    private final House house; //Поле может быть null

    public static long getNewId() {
        static_id += 1;
        return static_id;
    }


    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Flat flat) {
        return this.name.length() - flat.getName().length();
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


}