package joephysics62.co.uk.lsystems.turtle;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class TurtleState {

  private final Point3D _coord;
  private final double _width;
  private final Point3D _heading;
  private final Point3D _left;
  private final Color _colour;

  public TurtleState(final Point3D location, final Point3D heading, final Point3D left, final double width, final Color colour) {
    _coord = location;
    _heading = heading;
    _left = left;
    _width = width;
    _colour = colour;
  }

  public Color getColour() {
    return _colour;
  }

  public Point3D getHeading() {
    return _heading;
  }
  public Point3D getLeft() {
    return _left;
  }
  public Point3D getCoord() {
    return _coord;
  }
  public double getWidth() {
    return _width;
  }

  public TurtleState changeWidth(final double factor) {
    return new TurtleState(_coord, _heading, _left, factor * _width, _colour);
  }

  public TurtleState changeColour(final Color colour) {
    return new TurtleState(_coord, _heading, _left, _width, colour);
  }

  public TurtleState move(final double distance) {
    return new TurtleState(_coord.add(_heading.multiply(distance)), _heading, _left, _width, _colour);
  }

  public TurtleState turn(final double angleDegrees) {
    final Rotate rotate = new Rotate(angleDegrees, _heading.crossProduct(_left));
    return new TurtleState(_coord, rotate.transform(_heading), rotate.transform(_left), _width, _colour);
  }

  public TurtleState pitch(final double angleDegrees) {
    final Rotate rotate = new Rotate(angleDegrees, _left);
    return new TurtleState(_coord, rotate.transform(_heading), rotate.transform(_left), _width, _colour);
  }

  public TurtleState roll(final double angleDegrees) {
    final Rotate rotate = new Rotate(angleDegrees, _heading);
    return new TurtleState(_coord, rotate.transform(_heading), rotate.transform(_left), _width, _colour);
  }

}
