package joephysics62.co.uk.ifs.examples;

import java.util.Arrays;

import javafx.scene.transform.NonInvertibleTransformException;
import joephysics62.co.uk.ifs.AffineIFS;
import joephysics62.co.uk.ifs.IteratedFunctionSystem;

public class BarnsleyFern extends AffineIFS {

  public BarnsleyFern(final double xyValue) throws NonInvertibleTransformException {
    super(Arrays.asList(
        //IteratedFunctionSystem.from2d(0, 0, 0, 0.16, 0, 0),
        IteratedFunctionSystem.from2d(0.85, xyValue, -xyValue, 0.85, 0, 1.6),
        IteratedFunctionSystem.from2d(0.2, -.26, .23, .22, 0, 1.6),
        IteratedFunctionSystem.from2d(-.15, .28, .26, 0.24, 0, 0.44)
    ), new double[] {0.01, .85, .07, .07});
  }

}
