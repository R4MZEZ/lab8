package content;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * Класс для хранения данных о доме, в котором находится квартира
 */
public class House implements Comparable<House>, Serializable {
    public House(String name, Integer year, int numberOfFlatsOnFloor) {
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public House() {
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public int getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    @XmlAttribute(name = "name")
    private String name; //Поле не может быть null
    @XmlAttribute(name = "year")
    private Integer year; //Значение поля должно быть больше 0
    @XmlAttribute(name = "numberOfFlatsOnFloor")
    private int numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    @Override
    public int compareTo(House obj) {
        return this.year - obj.getYear();
    }
}