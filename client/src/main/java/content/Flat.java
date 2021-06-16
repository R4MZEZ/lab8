package content;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс, объекты которого будут храниться в коллекции
 */

public class Flat implements Comparable<Flat>, Serializable {
    private static final long serialVersionUID = -4288824612268147150L;


    public Flat(String name, Coordinates coordinates, Long area, Integer numberOfRooms, long livingSpace, View view, Transport transport, House house) {
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


    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private Float coordX;
    private Long coordY;
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Long area; //Поле не может быть null, Значение поля должно быть больше 0
    private final Integer numberOfRooms; //Значение поля должно быть больше 0
    private final long livingSpace; //Значение поля должно быть больше 0
    private final View view; //Поле не может быть null
    private final Transport transport; //Поле может быть null
    private final House house; //Поле может быть null
    private String house_name;
    private Integer house_year;
    private Integer house_numberOfFlatsOnFloor;
    private String user;



    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Float getCoordX() {
        return coordinates.getX();
    }

    public Long getCoordY() {
        return coordinates.getY();
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

    public long getLivingSpace() {
        return livingSpace;
    }

    public View getView() {
        return view;
    }

    public Transport getTransport() {
        return transport;
    }

    public House getHouse() {
        return house;
    }
    public String getHouse_name() {
        return house.getName();
    }

    public Integer getHouse_year() {
        return house.getYear();
    }

    public Integer getHouse_numberOfFlatsOnFloor() {
        return house.getNumberOfFlatsOnFloor();
    }

    public String getUser() {
        return user;
    }

    @Override
    public int compareTo(Flat flat) {
        return this.name.length() - flat.getName().length();
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ",\n name='" + name + '\'' +
                ",\n coordinates=" + coordinates.getX() + " ; " + coordinates.getY() +
                ",\n creationDate=" + creationDate +
                ",\n area=" + area +
                ",\n numberOfRooms=" + numberOfRooms +
                ",\n livingSpace=" + livingSpace +
                ",\n view=" + view +
                ",\n transport=" + transport +
                ",\n user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals("content.Flat")) return false;
        return (id == ((Flat)obj).getId()) &&
                coordinates.getX().equals(((Flat) obj).getCoordX()) &&
                coordinates.getY() == (((Flat) obj).getCoordY());
    }
}