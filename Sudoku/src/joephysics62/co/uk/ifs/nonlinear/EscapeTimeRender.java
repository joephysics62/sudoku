package joephysics62.co.uk.ifs.nonlinear;

import java.io.IOException;

import joephysics62.co.uk.plotting.AnimatedPlottingWriter;
import joephysics62.co.uk.plotting.CyclicColorProvider;
import joephysics62.co.uk.plotting.ImageBuilder;
import joephysics62.co.uk.plotting.ImageScale;

import org.apache.commons.math3.complex.Complex;

public class EscapeTimeRender {

  private static int MAX_ITER = 150;

  public static void main(final String[] args) throws IOException {
    final double ymin = -1.2;
    final double ymax = 1.2;
    final double xmin = -1.5;
    final ImageScale imageScale = new ImageScale(600, 1.5, ymin, ymax, xmin);

    final double escapeLimit = 25;

    final String fileName = "C:\\Users\\joe\\Pictures\\mandel6.gif";

    final CyclicColorProvider cp = new CyclicColorProvider(MAX_ITER, 6);

    final ImageBuilder<Double> imageBuilder = mandelbrotBuilder(imageScale, escapeLimit, cp);
    imageBuilder.setPlotPredicate(d -> d >= 0);

    final AnimatedPlottingWriter<Double> animatedPlottingWriter = new AnimatedPlottingWriter<Double>(1, 2);
    animatedPlottingWriter.setImageBuilder(imageBuilder);

    animatedPlottingWriter.write(fileName);
  }

  private static ImageBuilder<Double> juliaSetBuilder(final ImageScale imageScale, final double escapeLimit, final CyclicColorProvider cp) {
    final EscapeTime esc = new EscapeTime();
    final ImageBuilder<Double> imageBuilder = new ImageBuilder<Double>(imageScale, cp) {
      @Override
      protected Double valueForPixel(final double dataX, final double dataY, final double animationProgress) {
        final Complex plotLocation = new Complex(dataX, dataY);
        return esc.calculate(
            () -> plotLocation,
            c -> c.multiply(c).add(new Complex(-0.7, 0.5)),
            escapeLimit,
            MAX_ITER
        );
      }
    };
    return imageBuilder;
  }

  private static ImageBuilder<Double> mandelbrotBuilder(final ImageScale imageScale, final double escapeLimit,
      final CyclicColorProvider cp) {
    final EscapeTime esc = new EscapeTime();
    final ImageBuilder<Double> imageBuilder = new ImageBuilder<Double>(imageScale, cp) {
      @Override
      protected Double valueForPixel(final double dataX, final double dataY, final double animationProgress) {
        final Complex plotLocation = new Complex(dataX, dataY);
        return esc.calculate(
            () -> Complex.ZERO,
            c -> c.multiply(c).add(plotLocation),
            escapeLimit,
            MAX_ITER
        );
      }
    };
    return imageBuilder;
  }

}
