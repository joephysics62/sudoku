package com.fenton.lyapunov;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public class GenerationParameters implements Serializable {
  private static final long serialVersionUID = 5671858909961417148L;

  private String lyapunovString = "AB";
  private double startValue = 0.5;
  private int maxIterations = 50;
  private int frequency = 4;
  private double xmin = 2;
  private double xmax = 4;
  private double ymin = 2;
  private double ymax = 4;
  private double threshold = 2.5;

  private int redPhase = -1;
  private int greenPhase = 0;
  private int bluePhase = 1;

  public void writeImageData(final OutputStream outStream) throws IOException {
    final Plot plot = new Plot(500, 500, xmin, xmax, ymin, ymax);
    final LyapunovGenerator lyapunovGenerator = new LyapunovGenerator(lyapunovString, startValue,
                                                                      maxIterations, threshold);
    plot.generateData(lyapunovGenerator);
    final LyapunovRenderer renderer = new LyapunovRenderer(
        frequency,
        redPhase,
        greenPhase,
        bluePhase);
    plot.write(renderer, outStream);
  }

  public String getLyapunovString() {
    return lyapunovString;
  }
  public void setLyapunovString(final String lyapunovString) {
    this.lyapunovString = lyapunovString;
  }

  public double getStartValue() {
    return startValue;
  }
  public void setStartValue(final double startValue) {
    this.startValue = startValue;
  }

  public int getMaxIterations() {
    return maxIterations;
  }
  public void setMaxIterations(final int maxIterations) {
    this.maxIterations = maxIterations;
  }
  public int getFrequency() {
    return frequency;
  }
  public void setFrequency(final int frequency) {
    this.frequency = frequency;
  }
  public double getXmin() {
    return xmin;
  }
  public void setXmin(final double xmin) {
    this.xmin = xmin;
  }
  public double getXmax() {
    return xmax;
  }
  public void setXmax(final double xmax) {
    this.xmax = xmax;
  }
  public double getYmin() {
    return ymin;
  }
  public void setYmin(final double ymin) {
    this.ymin = ymin;
  }
  public double getYmax() {
    return ymax;
  }
  public void setYmax(final double ymax) {
    this.ymax = ymax;
  }
  public double getThreshold() {
    return threshold;
  }
  public void setThreshold(final double threshold) {
    this.threshold = threshold;
  }
  public int getRedPhase() {
    return redPhase;
  }
  public void setRedPhase(final int redPhase) {
    this.redPhase = redPhase;
  }
  public int getGreenPhase() {
    return greenPhase;
  }
  public void setGreenPhase(final int greenPhase) {
    this.greenPhase = greenPhase;
  }
  public int getBluePhase() {
    return bluePhase;
  }
  public void setBluePhase(final int bluePhase) {
    this.bluePhase = bluePhase;
  }

}
