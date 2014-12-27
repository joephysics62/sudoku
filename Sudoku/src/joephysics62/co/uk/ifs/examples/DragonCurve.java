package joephysics62.co.uk.ifs.examples;

import java.util.Arrays;

import javafx.scene.transform.NonInvertibleTransformException;
import joephysics62.co.uk.ifs.AffineIFS;
import joephysics62.co.uk.ifs.IteratedFunctionSystem;

public class DragonCurve extends AffineIFS {

  private static final int DEFAULT_B = 135;
  private static final int DEFAULT_A = 45;

  public DragonCurve() throws NonInvertibleTransformException {
    this(DEFAULT_A, DEFAULT_B);
  }

  public DragonCurve(final double A, final double B) throws NonInvertibleTransformException {
    super(Arrays.asList(
        IteratedFunctionSystem.from2d(cos(A), -sin(A), sin(A), cos(A), 0, 0),
        IteratedFunctionSystem.from2d(cos(B), -sin(B), sin(B), cos(B), 1, 0)
    ), new double[] {0.5, 0.5});
  }

  private static double cos(final double A) {
    return Math.cos(Math.toRadians(A)) / Math.sqrt(2);
  }

  private static double sin(final double A) {
    return Math.sin(Math.toRadians(A)) / Math.sqrt(2);
  }

}
