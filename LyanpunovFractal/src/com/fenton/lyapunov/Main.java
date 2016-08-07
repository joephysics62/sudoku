package com.fenton.lyapunov;

import java.awt.Color;
import java.io.IOException;

public class Main {
  public static void main(final String[] args) throws IOException {
    final Plot plot = new Plot(600, 600, 2, 4, 2, 4);

    final LyapunovGenerator lyapunovGenerator = new LyapunovGenerator("AB", 100);

    plot.generateData(lyapunovGenerator);

    plot.writeToImage("output_image.png", new Renderer<Double>() {

      private final int _freqency = 100;

      @Override
      public Color toColor(final Double dataValue) {
        if (Math.abs(dataValue) > 2.5) {
          return Color.WHITE;
        }
        return new Color(cycle(dataValue, 20), cycle(dataValue, 0), cycle(dataValue, 80));
      }

      private  int cycle(final double dataValue, final int phase) {
        final int value = (int) (dataValue * _freqency);
        final int cycled = Math.abs((value + phase) % (255 * 2));
        if (cycled > 255) {
          return (255 * 2) - cycled;
        }
        return cycled;
      }
    });

  }

}
