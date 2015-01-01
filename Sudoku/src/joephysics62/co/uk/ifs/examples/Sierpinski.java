package joephysics62.co.uk.ifs.examples;

import java.util.Arrays;

import javafx.scene.transform.NonInvertibleTransformException;
import joephysics62.co.uk.ifs.AffineIFS;
import joephysics62.co.uk.ifs.IteratedFunctionSystem;

public class Sierpinski extends AffineIFS {
  private static final double DEFAULT_R = Math.sqrt(3) / 4.0;
  private static final double DEFAULT_Q = 0.25;
  private static final double DEFAULT_H = 0.5;

  public Sierpinski() throws NonInvertibleTransformException {
    this(DEFAULT_R, DEFAULT_Q, DEFAULT_H);
  }

  public Sierpinski(final double r, final double q, final double h) throws NonInvertibleTransformException {
    super(
        Arrays.asList(
          IteratedFunctionSystem.from2d(-q, r, -r, -q, q, r),
          IteratedFunctionSystem.from2d(h, 0, 0, h, q, r),
          IteratedFunctionSystem.from2d(-q, -r, r, -q, 1, 0)
        ),
        new double[] {1, 1 , 1}
    );
  }

}
