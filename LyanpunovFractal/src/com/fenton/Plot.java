package com.fenton;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class Plot<T> {
  private T[][] _data;
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

  }

  @SuppressWarnings("unchecked")
  public void generateData(final PlotGenerator<T> generator) {
    _data = (T[][]) new Object[_imageHeight][_imageWidth];
    final double xStep = (_xMax - _xMin) / _imageWidth;
    final double yStep = (_yMax - _yMin) / _imageHeight;

    for (int imageX = 0; imageX < _imageWidth; imageX++) {
      for (int imageY = 0; imageY < _imageHeight; imageY++) {
        _data[imageY][imageX] = generator.generate(_xMin + xStep * imageX, _yMin + yStep * imageY);
      }
    }
  }

  public void write(final Renderer<T> renderer, final OutputStream outStream) throws IOException {
    final BufferedImage bImg = new BufferedImage(_imageWidth, _imageHeight, BufferedImage.TYPE_INT_RGB);

    for (int imageX = 0; imageX < _imageWidth; imageX++) {
      for (int imageY = 0; imageY < _imageHeight; imageY++) {
        bImg.setRGB(imageX, _imageHeight - imageY - 1, renderer.toColor(_data[imageY][imageX]).getRGB());
      }
    }
    ImageIO.write(bImg, "png", outStream);
  }

}
