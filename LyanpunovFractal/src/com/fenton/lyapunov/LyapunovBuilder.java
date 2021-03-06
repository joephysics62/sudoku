package com.fenton.lyapunov;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import com.fenton.Plot;

public class LyapunovBuilder implements Serializable {
  private static final long serialVersionUID = 5671858909961417148L;

  private String lyapunovString = "AB";
  private double startValue = 0.5;
  private int maxIterations = 50;
  private int frequency = 4;

  private int imageWidth = 500;
  private int imageHeight = 500;
  private double xmin = 2;
  private double xmax = 4;
  private double ymin = 2;
  private double ymax = 4;
  private double threshold = 2.5;

  private int redPhase = -1;
  private int greenPhase = 0;
  private int bluePhase = 1;

  public void writeImageData(final OutputStream outStream) throws IOException {
    final Plot<Double> plot = newPlot();
    final LyapunovGenerator lyapunovGenerator = newGenerator();
    plot.generateData(lyapunovGenerator);
    final LyapunovRenderer renderer = newRenderer();
    plot.write(renderer, outStream);
  }

  private LyapunovRenderer newRenderer() {
    return new LyapunovRenderer(
        frequency,
        redPhase,
        greenPhase,
        bluePhase);
  }

  private LyapunovGenerator newGenerator() {
    return new LyapunovGenerator(lyapunovString, startValue, maxIterations, threshold);
  }

  private Plot<Double> newPlot() {
    return new Plot<>(imageWidth, imageHeight, xmin, xmax, ymin, ymax);
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
  public int getImageWidth() {
    return imageWidth;
  }
  public void setImageWidth(final int imageWidth) {
    this.imageWidth = imageWidth;
  }
  public int getImageHeight() {
    return imageHeight;
  }
  public void setImageHeight(final int imageHeight) {
    this.imageHeight = imageHeight;
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
