package joephysics62.co.uk.old.lsystems.graphics;

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

public abstract class Simple3dViewer extends Application {

  private PerspectiveCamera _camera;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setResizable(false);
    final Scene scene = new Scene(createContent());
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
          _output.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
          break;
        case RIGHT:
          _output.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
          break;
        case UP:
          _output.getTransforms().add(new Translate(0, 0, 1));
          break;
        case DOWN:
          _output.getTransforms().add(new Translate(0, 0, -1));
          break;
        default:
          break;
        }
      }
    });
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private static final double START_DISTANCE = 15;

  private Group _output;

  private Parent createContent() {
    _camera = new PerspectiveCamera(true);
    _camera.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
    _camera.getTransforms().add(new Rotate(-115, Rotate.X_AXIS));
    _camera.getTransforms().add(new Translate(0, -2, -START_DISTANCE));

    // Build the Scene Graph
    final Group root = new Group();
    root.getChildren().add(_camera);

    _output = createContentGroup();
    root.getChildren().add(_output);

    // Use a SubScene
    final SubScene subScene = new SubScene(root, 800, 600);
    subScene.setFill(Color.BLACK);
    subScene.setCamera(_camera);
    final Group group = new Group();
    group.getChildren().add(subScene);
    return group;
  }

  protected abstract Group createContentGroup();

}
