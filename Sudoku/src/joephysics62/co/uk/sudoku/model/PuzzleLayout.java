package joephysics62.co.uk.sudoku.model;

import joephysics62.co.uk.grid.GridLayout;

public class PuzzleLayout extends GridLayout {

  private final int _subTableHeight;
  private final int _subTableWidth;
  private final int _initialsSize;
  private final GridUniqueness _gridUniqueness;

  public PuzzleLayout(
      final int height, final int width,
      final int subTableHeight, final int subTableWidth,
      final int initialsSize,
      GridUniqueness gridUniqueness) {
    super(height, width);
        _subTableHeight = subTableHeight;
        _subTableWidth = subTableWidth;
        _gridUniqueness = gridUniqueness;
        _initialsSize = initialsSize;
  }

  public int getSubTableHeight() { return _subTableHeight; }
  public int getSubTableWidth() { return _subTableWidth; }
  public int getInitialsSize() { return _initialsSize; }
  public boolean hasSubtables() { return _subTableHeight > 1 && _subTableWidth > 1; }
  public GridUniqueness getGridUniqueness() { return _gridUniqueness; }

  public static PuzzleLayout CLASSIC_SUDOKU = new PuzzleLayout(9, 9, 3, 3, 9, GridUniqueness.ROWS_COLUMNS_SUBTABLES);
  public static PuzzleLayout SUPER_SUDOKU = new PuzzleLayout(16, 16, 4, 4, 16, GridUniqueness.ROWS_COLUMNS_SUBTABLES);
  public static PuzzleLayout TIMES_MINI = new PuzzleLayout(6, 6, 2, 3, 6, GridUniqueness.ROWS_COLUMNS_SUBTABLES);
  public static PuzzleLayout FUTOSHIKI = new PuzzleLayout(5, 5, 0, 0, 5, GridUniqueness.ROWS_COLUMNS);

  public int getInitialValue() {
    return (1 << getInitialsSize()) - 1;
  }

}
