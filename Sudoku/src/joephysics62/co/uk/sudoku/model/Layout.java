package joephysics62.co.uk.sudoku.model;

public class Layout {

  private final int _height;
  private final int _width;
  private final int _subTableHeight;
  private final int _subTableWidth;
  private final int _initialsSize;

  public Layout(
      final int height, final int width,
      final int subTableHeight, final int subTableWidth,
      final int initialsSize) {
        _height = height;
        _width = width;
        _subTableHeight = subTableHeight;
        _subTableWidth = subTableWidth;
        _initialsSize = initialsSize;
  }

  public int getHeight() {
    return _height;
  }
  public int getWidth() {
    return _width;
  }
  public int getSubTableHeight() {
    return _subTableHeight;
  }
  public int getSubTableWidth() {
    return _subTableWidth;
  }
  public int getInitialsSize() {
    return _initialsSize;
  }
  public boolean hasSubtables() {
    return _subTableHeight > 1 && _subTableWidth > 1;
  }

  public static Layout CLASSIC_SUDOKU = new Layout(9, 9, 3, 3, 9);
  public static Layout SUPER_SUDOKU = new Layout(16, 16, 4, 4, 16);
  public static Layout TIMES_MINI = new Layout(6, 6, 2, 3, 6);
  public static Layout FUTOSHIKI = new Layout(5, 5, 0, 0, 5);

  public int getInitialValue() {
    return (1 << getInitialsSize()) - 1;
  }

}
