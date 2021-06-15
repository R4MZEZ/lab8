package content;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс для хранения видов из окна квартиры
 */
public enum View implements Serializable {
    YARD,
    BAD,
    NORMAL,
    GOOD,
    TERRIBLE;

    public static String ViewToString(){
        return (Arrays.toString(View.values()));
    }
}