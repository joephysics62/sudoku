package joephysics62.co.uk.old.lsystems.graphics;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ConnectingCylinder extends Cylinder {

  public ConnectingCylinder(final Point3D source, final Point3D target, final Color color, final double radius) {
    super(radius, target.distance(source));
    final Point3D midpoint = target.midpoint(source);
    getTransforms().add(new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ()));
    final Point3D diff = target.subtract(source);
    getTransforms().add(new Rotate(-diff.angle(Rotate.Y_AXIS), diff.crossProduct(Rotate.Y_AXIS)));
    setMaterial(new PhongMaterial(color));
  }

}
