package joephysics62.co.uk.ifs;

import javafx.geometry.Point3D;

public interface IteratedFunctionSystem {
  Point3D transform(Point3D input);
  Point3D inverseTransform(int fnum, Point3D input);
}
