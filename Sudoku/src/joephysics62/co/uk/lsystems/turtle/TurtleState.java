package joephysics62.co.uk.lsystems.turtle;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class TurtleState {

  private final Point3D _coord;
  private final double _width;
  private final Color _colour;
  private final RealMatrix _angle;

  private static final double[] INITIAL_DIRECTION = {0, 0, 1};

  public TurtleState(final Point3D location, final RealMatrix angle, final double width, final Color colour) {
    _coord = location;
    _angle = angle;
    _width = width;
    _colour = colour;
  }

  public RealMatrix getAngle() {
    return _angle;
  }
  public Color getColor() {
    return _colour;
  }
  public Point3D getCoord() {
    return _coord;
  }
  public double getWidth() {
    return _width;
  }

  public TurtleState changeWidth(final double factor) {
    return new TurtleState(_coord, _angle, factor * _width, _colour);
  }

  public TurtleState changeColour(final Color colour) {
    return new TurtleState(_coord, _angle, _width, colour);
  }

  public TurtleState move(final double distance) {
    final double[] operate = _angle.operate(INITIAL_DIRECTION);
    return new TurtleState(_coord.add(new Point3D(operate[0], operate[1], operate[2])), _angle, _width, _colour);
  }

  public TurtleState turn(final double angleDegrees) {
    return new TurtleState(_coord, yawMatrix(angleDegrees).multiply(_angle), _width, _colour);
  }

  public TurtleState pitch(final double angleDegrees) {
    return new TurtleState(_coord, pitchMatrix(angleDegrees).multiply(_angle), _width, _colour);
  }

  public TurtleState roll(final double angleDegrees) {
    return new TurtleState(_coord, rollMatrix(angleDegrees).multiply(_angle), _width, _colour);
  }

  private static RealMatrix yawMatrix(final double angleDegrees) {
    final double sinA = Math.sin(Math.toRadians(angleDegrees));
    final double cosA = Math.cos(Math.toRadians(angleDegrees));
    return MatrixUtils.createRealMatrix(new double[][] {
        { cosA,    0, sinA},
        {    0,    1, 0},
        {-sinA,    0, cosA}});
  }

  private static RealMatrix pitchMatrix(final double angleDegrees) {
    final double sinA = Math.sin(Math.toRadians(angleDegrees));
    final double cosA = Math.cos(Math.toRadians(angleDegrees));
    return MatrixUtils.createRealMatrix(new double[][] {
        { 1,     0,    0},
        { 0,  cosA, sinA},
        { 0, -sinA, cosA}});
  }

  private static RealMatrix rollMatrix(final double angleDegrees) {
    final double sinA = Math.sin(Math.toRadians(angleDegrees));
    final double cosA = Math.cos(Math.toRadians(angleDegrees));
    return MatrixUtils.createRealMatrix(new double[][] {
        { cosA, -sinA, 0},
        { sinA, cosA,  0},
        {     0,    0, 1}});
  }


}
