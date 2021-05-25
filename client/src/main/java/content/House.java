package content;

import java.io.Serializable;

/**
 * Класс для хранения данных о доме, в котором находится квартира
 */
public class House implements Comparable<House>, Serializable {
    private static final long serialVersionUID = 1321406681307379186L;

    public House(String name, Integer year, int numberOfFlatsOnFloor) {
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }


    public Integer getYear() {
        return year;
    }

    private final String name; //Поле не может быть null
    private final Integer year; //Значение поля должно быть больше 0
    private final int numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    @Override
    public int compareTo(House obj) {
        return this.year - obj.getYear();
    }
}