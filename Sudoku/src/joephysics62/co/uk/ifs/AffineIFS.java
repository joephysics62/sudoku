package joephysics62.co.uk.ifs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public abstract class AffineIFS implements IteratedFunctionSystem {

  private final List<Affine> _affines;
  private final List<Affine> _inverses;
  private final IntegerDistribution _eid;

  public AffineIFS(final List<Affine> affines, final double[] weights) throws NonInvertibleTransformException {
    _affines = affines;
    _inverses = new ArrayList<>();
    for (final Affine affine : _affines) {
      _inverses.add(affine.createInverse());
    }
   _eid = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public final Point3D transform(final Point3D input) {
    return _affines.get(_eid.sample()).transform(input);
  }

  @Override
  public final Point3D inverseTransform(final int fnum, final Point3D input) {
    return _inverses.get(fnum).transform(input);
  }

  @Override
  public int numTransforms() {
    return _affines.size();
  }

}
