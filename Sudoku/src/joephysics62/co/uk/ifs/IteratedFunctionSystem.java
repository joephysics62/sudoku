package joephysics62.co.uk.ifs;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;

public interface IteratedFunctionSystem {
  Point3D transform(Point3D input);
  Point3D inverseTransform(int fnum, Point3D input);
  int numTransforms();


  public static Affine from2d(final double xx, final double xy, final double yx, final double yy, final double tx, final double ty) {
    return new Affine(xx, xy, 0, tx,
                      yx, yy, 0, ty,
                      0 , 0 , 1, 0);
  }
}
