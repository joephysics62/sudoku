package com.fenton.lyapunov;

import java.awt.Color;

public class LyapunovRenderer implements Renderer<Double> {

  private final int _frequency;

  public LyapunovRenderer(final int frequency) {
    _frequency = frequency;
  }

  @Override
  public Color toColor(final Double dataValue) {
    if (Math.abs(dataValue) > 2.5) {
      return Color.WHITE;
    }
    return new Color(cycle(dataValue, 20), cycle(dataValue, 0), cycle(dataValue, 80));
  }

  private  int cycle(final double dataValue, final int phase) {
    final int value = (int) (dataValue * _frequency);
    final int cycled = Math.abs((value + phase) % (255 * 2));
    if (cycled > 255) {
      return (255 * 2) - cycled;
    }
    return cycled;
  }
}
