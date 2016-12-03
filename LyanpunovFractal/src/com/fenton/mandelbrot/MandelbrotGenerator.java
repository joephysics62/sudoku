package com.fenton.mandelbrot;

import com.fenton.PlotGenerator;

public class MandelbrotGenerator implements PlotGenerator<Boolean> {

  private static final int BAIL_OUT = 25;
  private static double MAX = 50.0;
  private static double MIN = 1e-10;

  @Override
  public Boolean generate(final double x, final double y) {
    double zre = 0;
    double zim = 0;

    for (int c = 0; c < BAIL_OUT; c++) {
      final double zre2 = zre * zre;
      final double zim2 = zim * zim;
      final double mod = zre2 + zim2;
      if (mod > MAX) {
        return false;
      }
      if (mod < MIN ) {
        return true;
      }

      final double zreIm = zre * zim;
      zre = (x + zre2 - zim2);
      zim = (y - 2 * zreIm);
    }
    return true;
  }


}
