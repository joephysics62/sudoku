package joephysics62.co.uk.lsystems.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.LSystemGenerator;
import joephysics62.co.uk.lsystems.examples.BushExample3d;
import joephysics62.co.uk.lsystems.turtle.Turtle;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class LSystem3dWriter extends Application {

  private final LSystemGenerator _generator;

  public LSystem3dWriter() {
    _generator = new LSystemGenerator();
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setResizable(false);
    final Scene scene = new Scene(createContent(new BushExample3d(), 2));
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Parent createContent(final LSystem lsystem, final int iterations) {
    // Create and position camera
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.getTransforms().addAll(new Rotate(-20, Rotate.Y_AXIS), new Rotate(-20, Rotate.X_AXIS), new Translate(0, 0, -15));

    // Build the Scene Graph
    final Group root = new Group();
    root.getChildren().add(camera);
    root.getChildren().addAll(buildLsystemNodes(lsystem, iterations));
    // Use a SubScene
    final SubScene subScene = new SubScene(root, 600, 600);
    subScene.setFill(Color.WHITE);
    subScene.setCamera(camera);
    final Group group = new Group();
    group.getChildren().add(subScene);
    return group;
  }

  private List<Node> buildLsystemNodes(final LSystem lsystem, final int iterations) {
    final double cylinderLength = 1;
    final double cylinderWidth = 0.02;
    Point3D currentPoint = new Point3D(0, 0, 0);
    RealMatrix currentDirection = MatrixUtils.createRealIdentityMatrix(3);

    final double cosA = Math.cos(Math.toRadians(lsystem.angleDegrees()));
    final double sinA = Math.sin(Math.toRadians(lsystem.angleDegrees()));

    final RealMatrix positiveYaw = MatrixUtils.createRealMatrix(new double[][] {
            { cosA, sinA, 0},
            {-sinA, cosA, 0},
            {0, 0, 1}});
    final RealMatrix negativeYaw = MatrixUtils.createRealMatrix(new double[][] {
        {cosA, -sinA, 0},
        {sinA,  cosA, 0},
        {0, 0, 1}});
    final RealMatrix positivePitch = MatrixUtils.createRealMatrix(new double[][] {
        { cosA, 0, -sinA},
        {    0, 1,     0},
        { sinA, 0,  cosA}});
    final RealMatrix negativePitch = MatrixUtils.createRealMatrix(new double[][] {
        { cosA, 0,  sinA},
        {    0, 1,     0},
        {-sinA, 0,  cosA}});
    final RealMatrix positiveRoll = MatrixUtils.createRealMatrix(new double[][] {
        { 1, 0,        0},
        { 0, cosA, -sinA},
        { 0, sinA,  cosA}});
    final RealMatrix negativeRoll = MatrixUtils.createRealMatrix(new double[][] {
        { 1,     0,     0},
        { 0,  cosA,  sinA},
        { 0, -sinA,  cosA}});

    final Stack<Point3D> coordStack = new Stack<>();
    final Stack<RealMatrix> angles = new Stack<>();
    final List<Turtle> turtleData = _generator.generate(lsystem, iterations);

    final List<Node> nodes = new ArrayList<>();
    for (final Turtle turtle : turtleData) {
      if (turtle.moveUnits() != 0) {
        final double[] change = currentDirection.operate(new double[] {0, -cylinderLength, 0});
        final Point3D nextPoint = new Point3D(
            currentPoint.getX() + change[0],
            currentPoint.getY() + change[1],
            currentPoint.getZ() + change[2]
        );
        if (turtle.draw()) {
          final Cylinder cylinder = new Cylinder(cylinderWidth, cylinderLength);
          cylinder.getTransforms().add(new Translate(0, -0.5 * cylinderLength, 0));
          cylinder.getTransforms().add(new Translate(currentPoint.getX(), currentPoint.getY(), currentPoint.getZ()));
          final Sphere sphere = new Sphere(0.1);
          sphere.setMaterial(new PhongMaterial(Color.RED));
          cylinder.setMaterial(new PhongMaterial(Color.GREEN));
          nodes.add(cylinder);
          nodes.add(sphere);
        }
        currentPoint = nextPoint;
      }
      switch (turtle.stackChange()) {
      case PUSH:
        angles.push(currentDirection);
        coordStack.push(currentPoint);
        break;
      case POP:
        currentPoint = coordStack.pop();
        currentDirection = angles.pop();
        break;
      default:
        break;
      }
      switch (turtle.angleChange()) {
      case LEFT:
        currentDirection = currentDirection.multiply(positiveYaw);
        break;
      case RIGHT:
        currentDirection = currentDirection.multiply(negativeYaw);
        break;
      case UP:
        currentDirection = currentDirection.multiply(positivePitch);
        break;
      case DOWN:
        currentDirection = currentDirection.multiply(negativePitch);
        break;
      case ROLL_LEFT:
        currentDirection = currentDirection.multiply(positiveRoll);
        break;
      case ROLL_RIGHT:
        currentDirection = currentDirection.multiply(negativeRoll);
        break;
      default:
        break;
      }
    }
    return nodes;
  }

  public static void main(final String[] args) {
    launch(args);
  }

}
