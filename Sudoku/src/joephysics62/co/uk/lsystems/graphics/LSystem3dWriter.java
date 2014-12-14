package joephysics62.co.uk.lsystems.graphics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import joephysics62.co.uk.lsystems.LSystemGenerator;
import joephysics62.co.uk.lsystems.examples.BushExample3d;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;
import joephysics62.co.uk.lsystems.turtle.TurtleListener;

public class LSystem3dWriter extends Application {

  private final LSystemGenerator _generator;
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

  private Parent createContent(final TurtleLSystem lsystem, final int iterations) {
    _camera = new PerspectiveCamera(true);
    _camera.getTransforms().add(new Rotate(-115, Rotate.X_AXIS));
    _camera.getTransforms().add(new Translate(0, -2, -15));

    // Build the Scene Graph
    final Group root = new Group();
    root.getChildren().add(_camera);
    final TurtleListener javaFXListener = new JavaFXListener(root);
    final LSystemTurtleInterpreter turtleInterpreter = new LSystemTurtleInterpreter(javaFXListener, lsystem.angle(), 0.1, 1.6);
    turtleInterpreter.interpret(_generator.generate(lsystem, iterations));
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

}
