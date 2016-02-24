package joephysics62.co.uk.old.plotting;

public class ImageScale {


  private final double _scale;
  private final int _width;
  private final double _xmin;
  private final double _ymin;
  private final int _height;

  public ImageScale(final int height, final double aspect, final double ymin, final double ymax, final double xmin) {
    _height = height;
    _ymin = ymin;
    _xmin = xmin;
    _width = (int) (aspect * height);
    _scale = height / Math.abs(ymax - ymin);
  }

  public int getWidth() {
    return _width;
  }

  public int getHeight() {
    return _height;
  }

  public int pixelX(final double xplot) {
    return (int) ((xplot - _xmin) * _scale);
  }

  public int pixelY(final double yplot) {
    return (int) ((yplot - _ymin) * _scale);
  }

  public double dataX(final int x) {
    return _xmin + x / _scale;
  }

  public double dataY(final int y) {
    return _ymin + y / _scale;
  }



  public boolean isOnPlot(final int x, final int y) {
    return x > 0 && y > 0 && x < _width && y < _height;
  }

}
