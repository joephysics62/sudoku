package joephysics62.co.uk.ifs.nonlinear;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.math3.complex.Complex;

public class EscapeTime {

  public double calculate(final Supplier<Complex> initial, final Function<Complex, Complex> mapping, final double escapeLimit, final int maxIterations) {
    Complex curr = initial.get();
    final double logOfMaxIterations = Math.log(maxIterations);
    final double logOfBase = Math.log(2);

    for (int i = 0; i < maxIterations; i++) {
      final double mod = curr.abs();
      if (mod > escapeLimit) {
        return i - (Math.log(Math.log(mod) / logOfMaxIterations) / logOfBase);
      }
      curr = mapping.apply(curr);
    }
    return -1;
  }

}
