package joephysics62.co.uk.ifs.nonlinear;

import java.io.IOException;

import joephysics62.co.uk.plotting.AnimatedPlottingWriter;
import joephysics62.co.uk.plotting.CyclicColorProvider;
import joephysics62.co.uk.plotting.ImageBuilder;
import joephysics62.co.uk.plotting.ImageScale;

import org.apache.commons.math3.complex.Complex;

public class Mandelbrot {

  private static int MAX_ITER = 150;

  public static void main(final String[] args) throws IOException {
    final double ymin = -1.2;
    final double ymax = 1.2;
    final double xmin = -3;
    final ImageScale imageScale = new ImageScale(600, 1.5, ymin, ymax, xmin);

    final double escapeLimit = 25;

    final String fileName = "C:\\Users\\joe\\Pictures\\mandel5.gif";

    final CyclicColorProvider cp = new CyclicColorProvider(MAX_ITER, 6);

    final ImageBuilder<Double> imageBuilder = new ImageBuilder<Double>(imageScale, cp) {
      @Override
      protected Double valueForPixel(final double dataX, final double dataY, final double animationProgress) {
        return mandelTime(new Complex(dataX, dataY), escapeLimit, animationProgress);
      }
    };
    imageBuilder.setPlotPredicate(d -> d >= 0);

    final AnimatedPlottingWriter<Double> animatedPlottingWriter = new AnimatedPlottingWriter<Double>(240, 24);
    animatedPlottingWriter.setImageBuilder(imageBuilder);

    animatedPlottingWriter.write(fileName);
  }

  private static double juliaEscapeTime(final Complex plotLocation, final double escapeLimit, final double animationProgress) {
    double currRe = plotLocation.getReal();
    double currIm = plotLocation.getImaginary();
    final double logOfMaxIterations = Math.log(MAX_ITER);
    final double logOfBase = Math.log(2);

    final Complex c = new Complex(0.285 - 0.01 * animationProgress, 0.01 + 0.01 * animationProgress);

    for (int i = 0; i < MAX_ITER; i++) {
      final double currReTemp = currRe * currRe - currIm * currIm + c.getReal();
      currIm = 2.0 * currRe * currIm + c.getImaginary();
      currRe = currReTemp;
      final double mod = Math.sqrt(currRe * currRe + currIm * currIm);
      if (mod > escapeLimit) {
        return i - (Math.log(Math.log(mod) / logOfMaxIterations) / logOfBase);
      }
    }
    return -1;
  }

  private static double mandelTime(final Complex plotLocation, final double escapeLimit, final double animationProgress) {
    final Complex zNought = new Complex(animationProgress, 0);
    double currRe = zNought.getReal();
    double currIm = zNought.getImaginary();
    final double logOfMaxIterations = Math.log(MAX_ITER);
    final double logOfBase = Math.log(2);

    for (int i = 0; i < MAX_ITER; i++) {
      final double currReTemp = currRe * currRe - currIm * currIm + plotLocation.getReal();
      currIm = 2.0 * currRe * currIm + plotLocation.getImaginary();
      currRe = currReTemp;
      final double mod = Math.sqrt(currRe * currRe + currIm * currIm);
      if (mod > escapeLimit) {
        return i - (Math.log(Math.log(mod) / logOfMaxIterations) / logOfBase);
      }
    }
    return -1;
  }

}
