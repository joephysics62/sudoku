package joephysics62.co.uk.lsystems.turtle;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public interface Listener {

  void drawLine(Point3D start, Point3D coord, Color color, double width);

}
