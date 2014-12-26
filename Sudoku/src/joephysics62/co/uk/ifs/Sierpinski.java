package joephysics62.co.uk.ifs;

import java.util.Arrays;

import javafx.scene.transform.NonInvertibleTransformException;

public class Sierpinski extends AffineIFS {
  private static final double H = 0.5;

  public Sierpinski() throws NonInvertibleTransformException {
    this(Math.sqrt(3) / 4.0, 0.25);
  }

  public Sierpinski(final double r, final double q) throws NonInvertibleTransformException {
    super(
        Arrays.asList(
          IteratedFunctionSystem.from2d(-q, r, -r, -q, q, r),
          IteratedFunctionSystem.from2d(H, 0, 0, H, q, r),
          IteratedFunctionSystem.from2d(-q, -r, r, -q, 1, 0)
        ),
        new double[] {1, 1 , 1}
    );
  }

}
