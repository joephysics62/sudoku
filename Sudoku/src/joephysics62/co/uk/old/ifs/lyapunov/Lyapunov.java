package joephysics62.co.uk.old.ifs.lyapunov;

import java.awt.Color;
import java.io.IOException;

import joephysics62.co.uk.old.plotting.AnimatedPlottingWriter;
import joephysics62.co.uk.old.plotting.ColorProvider;
import joephysics62.co.uk.old.plotting.ImageBuilder;
import joephysics62.co.uk.old.plotting.ImageScale;

public class Lyapunov {

  private final String _pattern;
  private final double _init;

  public static void main(final String[] args) throws IOException {
    final String fileName = "C:\\Users\\joe\\Pictures\\lyapunov4.gif";
    final AnimatedPlottingWriter<Double> plottingWriter = new AnimatedPlottingWriter<Double>(1, 1);
    final ImageScale imageScale = new ImageScale(600, 1, 0, 4, 0);
    final ColorProvider<Double> colorProvider = new ColorProvider<Double>() {

      @Override
      public Color getColouring(final Double value) {
        final int a = Math.min(255, (int) (100 * Math.abs(value)));
        return new Color(a, a, a);
      }

    };
    plottingWriter.setImageBuilder(new ImageBuilder<Double>(imageScale, colorProvider) {

      @Override
      protected Double valueForPixel(final double dataX, final double dataY, final double animationProgress) {
        final Lyapunov lyapunov = new Lyapunov("AAAABBBAAB", 0.5 * animationProgress + 0.5);
        final double exponent = lyapunov.exponent(dataX, dataY, 20);
        return exponent;
      }
    });
    plottingWriter.write(fileName);
  }

  public Lyapunov(final String pattern, final double init)  {
    _pattern = pattern;
    _init = init;
  }

  public double exponent(final double x, final double y, final int limit) {
    double current = _init;

    double sumValue = 0;
    for (int i = 0; i < limit; i++) {
      final int charIndex = i % _pattern.length();
      final double r = _pattern.charAt(charIndex) == 'A' ? x : y;
      current = r * current * (1 - current);
      sumValue+= Math.log(Math.abs(r * (1 - 2 * current)));
    }
    return sumValue / limit;
  }

}
