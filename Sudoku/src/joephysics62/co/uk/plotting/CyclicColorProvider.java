package joephysics62.co.uk.plotting;

import java.awt.Color;

public class CyclicColorProvider implements ColorProvider<Double> {

  private final double _maxValue;
  private final int _cycles;

  public CyclicColorProvider(final double maxValue, final int cycles) {
    _maxValue = maxValue;
    _cycles = cycles;
  }

  @Override
  public Color getColouring(final Double value) {
    final double angle = 2 * _cycles * Math.PI * value / _maxValue;
    final int sine = (int) (255 * (Math.sin(angle) + 1) / 2.0);
    final int cosine = (int) (255 * (Math.cos(angle) + 1) / 2.0);
    return new Color(sine, cosine, 0);
  }

}
