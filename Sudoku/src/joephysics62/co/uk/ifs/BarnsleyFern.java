package joephysics62.co.uk.ifs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class BarnsleyFern implements IteratedFunctionSystem {

  private final List<Affine> _affines;
  private final IntegerDistribution _eid;

  public BarnsleyFern(final double xyValue) {
    _affines = Arrays.asList(
        from2d(0, 0, 0, 0.16, 0, 0),
        from2d(0.85, xyValue, -xyValue, 0.85, 0, 1.6),
        from2d(0.2, -.26, .23, .22, 0, 1.6),
        from2d(-.15, .28, .26, 0.24, 0, 0.44)
    );
    final double[] weights = new double[] {0.01, .85, .07, .07};
    _eid = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
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
