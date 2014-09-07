package joephysics62.co.uk.sudoku.model;

public class Cell {

  public static boolean isSolved(final int value) {
    return value != 0 && (value & (value - 1)) == 0;
  }

  public static int cellValueAsBitwise(final Integer cellValue) {
    return 1 << (cellValue - 1);
  }

  public static int remove(final int targetValue, final int valueToRemove) {
    return targetValue & (~valueToRemove);
  }
}
