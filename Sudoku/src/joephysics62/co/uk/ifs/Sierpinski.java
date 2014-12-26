package joephysics62.co.uk.ifs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class Sierpinski implements IteratedFunctionSystem {

  private final List<Affine> _affines;
  private final List<Affine> _inverses;
  private final IntegerDistribution _eid;

  public Sierpinski() throws NonInvertibleTransformException {
    this(Math.sqrt(3) / 4.0, 0.25);
  }

  public Sierpinski(final double r, final double q) throws NonInvertibleTransformException {
    final double h = 0.5;
    _affines = Arrays.asList(
        from2d(-q, r, -r, -q, q, r),
        from2d(h, 0, 0, h, q, r),
        from2d(-q, -r, r, -q, 1, 0)
    );
    _inverses = new ArrayList<>();
    for (final Affine affine : _affines) {
      _inverses.add(affine.createInverse());
    }
    final double[] weights = new double[] {1, 1 , 1};
    _eid = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public Point3D transform(final Point3D input) {
    return _affines.get(_eid.sample()).transform(input);
  }

  @Override
  public Point3D inverseTransform(final int fnum, final Point3D input) {
    return _inverses.get(fnum).transform(input);
  }

  private static Affine from2d(final double xx, final double xy, final double yx, final double yy, final double tx, final double ty) {
    return new Affine(xx, xy, 0, tx,
                      yx, yy, 0, ty,
                      0 , 0 , 1, 0);
  }

}
