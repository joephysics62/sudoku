package com.fenton.mandelbrot;

import java.awt.Color;

import com.fenton.Renderer;

public class MandelbrotRenderer implements Renderer<Boolean> {

  @Override
  public Color toColor(final Boolean dataValue) {
    return Boolean.TRUE.equals(dataValue) ? Color.BLACK : Color.WHITE;
  }

}
