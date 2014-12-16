package joephysics62.co.uk.lsystems.graphics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
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
import joephysics62.co.uk.lsystems.LSystemGenerator;
import joephysics62.co.uk.lsystems.LSystemTurtleInterpreter;
import joephysics62.co.uk.lsystems.Line3D;
import joephysics62.co.uk.lsystems.examples.BushExample3d;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;
import joephysics62.co.uk.lsystems.turtle.TurtleState;

public class LSystem3dWriter extends Application {

  private static final int ITERATIONS = 6;
  private static final double DRAW_STEP = 0.1;
  private final LSystemGenerator _generator;
  private PerspectiveCamera _camera;

  public LSystem3dWriter() {
    _generator = new LSystemGenerator();
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setResizable(false);
    final Scene scene = new Scene(createContent(new BushExample3d(), ITERATIONS));
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

  private Parent createContent(final TurtleLSystem lsystem, final int iterations) {
    _camera = new PerspectiveCamera(true);
    _camera.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
    _camera.getTransforms().add(new Rotate(-115, Rotate.X_AXIS));
    _camera.getTransforms().add(new Translate(0, -2, -15));

    // Build the Scene Graph
    final Group root = new Group();
    root.getChildren().add(_camera);

    final TurtleState initialState = new TurtleState(new Point3D(0, 0, 0), Rotate.Z_AXIS, Rotate.X_AXIS, 0.03, Color.BROWN);

    final LSystemTurtleInterpreter turtleInterpreter = new LSystemTurtleInterpreter(lsystem.angle(), DRAW_STEP, lsystem.narrowing());
    final String result = _generator.generate(lsystem, iterations);
    for (final Line3D line3d : turtleInterpreter.interpret(result, initialState)) {
      root.getChildren().add(connectingCylinder(line3d.getStart(), line3d.getEnd(), line3d.getColour(), line3d.getWidth() / 2));
    }
    // Use a SubScene
    final SubScene subScene = new SubScene(root, 600, 600);
    subScene.setFill(Color.WHITE);
    subScene.setCamera(_camera);
    final Group group = new Group();
    group.getChildren().add(subScene);
    return group;
  }


  public static void main(final String[] args) {
    launch(args);
  }

  private static Cylinder connectingCylinder(final Point3D source, final Point3D target, final Color color, final double radius) {
    final Cylinder cylinder = new Cylinder(radius, target.distance(source));
    final Point3D midpoint = target.midpoint(source);
    cylinder.getTransforms().add(new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ()));
    final Point3D diff = target.subtract(source);
    cylinder.getTransforms().add(new Rotate(-diff.angle(Rotate.Y_AXIS), diff.crossProduct(Rotate.Y_AXIS)));
    cylinder.setMaterial(new PhongMaterial(color));
    return cylinder;
  }

}
