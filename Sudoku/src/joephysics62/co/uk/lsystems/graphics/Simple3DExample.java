package joephysics62.co.uk.lsystems.graphics;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Simple3DExample extends Application {
  public Parent createContent() throws Exception {

    // Box
    final Cylinder cylinder = new Cylinder(0.1, 5);
    cylinder.setMaterial(new PhongMaterial(Color.GREEN));
    //testBox.setMaterial(new PhongMaterial(Color.RED));
    //testBox.setDrawMode(DrawMode.LINE);

    // Create and position camera
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.getTransforms().addAll(new Rotate(-20, Rotate.Y_AXIS),
        new Rotate(-20, Rotate.X_AXIS), new Translate(0, 0, -15));

    // Build the Scene Graph
    final Group root = new Group();
    root.getChildren().add(camera);
    root.getChildren().add(cylinder);

    // Use a SubScene
    final SubScene subScene = new SubScene(root, 600, 600);
    subScene.setFill(Color.WHITE);
    subScene.setCamera(camera);
    final Group group = new Group();
    group.getChildren().add(subScene);
    return group;
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setResizable(false);
    final Scene scene = new Scene(createContent());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Java main for when running without JavaFX launcher
   */
  public static void main(final String[] args) {
    launch(args);
  }
}
