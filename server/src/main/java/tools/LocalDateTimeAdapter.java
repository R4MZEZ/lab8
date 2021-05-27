package tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Вспомогательный класс для корректной сериализации/десериализации класса LocalDateTime (класс времени)
 */
public class LocalDateTimeAdapter{
    public static LocalDateTime unmarshal(String v){
        return LocalDateTime.of(Integer.parseInt(v.split(",")[0].split("\\.")[2]),
                                Integer.parseInt(v.split(",")[0].split("\\.")[1]),
                                Integer.parseInt(v.split(",")[0].split("\\.")[0]),
                                Integer.parseInt(v.split(",")[1].split(":")[0]),
                                Integer.parseInt(v.split(",")[1].split(":")[1]),
                                Integer.parseInt(v.split(",")[1].split(":")[2]));
    }
    public String marshal(LocalDateTime l) {
        return l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mi:ss"));
    }
}
