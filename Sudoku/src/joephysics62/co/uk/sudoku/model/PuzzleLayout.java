package joephysics62.co.uk.sudoku.model;

public class PuzzleLayout {

  private final int _height;
  private final int _width;
  private final int _subTableHeight;
  private final int _subTableWidth;
  private final int _initialsSize;

  public PuzzleLayout(
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

  public static PuzzleLayout CLASSIC_SUDOKU = new PuzzleLayout(9, 9, 3, 3, 9);
  public static PuzzleLayout SUPER_SUDOKU = new PuzzleLayout(16, 16, 4, 4, 16);
  public static PuzzleLayout TIMES_MINI = new PuzzleLayout(6, 6, 2, 3, 6);
  public static PuzzleLayout FUTOSHIKI = new PuzzleLayout(5, 5, 0, 0, 5);

}
