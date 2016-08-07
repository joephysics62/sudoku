package com.fenton.lyapunov;

public class LyapunovGenerator implements PlotGenerator {

  private final int _maxIterations;
  private final char[] _lyapunovString;

  public LyapunovGenerator(final String lyapunovString, final int maxIterations) {
    _maxIterations = maxIterations;
    _lyapunovString = lyapunovString.toCharArray();
  }

  @Override
  public double generate(final double x, final double y) {
    double val = 0.5;
    double sum = 0;
    for (int i = 1; i <= _maxIterations; i++) {
      final double rValue = rFunction(x, y, i - 1);
      val =  iterateNext(val, rValue);
      final double logArgand = doArgand(x, y, val, i);
      if (logArgand == 0) {
        return Integer.MIN_VALUE;
      }
      sum += doLog(logArgand) / _maxIterations;
    }
    return sum;
  }

  private double iterateNext(final double val, final double rValue) {
    return rValue * val * (1 - val);
  }

  private double doArgand(final double x, final double y, final double val, final int i) {
    return Math.abs(rFunction(x, y, i) * (1 - 2 * val));
  }

  private double doLog(final double logArgand) {
    return Math.log(logArgand);
  }

  private double rFunction(final double x, final double y, final int iteration) {
    final char lyapunovChar = _lyapunovString[iteration % _lyapunovString.length];
    switch (lyapunovChar) {
    case 'A':
      return x;
    case 'B':
      return y;
    default:
      throw new RuntimeException(lyapunovChar + " ??? ");
    }
  }
}
