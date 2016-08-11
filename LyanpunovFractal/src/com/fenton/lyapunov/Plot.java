package com.fenton.lyapunov;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class Plot {
  private final double[][] _data;
  private final int _imageHeight;
  private final int _imageWidth;
  private final double _xMin;
  private final double _xMax;
  private final double _yMin;
  private final double _yMax;

  public Plot(final int imageHeight, final int imageWidth,
                    final double xMin, final double xMax,
                    final double yMin, final double yMax) {
    _imageHeight = imageHeight;
    _imageWidth = imageWidth;
    _xMin = xMin;
    _xMax = xMax;
    _yMin = yMin;
    _yMax = yMax;
    _data = new double[imageHeight][imageWidth];
  }

  public void generateData(final PlotGenerator generator) {
    final double xStep = (_xMax - _xMin) / _imageWidth;
    final double yStep = (_yMax - _yMin) / _imageHeight;

    for (int imageX = 0; imageX < _imageWidth; imageX++) {
      for (int imageY = 0; imageY < _imageHeight; imageY++) {
        _data[imageX][imageY] = generator.generate(_xMin + xStep * imageX, _yMin + yStep * imageY);
      }
    }
  }

  public void write(final Renderer<Double> renderer, final OutputStream outStream) throws IOException {
    final BufferedImage bImg = new BufferedImage(_imageWidth, _imageHeight, BufferedImage.TYPE_INT_RGB);

    for (int imageX = 0; imageX < _imageWidth; imageX++) {
      for (int imageY = 0; imageY < _imageHeight; imageY++) {
        bImg.setRGB(imageX, _imageHeight - imageY - 1, renderer.toColor(_data[imageX][imageY]).getRGB());
      }
    }
    ImageIO.write(bImg, "png", outStream);
  }

}
