package tools;

import content.Coordinates;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Вспомогательный класс для корректной сериализации/десериализации класса Coordinates
 */
public class CoordinatesAdapter extends XmlAdapter<String, Coordinates> {
    public Coordinates unmarshal(String v){
        return new Coordinates(Float.parseFloat(v.split(";")[0]), Long.parseLong(v.split(";")[1]));
    }
    public String marshal(Coordinates c){
        return c.getStringValue();
    }
}