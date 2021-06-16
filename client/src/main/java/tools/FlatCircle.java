package tools;

import content.Flat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FlatCircle extends Circle {
    private Flat flat;

    public FlatCircle(int centerX, int centerY, long radius, Color color) {
        super(centerX,centerY,radius,color);
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }
}
