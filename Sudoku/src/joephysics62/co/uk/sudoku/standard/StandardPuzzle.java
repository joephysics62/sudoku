package joephysics62.co.uk.sudoku.standard;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.InitialValues;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Restriction;
import joephysics62.co.uk.sudoku.model.Uniqueness;

public class StandardPuzzle implements Puzzle<Integer> {

  public static final int STANDARD_MIN_VALUE = 1;
  public static final int STANDARD_MAX_VALUE = 9;
  private static Set<Integer> INITS_SET = new LinkedHashSet<>();
  static {
    for (int i = STANDARD_MIN_VALUE; i <= STANDARD_MAX_VALUE; i++) {
      INITS_SET.add(i);
    }
  }
  public static InitialValues<Integer> INITS = new InitialValues<>(INITS_SET);
  private final Map<Cell<Integer>, Set<Restriction<Integer>>> _constraints;

  public StandardPuzzle(Map<Cell<Integer>, Set<Restriction<Integer>>> constraints) {
    _constraints = constraints;
  }

  @Override
  public Set<Cell<Integer>> getAllCells() {
    return _constraints.keySet();
  }

  @Override
  public boolean isSolved() {
    Set<Cell<Integer>> allCells = getAllCells();
    for (Cell<Integer> cell : allCells) {
      if (!cell.isSolved()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Collection<Restriction<Integer>> getAllRestrictions() {
    final Set<Restriction<Integer>> out = new LinkedHashSet<>();
    for (Set<Restriction<Integer>> set : _constraints.values()) {
      out.addAll(set);
    }
    return Collections.unmodifiableSet(out);
  }

  @Override
  public Set<Restriction<Integer>> getRestrictions(final Cell<Integer> cell) {
    return Collections.unmodifiableSet(_constraints.get(cell));
  }


  public static Puzzle<Integer> fromTableValues(final List<List<Integer>> input) {
    final List<List<Cell<Integer>>> wholePuzzle = asIntegerCellTable(input);
    final Map<Cell<Integer>, Set<Restriction<Integer>>> constraints = new LinkedHashMap<>();
    for (List<Cell<Integer>> row : wholePuzzle) {
      addToMap(Uniqueness.of(row), constraints);
    }
    for (int i = 0; i < STANDARD_MAX_VALUE; i++) {
      final Set<Cell<Integer>> colCells = new LinkedHashSet<>();
      for (List<Cell<Integer>> row : wholePuzzle) {
        Cell<Integer> cell = row.get(i);
        colCells.add(cell);
      }
      addToMap(Uniqueness.of(colCells), constraints);
    }
    for (int i = 0; i < STANDARD_MAX_VALUE / 3; i++) {
      for (int j = 0; j < STANDARD_MAX_VALUE / 3; j++) {
        final Set<Cell<Integer>> subTableCells = new LinkedHashSet<>();
        for (int ii = 0; ii < STANDARD_MAX_VALUE / 3; ii++) {
          for (int jj = 0; jj < STANDARD_MAX_VALUE / 3; jj++) {
            int row = i * 3 + ii;
            int col = j * 3 + jj;
            Cell<Integer> cell = wholePuzzle.get(row).get(col);
            subTableCells.add(cell);
          }
        }
        addToMap(Uniqueness.of(subTableCells), constraints);
      }
    }
    return new StandardPuzzle(constraints);
  }

  private static void addToMap(Restriction<Integer> restriction, final Map<Cell<Integer>, Set<Restriction<Integer>>> constraints) {
    for (Cell<Integer> cell : restriction.getCells()) {
      if (!constraints.containsKey(cell)) {
        constraints.put(cell, new LinkedHashSet<Restriction<Integer>>());
      }
      constraints.get(cell).add(restriction);
    }
  }

  private static List<List<Cell<Integer>>> asIntegerCellTable(final List<List<Integer>> input) {
    if (input.size() != STANDARD_MAX_VALUE) {
      throw new IllegalArgumentException();
    }
    int rowNum = 0;
    final List<List<Cell<Integer>>> wholePuzzle = new ArrayList<>(STANDARD_MAX_VALUE);
    for (List<Integer> row : input) {
      rowNum++;
      if (row.size() != STANDARD_MAX_VALUE) {
        throw new IllegalArgumentException();
      }
      final List<Cell<Integer>> rowCells = new ArrayList<Cell<Integer>>(STANDARD_MAX_VALUE);
      int colNum = 0;
      for (Integer integer : row) {
        Coord id = new Coord(rowNum, ++colNum);
        rowCells.add(null == integer ? new IntegerCell(INITS, id) : new IntegerCell(integer, id));
      }
      wholePuzzle.add(rowCells);
    }
    return wholePuzzle;
  }

  @Override
  public void write(PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    for (Cell<Integer> cell : _constraints.keySet()) {
      maxRow = Math.max(cell.getIdentifier().getRow(), maxRow);
      maxCol = Math.max(cell.getIdentifier().getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Cell<Integer> cell : _constraints.keySet()) {
      Coord coord = cell.getIdentifier();
      array[coord.getRow() - 1][coord.getCol() - 1] = cell.isSolved() ? cell.getValue() : null;
    }
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxCol; j++) {
        if (j == 0) {
          out.print("|");
        }
        Object value = array[i][j];
        out.print(value == null ? "?|" : value + "|");
      }
      out.println("");
    }
  }

}
