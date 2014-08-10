package joephysics62.co.uk.sudoku.standard;

import java.util.LinkedHashSet;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.InitialValues;

public class StandardPuzzle {
  public static final int STANDARD_MIN_VALUE = 1;
  public static final int STANDARD_MAX_VALUE = 9;
  private static Set<Integer> INITS_SET = new LinkedHashSet<>();
  static {
    for (int i = STANDARD_MIN_VALUE; i <= STANDARD_MAX_VALUE; i++) {
      INITS_SET.add(i);
    }
  }
  public static InitialValues<Integer> INITS = new InitialValues<>(INITS_SET);
}
