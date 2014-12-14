package joephysics62.co.uk.lsystems.graphics;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import joephysics62.co.uk.lsystems.turtle.TurtleListener;

public class JavaFXListener implements TurtleListener {

  private final Group _sceneRootGroup;

  public JavaFXListener(final Group sceneRootGroup) {
    _sceneRootGroup = sceneRootGroup;
  }

  @Override
  public void drawLine(final Point3D start, final Point3D coord, final Color color, final double width) {
    _sceneRootGroup.getChildren().add(connectingCylinder(coord, start, color, width / 2));
  }

  private Cylinder connectingCylinder(final Point3D target, final Point3D source, final Color color, final double radius) {
    final Cylinder cylinder = new Cylinder(radius, target.distance(source));
    final Point3D midpoint = target.midpoint(source);
    cylinder.getTransforms().add(new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ()));
    final Point3D diff = target.subtract(source);
    cylinder.getTransforms().add(new Rotate(-diff.angle(Rotate.Y_AXIS), diff.crossProduct(Rotate.Y_AXIS)));
    cylinder.setMaterial(new PhongMaterial(color));
    return cylinder;
  }

}
