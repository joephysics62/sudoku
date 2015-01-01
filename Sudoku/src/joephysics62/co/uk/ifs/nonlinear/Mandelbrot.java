package joephysics62.co.uk.ifs.nonlinear;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.lsystems.animation.GifSequenceWriter;
import joephysics62.co.uk.plotting.ImageScale;

import org.apache.commons.math3.complex.Complex;

public class Mandelbrot {

  private static int MAX_ITER = 30;

  public static void main(final String[] args) throws IOException {
    final double ymin = -1;
    final double ymax = 1;
    final double xmin = -2;
    final ImageScale imageScale = new ImageScale(600, 1.5, ymin, ymax, xmin);

    final double escapeLimit = 15;

    final String fileName = "C:\\Users\\joe\\Pictures\\mandelbrot.gif";

    final int numFrames = 240;
    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

      for (int i = 0; i < numFrames; i++) {
        final BufferedImage bi = new BufferedImage(imageScale.getWidth(), imageScale.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < imageScale.getWidth(); x++) {
          for (int y = 0; y < imageScale.getHeight(); y++) {
            final Complex c = new Complex(imageScale.dataX(x), imageScale.dataY(y));
            final double a = 1.0 * i / numFrames;
            final Complex zNought = new Complex(a, a);
            final double escapes = escapeTime(c, zNought, escapeLimit);
            if (escapes >= 0) {
              bi.setRGB(x, y, new Color(intensity(escapes), intensity(escapes), intensity(escapes)).getRGB());
            }
          }
        }
        gifSequenceWriter.writeToSequence(bi);
        System.out.println("Done FRAME " + i);
      }
    }

  }

  private static int intensity(final double i) {
    return Math.min(255, (int) (255.0 * i / MAX_ITER));
  }

  private static double escapeTime(final Complex c, final Complex zNought, final double escapeLimit) {
    double currRe = zNought.getReal();
    double currIm = zNought.getImaginary();
    final double logOfMaxIterations = Math.log(MAX_ITER);
    final double logOfBase = Math.log(2);

    for (int i = 0; i < MAX_ITER; i++) {
      final double currReTemp = currRe * currRe - currIm * currIm + c.getReal();
      currIm = 2.3 * currRe * currIm + c.getImaginary();
      currRe = currReTemp;
      final double mod = Math.sqrt(currRe * currRe + currIm * currIm);
      if (mod > escapeLimit) {
        return i - (Math.log(Math.log(mod) / logOfMaxIterations) / logOfBase);
      }
    }
    return -1;
  }

}
