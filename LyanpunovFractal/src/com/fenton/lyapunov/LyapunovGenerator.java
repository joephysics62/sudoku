package com.fenton.lyapunov;

import com.fenton.PlotGenerator;

public class LyapunovGenerator implements PlotGenerator<Double> {

  private final int _maxIterations;
  private final char[] _lyapunovString;
  private final double _threshold;
  private final double _startValue;

  public LyapunovGenerator(final String lyapunovString,
                           final double startValue,
                           final int maxIterations,
                           final double threshold) {
    _startValue = startValue;
    _maxIterations = maxIterations;
    _threshold = threshold;
    _lyapunovString = lyapunovString.toCharArray();
  }

  @Override
  public Double generate(final double x, final double y) {
    double val = _startValue;
    double sum = 0;
    for (int i = 1; i <= _maxIterations; i++) {
      final double rValue = rFunction(x, y, i - 1);
      val =  iterateNext(val, rValue);
      final double logArgand = doArgand(x, y, val, i);
      if (logArgand == 0) {
        return sum;
      }
      sum += doLog(logArgand);
      if (Math.abs(sum) > _threshold) {
        return sum;
      }
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
    return Math.log(logArgand) / _maxIterations;
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
