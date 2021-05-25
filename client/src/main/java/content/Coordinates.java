package content;

import java.io.Serializable;

/**
 * Класс для хранения координат квартиры
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 4325446441307549186L;

    public Coordinates(Float x, long y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public String getStringValue(){ return x + ";" + y; }


    private final Float x; //Поле не может быть null
    private final long y; //Максимальное значение поля: 368


}