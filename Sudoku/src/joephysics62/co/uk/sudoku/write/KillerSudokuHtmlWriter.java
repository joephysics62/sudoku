package joephysics62.co.uk.sudoku.write;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.constraints.UniqueSum;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.grid.arrays.IntegerArrayGrid;
import joephysics62.co.uk.grid.maths.Colour;
import joephysics62.co.uk.grid.maths.FourColourSolver;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class KillerSudokuHtmlWriter extends PuzzleHtmlWriter {

  private static final String TEMPLATE_FTL = "killerSudokuTemplate.ftl";
  private final FourColourSolver _fcs;

  public KillerSudokuHtmlWriter(Puzzle puzzle, FourColourSolver fcs) throws URISyntaxException {
    super(puzzle, TEMPLATE_FTL);
    _fcs = fcs;
  }

  @Override
  protected void addPuzzleSpecificParams(Map<String, Object> root, final Puzzle puzzle) {
    IntegerArrayGrid groupIds = calculateGroupsIdsGrid(puzzle);
    Multimap<Colour, Integer> colourToGroups = calculateColourToGroups(groupIds);
    for (Colour colour : Colour.values()) {
      root.put(colour + "Groups", colourToGroups.containsKey(colour) ? colourToGroups.get(colour) : Collections.emptyList());
    }
    root.put("subTableHeight", puzzle.getLayout().getSubTableHeight());
    root.put("subTableWidth", puzzle.getLayout().getSubTableWidth());
  }

  private Multimap<Colour, Integer> calculateColourToGroups(IntegerArrayGrid groupIds) {
    final Multimap<Colour, Integer> reverseMap = ArrayListMultimap.create();
    for (Entry<Integer, Colour> entry : _fcs.calculateColourMap(groupIds).entrySet()) {
      Integer groupId = entry.getKey();
      Colour colour = entry.getValue();
      reverseMap.put(colour, groupId);
    }
    return reverseMap;
  }

  private IntegerArrayGrid calculateGroupsIdsGrid(final Puzzle puzzle) {
    PuzzleLayout layout = puzzle.getLayout();
    IntegerArrayGrid groupIds = new IntegerArrayGrid(layout);
    for (final Coord coord : puzzle) {
      groupIds.set(getGroupId(coord), coord);
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
    GridLayout layout = puzzle.getLayout();
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
      final Coord cellToIndicateSum = topLeftCell(cellsInUniqueSum);
      if (coord.equals(cellToIndicateSum)) {
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

  private Coord topLeftCell(List<Coord> coords) {
    if (coords.isEmpty()) {
      return null;
    }
    if (coords.size() == 1) {
      return coords.get(0);
    }
    final List<Coord> sorted = new ArrayList<>(coords);
    Collections.sort(sorted, new Comparator<Coord>() {

      @Override
      public int compare(Coord c1, Coord c2) {
        int rowCompare = Integer.compare(c1.getRow(), c2.getRow());
        if (rowCompare != 0) {
          return rowCompare;
        }
        return Integer.compare(c1.getCol(), c2.getCol());
      }
    });
    return sorted.get(0);
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
