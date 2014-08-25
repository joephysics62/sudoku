package joephysics62.co.uk.sudoku.standard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGroup;
import joephysics62.co.uk.sudoku.model.InitialValues;
import joephysics62.co.uk.sudoku.model.Restriction;
import joephysics62.co.uk.sudoku.model.Uniqueness;

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

  public static StandardPuzzle fromTableValues(final List<List<Integer>> input) {
    final List<List<IntegerCell>> wholePuzzle = asIntegerCellTable(input);
    int rowNum = 0;
    final Map<CellGroup<Integer>, Restriction<Integer>> constraints = new LinkedHashMap<>();
    for (List<IntegerCell> row : wholePuzzle) {
      CellGroup<Integer> rowGroup = new CellGroup<Integer>("row_" + ++rowNum, new LinkedHashSet<Cell<Integer>>(row));
      constraints.put(rowGroup, Uniqueness.of(rowGroup));
    }
    for (int i = 0; i < STANDARD_MAX_VALUE; i++) {
      final Set<Cell<Integer>> colCells = new LinkedHashSet<>();
      for (List<IntegerCell> row : wholePuzzle) {
        colCells.add(row.get(i));
      }
      CellGroup<Integer> cellGroup = new CellGroup<Integer>("col_" + i, colCells);
      constraints.put(cellGroup, Uniqueness.of(cellGroup));
    }

    System.err.println(wholePuzzle);
    return null;
  }

  private static List<List<IntegerCell>> asIntegerCellTable(final List<List<Integer>> input) {
    if (input.size() != STANDARD_MAX_VALUE) {
      throw new IllegalArgumentException();
    }
    int rowNum = 0;
    final List<List<IntegerCell>> wholePuzzle = new ArrayList<>(STANDARD_MAX_VALUE);
    for (List<Integer> row : input) {
      rowNum++;
      if (row.size() != STANDARD_MAX_VALUE) {
        throw new IllegalArgumentException();
      }
      final List<IntegerCell> rowCells = new ArrayList<IntegerCell>(STANDARD_MAX_VALUE);
      wholePuzzle.add(rowCells);
      int colNum = 0;
      for (Integer integer : row) {
        String id = rowNum + "," + ++colNum;
        rowCells.add(null == integer ? new IntegerCell(INITS, id) : new IntegerCell(integer, id));
      }
      wholePuzzle.add(rowCells);
    }
    return wholePuzzle;
  }
}
