package joephysics62.co.uk.lsystems.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.LSystemGenerator;
import joephysics62.co.uk.lsystems.examples.BushExample3d;
import joephysics62.co.uk.lsystems.turtle.Turn;
import joephysics62.co.uk.lsystems.turtle.Turtle;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class LSystem3dWriter extends Application {

  private final LSystemGenerator _generator;
  private final double cameraDist = 10;
  private final double rotateChangeDeg = 5;
  private final double currentAngle = 0;
  private PerspectiveCamera _camera;

  public LSystem3dWriter() {
    _generator = new LSystemGenerator();
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setResizable(false);
    final Scene scene = new Scene(createContent(new BushExample3d(), 7));
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(final KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case Z:
          _camera.getTransforms().add(new Translate(0, 0, 1));
          break;
        case A:
          _camera.getTransforms().add(new Translate(0, 0, -1));
          break;
        case LEFT:
          _camera.getTransforms().add(new Rotate(-5, Rotate.Y_AXIS));
          break;
        case RIGHT:
          _camera.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
          break;
        default:
          break;
        }
      }
    });
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Parent createContent(final LSystem lsystem, final int iterations) {
    _camera = new PerspectiveCamera(true);
    _camera.getTransforms().add(new Rotate(-115, Rotate.X_AXIS));
    _camera.getTransforms().add(new Translate(0, -2, -15));

    // Build the Scene Graph
    final Group root = new Group();
    root.getChildren().add(_camera);
    root.getChildren().addAll(buildLsystemNodes(lsystem, iterations));
    // Use a SubScene
    final SubScene subScene = new SubScene(root, 600, 600);
    subScene.setFill(Color.WHITE);
    subScene.setCamera(_camera);
    final Group group = new Group();
    group.getChildren().add(subScene);
    return group;
  }

  private static RealMatrix yaw(final double angleDegrees) {
    final double sinA = Math.sin(Math.toRadians(angleDegrees));
    final double cosA = Math.cos(Math.toRadians(angleDegrees));
    return MatrixUtils.createRealMatrix(new double[][] {
        { cosA,    0, sinA},
        {    0,    1, 0},
        {-sinA,    0, cosA}});
  }

  private static RealMatrix pitch(final double angleDegrees) {
    final double sinA = Math.sin(Math.toRadians(angleDegrees));
    final double cosA = Math.cos(Math.toRadians(angleDegrees));
    return MatrixUtils.createRealMatrix(new double[][] {
        { 1,     0,    0},
        { 0,  cosA, sinA},
        { 0, -sinA, cosA}});
  }

  private static RealMatrix roll(final double angleDegrees) {
    final double sinA = Math.sin(Math.toRadians(angleDegrees));
    final double cosA = Math.cos(Math.toRadians(angleDegrees));
    return MatrixUtils.createRealMatrix(new double[][] {
        { cosA, -sinA, 0},
        { sinA, cosA,  0},
        {     0,    0, 1}});
  }

  private List<Node> buildLsystemNodes(final LSystem lsystem, final int iterations) {
    final double stepSize = 0.1;
    double currentWidth = 0.03;
    Point3D currentPoint = new Point3D(0, 0, 0);
    RealMatrix currentDirection = MatrixUtils.createRealIdentityMatrix(3);

    final Stack<Point3D> coordStack = new Stack<>();
    final Stack<RealMatrix> angles = new Stack<>();
    final Stack<Double> widths = new Stack<>();
    final List<Turtle> turtleData = _generator.generate(lsystem, iterations);

    final List<Node> nodes = new ArrayList<>();
    for (final Turtle turtle : turtleData) {
      if (turtle.moveUnits() != 0) {
        final double[] change = currentDirection.operate(new double[] {0, 0, stepSize});
        final Point3D nextPoint = new Point3D(
            currentPoint.getX() + change[0],
            currentPoint.getY() + change[1],
            currentPoint.getZ() + change[2]
        );
        if (turtle.draw()) {
          nodes.add(connectingCylinder(currentPoint, nextPoint, currentWidth));
        }
        currentPoint = nextPoint;
      }
      switch (turtle.getWidthChange()) {
      case GROW:
        currentWidth *= 1.6;
        break;
      case NARROW:
        currentWidth /= 1.6;
        break;
      case NONE:
      default:
        break;
      }
      switch (turtle.stackChange()) {
      case PUSH:
        angles.push(currentDirection);
        coordStack.push(currentPoint);
        widths.push(currentWidth);
        break;
      case POP:
        currentPoint = coordStack.pop();
        currentDirection = angles.pop();
        currentWidth = widths.pop();
        break;
      default:
        break;
      }
      final RealMatrix rotationMatrix = rotationMatrix(turtle.angleChange(), lsystem.angleDegrees());
      currentDirection = currentDirection.multiply(rotationMatrix);
    }
    return nodes;
  }

  private Cylinder connectingCylinder(final Point3D target, final Point3D source, final double radius) {
    final Cylinder cylinder = new Cylinder(radius, target.distance(source));
    final Point3D midpoint = target.midpoint(source);
    cylinder.getTransforms().add(new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ()));
    final Point3D diff = target.subtract(source);
    cylinder.getTransforms().add(new Rotate(-diff.angle(Rotate.Y_AXIS), diff.crossProduct(Rotate.Y_AXIS)));
    cylinder.setMaterial(new PhongMaterial(Color.GREEN));
    return cylinder;
  }

  private static RealMatrix rotationMatrix(final Turn turn, final Double angle) {
    switch (turn) {
    case LEFT:
      return yaw(angle);
    case RIGHT:
      return yaw(-angle);
    case UP:
      return pitch(angle);
    case DOWN:
      return pitch(-angle);
    case ROLL_LEFT:
      return roll(angle);
    case ROLL_RIGHT:
      return roll(-angle);
    default:
      return MatrixUtils.createRealIdentityMatrix(3);
    }
  }

  public static void main(final String[] args) {
    launch(args);
  }

}
