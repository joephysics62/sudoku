package joephysics62.co.uk.sudoku.model;


public class Cell {
  private int _bitwiseValue;
  private final Coord _identifier;
  private boolean _solved = false;

  public boolean isSolved() {
    return _solved;
  }

  public static boolean isPower2(final int value) {
    return value != 0 && (value & (value - 1)) == 0;
  }

  public boolean isUnsolvable() {
    return _bitwiseValue == 0;
  }

  private Cell(int initialBitwise, Coord identifier) {
    _bitwiseValue = initialBitwise;
    _identifier = identifier;
  }

  public static Cell given(int givenValue, Coord coord) {
    return new Cell(cellValueAsBitwise(givenValue), coord);
  }

  public static Cell unknown(int inits, Coord coord) {
    return new Cell(inits, coord);
  }

  public static Cell copyOf(Cell otherCell) {
    return new Cell(otherCell);
  }

  private Cell(Cell old) {
    _identifier = old._identifier;
    _bitwiseValue = old._bitwiseValue;
  }

  @Override
  public String toString() {
    return String.format("Cell(coord=%s,values=%s)", _identifier, _bitwiseValue);
  }

  public boolean canApplyElimination() {
    return !isSolved() && isPower2(_bitwiseValue);
  }

  public int getCurrentValues() {
    return _bitwiseValue;
  }

  public void fixValue(final int value) {
    _bitwiseValue = cellValueAsBitwise(value);
    setSolved();
  }

  public void setSolved() {
    _solved = true;
  }

  private static int cellValueAsBitwise(final Integer cellValue) {
    return 1 << (cellValue - 1);
  }

  public boolean remove(final int cellValue) {
    final int oldValue = _bitwiseValue;
    _bitwiseValue = _bitwiseValue & (~cellValue);
    return oldValue != _bitwiseValue;
  }

  public Coord getCoord() {
    return _identifier;
  }
}
