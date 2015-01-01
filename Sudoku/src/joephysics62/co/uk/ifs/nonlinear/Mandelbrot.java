package joephysics62.co.uk.ifs.nonlinear;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import joephysics62.co.uk.plotting.AnimatedPlottingWriter;
import joephysics62.co.uk.plotting.ImageScale;

import org.apache.commons.math3.complex.Complex;

public class Mandelbrot {

  private static int MAX_ITER = 30;

  public static void main(final String[] args) throws IOException {
    final double ymin = -2;
    final double ymax = 2;
    final double xmin = -3;
    final ImageScale imageScale = new ImageScale(600, 1.5, ymin, ymax, xmin);

    final double escapeLimit = 15;

    final String fileName = "C:\\Users\\joe\\Pictures\\mandelbrot4.gif";

    final AnimatedPlottingWriter animatedPlottingWriter = new AnimatedPlottingWriter(480, 24) {
      @Override
      protected BufferedImage createImage(final double progress) {
        final BufferedImage bi = new BufferedImage(imageScale.getWidth(), imageScale.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < imageScale.getWidth(); x++) {
          for (int y = 0; y < imageScale.getHeight(); y++) {
            final Complex c = new Complex(imageScale.dataX(x), imageScale.dataY(y));
            final double escapes = escapeTime(c, escapeLimit, progress);
            if (escapes >= 0) {
              bi.setRGB(x, y, toRGB(escapes));
            }
          }
        }
        return bi;
      }
    };

    animatedPlottingWriter.write(fileName);
  }

  private static int toRGB(final double value) {
    final int cycles = 2;
    final double angle = 2 * cycles * Math.PI * value / MAX_ITER;
    final int sine = (int) (255 * (Math.sin(angle) + 1) / 2.0);
    final int cosine = (int) (255 * (Math.cos(angle) + 1) / 2.0);

    return new Color(sine, cosine, 0).getRGB();
  }

  private static double escapeTime(final Complex c, final double escapeLimit, final double animationProgress) {
    final double a = 2.0 + 2.0 * animationProgress;
    final Complex zNought = new Complex(0, 0);
    double currRe = zNought.getReal();
    double currIm = zNought.getImaginary();
    final double logOfMaxIterations = Math.log(MAX_ITER);
    final double logOfBase = Math.log(2);

    for (int i = 0; i < MAX_ITER; i++) {
      final double currReTemp = currRe * currRe - currIm * currIm + c.getReal();
      currIm = a * currRe * currIm + c.getImaginary();
      currRe = currReTemp;
      final double mod = Math.sqrt(currRe * currRe + currIm * currIm);
      if (mod > escapeLimit) {
        return i - (Math.log(Math.log(mod) / logOfMaxIterations) / logOfBase);
      }
    }
    return -1;
  }

}
