package com.fenton.lyapunov;

import java.awt.Color;

public class LyapunovRenderer implements Renderer<Double> {

  private static final int COLOR_MAX = 255;
  private final int _frequency;
  private final double _threshold;
  private final int _redPhase;
  private final int _greenPhase;
  private final int _bluePhase;

  public LyapunovRenderer(final int frequency, final double threshold,
                          final int redPhase, final int greenPhase, final int bluePhase) {
    _frequency = frequency;
    _threshold = threshold;
    _redPhase = redPhase;
    _greenPhase = greenPhase;
    _bluePhase = bluePhase;
  }

  @Override
  public Color toColor(final Double dataValue) {
    if (Math.abs(dataValue) > _threshold) {
      return Color.WHITE;
    }
    return new Color(cycle(dataValue, _redPhase),
                     cycle(dataValue, _greenPhase),
                     cycle(dataValue, _bluePhase));
  }

  private  int cycle(final double dataValue, final int phase) {
    return (int) (COLOR_MAX * (1 + Math.sin(dataValue * _frequency + phase)) / 2);
  }
}
