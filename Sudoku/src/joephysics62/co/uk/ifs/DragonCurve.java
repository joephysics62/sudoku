package joephysics62.co.uk.ifs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class DragonCurve implements IteratedFunctionSystem {

  private static final int DEFAULT_B = 135;
  private static final int DEFAULT_A = 45;
  private final List<Affine> _affines;
  private final IntegerDistribution _eid;

  public DragonCurve() {
    this(DEFAULT_A, DEFAULT_B);
  }

  public DragonCurve(final double A, final double B) {
    final double sinA = Math.sin(Math.toRadians(A)) / Math.sqrt(2);
    final double cosA = Math.cos(Math.toRadians(A)) / Math.sqrt(2);
    final double sinB = Math.sin(Math.toRadians(B)) / Math.sqrt(2);
    final double cosB = Math.cos(Math.toRadians(B)) / Math.sqrt(2);

    _affines = Arrays.asList(
        from2d(cosA, -sinA, sinA, cosA, 0, 0),
        from2d(cosB, -sinB, sinB, cosB, 1, 0)
    );
    final double[] weights = new double[] {0.5, 0.5};
    _eid = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public Point3D inverseTransform(final int fnum, final Point3D input) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Point3D transform(final Point3D input) {
    return _affines.get(_eid.sample()).transform(input);
  }

  private static Affine from2d(final double xx, final double xy, final double yx, final double yy, final double tx, final double ty) {
    return new Affine(xx, xy, 0, tx,
                      yx, yy, 0, ty,
                      0 , 0 , 1, 0);
  }

}
