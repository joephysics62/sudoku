package joephysics62.co.uk.sudoku.write;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.constraints.UniqueSum;
import joephysics62.co.uk.sudoku.gridmaths.Colour;
import joephysics62.co.uk.sudoku.gridmaths.FourColourSolver;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class KillerSudokuHtmlWriter extends PuzzleHtmlWriter {

  private static final String TEMPLATE_FTL = "killerSudokuTemplate.ftl";
  private final FourColourSolver _fcs;

  public KillerSudokuHtmlWriter(Puzzle puzzle, FourColourSolver fcs) throws URISyntaxException {
    super(puzzle, TEMPLATE_FTL);
    _fcs = fcs;
  }

  @Override
  protected void addPuzzleSpecificParams(Map<String, Object> root, final Layout layout) {
    int[][] groupIds = calculateGroupsIdsGrid(layout);
    for (Entry<Colour, List<Integer>> entry : calculateColourToGroups(groupIds).entrySet()) {
      root.put(entry.getKey() + "Groups", entry.getValue());
    }
    root.put("subTableHeight", layout.getSubTableHeight());
    root.put("subTableWidth", layout.getSubTableWidth());
  }

  private Map<Colour, List<Integer>> calculateColourToGroups(int[][] groupIds) {
    Map<Colour, List<Integer>> reverseMap = new LinkedHashMap<>();
    for (Entry<Integer, Colour> entry : _fcs.calculateColourMap(groupIds).entrySet()) {
      Integer groupId = entry.getKey();
      Colour colour = entry.getValue();
      if (!reverseMap.containsKey(colour)) {
        reverseMap.put(colour, new ArrayList<Integer>());
      }
      reverseMap.get(colour).add(groupId);
    }
    return reverseMap;
  }

  private int[][] calculateGroupsIdsGrid(final Layout layout) {
    int[][] groupIds = new int[layout.getHeight()][layout.getWidth()];
    for (int rowNum = 1; rowNum <= layout.getHeight(); rowNum++) {
      for (int colNum = 1; colNum <= layout.getWidth(); colNum++) {
        groupIds[rowNum - 1][colNum - 1] = getGroupId(Coord.of(rowNum, colNum));
      }
    }
    return groupIds;
  }

  protected String getTemplateLocation() {
    return TEMPLATE_FTL;
  }

  public static class KillerSudokuCell {
    private final Integer _sum;
    private final int _groupId;

    public KillerSudokuCell(final Integer sum, final int groupId) {
      _sum = sum;
      _groupId = groupId;
    }

    public int getGroupId() { return _groupId; }
    public Integer getSum() { return _sum; }
  }

  @Override
  protected List<List<Object>> generateTable() {
    Puzzle puzzle = getPuzzle();
    Layout layout = puzzle.getLayout();
    List<List<Object>> table = new ArrayList<>();
    for (int rowNum = 1; rowNum <= layout.getHeight(); rowNum++) {
      final List<Object> rowList = new ArrayList<>();
      for (int colNum = 1; colNum <= layout.getWidth(); colNum++) {
        Coord coord = Coord.of(rowNum, colNum);
        final int groupId = getGroupId(coord);
        final Integer sum = getSum(groupId, coord);
        rowList.add(new KillerSudokuCell(sum, groupId));
      }
      table.add(rowList);
    }
    return table;
  }

  private Integer getSum(int groupId, Coord coord) {
    Constraint constraint = getPuzzle().getAllConstraints().get(groupId);
    if (constraint instanceof UniqueSum) {
      UniqueSum uniqueSum = (UniqueSum) constraint;
      List<Coord> cellsInUniqueSum = uniqueSum.getCells();
      if (!cellsInUniqueSum.contains(coord)) {
        throw new RuntimeException();
      }
      List<Integer> rows = new ArrayList<>();
      List<Integer> cols = new ArrayList<>();
      for (Coord coord2 : cellsInUniqueSum) {
        rows.add(coord2.getRow());
        cols.add(coord2.getCol());
      }
      Collections.sort(rows);
      Collections.sort(cols);
      if (coord.equals(Coord.of(rows.get(0), cols.get(0)))) {
        return uniqueSum.getSum();
      }
      else {
        return null;
      }
    }
    else {
      throw new RuntimeException();
    }
  }

  private int getGroupId(Coord coord) {
    for (Constraint constraint : getPuzzle().getConstraints(coord)) {
      if (constraint instanceof UniqueSum) {
        int indexOf = getPuzzle().getAllConstraints().indexOf(constraint);
        if (indexOf >= 0) {
          return indexOf;
        }
      }
    }
    throw new RuntimeException("No group id defined for coord " + coord);
  }

}
