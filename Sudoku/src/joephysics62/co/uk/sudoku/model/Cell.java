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

  public static Integer convertToNiceValue(int bitwiseValue) {
    if (Cell.isSolved(bitwiseValue)) {
      return Integer.numberOfTrailingZeros(bitwiseValue) + 1;
    }
    else {
      return null;
    }
  }

  public static String asString(final int nonBitwiseValue, final int puzzleSize) {
    if (puzzleSize > 9) {
      if (nonBitwiseValue > 10) {
        return Character.toString((char) (nonBitwiseValue + 54));
      }
      else {
        return Integer.toString(nonBitwiseValue - 1);
      }
    }
    else {
      return Integer.toString(nonBitwiseValue);
    }
  }
}
