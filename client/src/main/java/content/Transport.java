package content;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс для хранения вида транспорта собственника квартиры
 */
public enum Transport implements Serializable {
    FEW,
    LITTLE,
    NORMAL;

    public static void TransportToString(){
        System.out.println("Список возможных транспортов: " + Arrays.toString(Transport.values()));
    }
}