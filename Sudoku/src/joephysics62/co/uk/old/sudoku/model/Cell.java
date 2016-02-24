package joephysics62.co.uk.old.sudoku.model;

public class Cell {

  public static boolean isSolved(final int value) {
    if (value < 0) {
      return true; // non-value cells excluded.
    }
    return value != 0 && (value & (value - 1)) == 0;
  }

  public static int cellValueAsBitwise(final Integer cellValue) {
    return 1 << (cellValue - 1);
  }

  public static int remove(final int targetValue, final int valueToRemove) {
    if (targetValue < 0) {
      throw new UnsupportedOperationException();
    }
    return targetValue & (~valueToRemove);
  }

  public static Integer toNumericValue(int bitwiseValue) {
    if (bitwiseValue < 0) {
      return -1;
    }
    if (Cell.isSolved(bitwiseValue)) {
      return Integer.numberOfTrailingZeros(bitwiseValue) + 1;
    }
    else {
      return null;
    }
  }

  public static String asString(final int nonBitwiseValue, final int possiblesSize) {
    if (nonBitwiseValue < 0) {
      return " ";
    }
    if (possiblesSize > 9) {
      if (nonBitwiseValue > 10) {
        // 65 => A
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

  public static Integer fromString(final String input, final int puzzleSize) {
    if (input.isEmpty()) {
      return null;
    }
    if (puzzleSize > 9) {
      try {
        return Integer.parseInt(input) + 1;
      }
      catch (NumberFormatException e) {
        int intValue = Character.getNumericValue(input.toCharArray()[0]) + 1;
        return intValue;
      }
    }
    else {
      return Integer.parseInt(input);
    }
  }
}
