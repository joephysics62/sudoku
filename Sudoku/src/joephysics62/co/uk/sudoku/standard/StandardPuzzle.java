package joephysics62.co.uk.sudoku.standard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGroup;
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
  private final Map<String, Restriction<Integer>> _constraintsByGroup;
  private final Map<String, Set<String>> _groupsByCell;
  private final Set<Cell<Integer>> _allCells;

  @Override
  public Set<String> getGroups(final String cellId) {
    return _groupsByCell.get(cellId);
  }

  public StandardPuzzle(Set<Cell<Integer>> wholePuzzle, Map<String, Restriction<Integer>> constraints, Map<String, Set<String>> groupsByCell) {
    _groupsByCell = Collections.unmodifiableMap(groupsByCell);
    _constraintsByGroup = Collections.unmodifiableMap(constraints);


    _allCells = Collections.unmodifiableSet(wholePuzzle);
  }

  @Override
  public Set<Cell<Integer>> getAllCells() {
    return _allCells;
  }

  @Override
  public Collection<Restriction<Integer>> getAllRestrictions() {
    return _constraintsByGroup.values();
  }

  @Override
  public Set<Restriction<Integer>> getRestrictions(final String cellId) {
    Set<String> groups = _groupsByCell.get(cellId);
    Set<Restriction<Integer>> out = new LinkedHashSet<>();
    for (String groupId : groups) {
      out.add(_constraintsByGroup.get(groupId));
    }
    return out;
  }


  public static Puzzle<Integer> fromTableValues(final List<List<Integer>> input) {
    final List<List<IntegerCell>> wholePuzzle = asIntegerCellTable(input);
    int rowNum = 0;
    final Map<String, Restriction<Integer>> constraintsByGroup = new LinkedHashMap<>();
    final Map<String, Set<String>> groupsByCell = new LinkedHashMap<>();
    for (List<IntegerCell> row : wholePuzzle) {
      String rowGroupId = "row_" + ++rowNum;
      CellGroup<Integer> rowGroup = new CellGroup<Integer>(rowGroupId, new LinkedHashSet<Cell<Integer>>(row));
      for (IntegerCell cell : row) {
        String cellId = cell.getIdentifier();
        if (!groupsByCell.containsKey(cellId)) {
          groupsByCell.put(cellId, new LinkedHashSet<String>());
        }
        groupsByCell.get(cellId).add(rowGroupId);
      }
      constraintsByGroup.put(rowGroupId, Uniqueness.of(rowGroup));
    }
    for (int i = 0; i < STANDARD_MAX_VALUE; i++) {
      String colGroupId = "col_" + i;
      final Set<Cell<Integer>> colCells = new LinkedHashSet<>();
      for (List<IntegerCell> row : wholePuzzle) {
        IntegerCell cell = row.get(i);
        colCells.add(cell);
        String cellId = cell.getIdentifier();
        if (!groupsByCell.containsKey(cellId)) {
          groupsByCell.put(cellId, new LinkedHashSet<String>());
        }
        groupsByCell.get(cellId).add(colGroupId);
      }
      CellGroup<Integer> cellGroup = new CellGroup<Integer>(colGroupId, colCells);
      constraintsByGroup.put(colGroupId, Uniqueness.of(cellGroup));
    }
    for (int i = 0; i < STANDARD_MAX_VALUE / 3; i++) {
      for (int j = 0; j < STANDARD_MAX_VALUE / 3; j++) {
        String subTableId = "subTable_" + i + "_" + j;
        final Set<Cell<Integer>> subTableCells = new LinkedHashSet<>();
        for (int ii = 0; ii < STANDARD_MAX_VALUE / 3; ii++) {
          for (int jj = 0; jj < STANDARD_MAX_VALUE / 3; jj++) {
            int row = i * 3 + ii;
            int col = j * 3 + jj;
            IntegerCell cell = wholePuzzle.get(row).get(col);
            subTableCells.add(cell);
            String cellId = cell.getIdentifier();
            if (!groupsByCell.containsKey(cellId)) {
              groupsByCell.put(cellId, new LinkedHashSet<String>());
            }
            groupsByCell.get(cellId).add(subTableId);
          }
        }
        CellGroup<Integer> subTableGroup = new CellGroup<>(subTableId, subTableCells);
        constraintsByGroup.put(subTableId, Uniqueness.of(subTableGroup));
      }
    }
    final Set<Cell<Integer>> cells = new LinkedHashSet<>();
    for (List<IntegerCell> row : wholePuzzle) {
      for (IntegerCell integerCell : row) {
        cells.add(integerCell);
      }
    }
    return new StandardPuzzle(cells, constraintsByGroup, groupsByCell);
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
