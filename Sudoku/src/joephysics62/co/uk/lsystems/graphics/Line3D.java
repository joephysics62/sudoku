package joephysics62.co.uk.lsystems.graphics;

import javafx.geometry.Point3D;

public class Line3D {
  private final Point3D _start;
  private final Point3D _end;
  private final int _colour;
  private final double _width;

  public Line3D(final Point3D start, final Point3D end, final int colour, final double width) {
    _start = start;
    _end = end;
    _colour = colour;
    _width = width;
  }
  public int getColour() {
    return _colour;
  }
  public Point3D getStart() {
    return _start;
  }
  public Point3D getEnd() {
    return _end;
  }
  public double getWidth() {
    return _width;
  }
}