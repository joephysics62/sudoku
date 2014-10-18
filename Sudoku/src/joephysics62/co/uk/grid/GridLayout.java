package joephysics62.co.uk.grid;

public class GridLayout {

  private final int _height;
  private final int _width;
  public GridLayout(final int height, final int width) {
    _height = height;
    _width = width;
  }

  public final int getHeight() {
    return _height;
  }

  public final int getWidth() {
    return _width;
  }
}
