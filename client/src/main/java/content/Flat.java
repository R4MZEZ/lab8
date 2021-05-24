package content;

import tools.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, объекты которого будут храниться в коллекции
 */
@XmlType(name = "flat")
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

    public Flat() {
        this.id = getNewId();
        this.creationDate = LocalDateTime.now();
    }
    @XmlAttribute(name = "id")
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlAttribute(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlJavaTypeAdapter(CoordinatesAdapter.class)
    @XmlAttribute
    private Coordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlAttribute
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlAttribute(name = "area")
    private Long area; //Поле не может быть null, Значение поля должно быть больше 0
    @XmlAttribute(name = "numberOfRooms")
    private Integer numberOfRooms; //Значение поля должно быть больше 0
    @XmlAttribute(name = "livingSpace")
    private long livingSpace; //Значение поля должно быть больше 0
    @XmlAttribute(name = "view")
    private View view; //Поле не может быть null
    @XmlAttribute(name = "transport")
    private Transport transport; //Поле может быть null
    @XmlElement(name="house")
    private House house; //Поле может быть null

    public static long getNewId() {
        static_id += 1;
        return static_id;
    }

    @XmlTransient
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

    @Override
    public int compareTo(Flat flat) {
        return this.name.length() - flat.getName().length();
    }

    public String NiceToString() {
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