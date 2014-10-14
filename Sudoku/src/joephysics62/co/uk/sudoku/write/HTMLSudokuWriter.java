package joephysics62.co.uk.sudoku.write;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class HTMLSudokuWriter extends HTMLWriter {

  private static final String TEMPLATE_FTL = "templates/sudokuTemplate.ftl";

  public HTMLSudokuWriter(Puzzle puzzle) {
    super(puzzle, TEMPLATE_FTL);
  }

  @Override
  protected void addPuzzleSpecificParams(Map<String, Object> root, final Layout layout) {
    root.put("subTableHeight", layout.getSubTableHeight());
    root.put("subTableWidth", layout.getSubTableWidth());
  }

  protected String getTemplateLocation() {
    return TEMPLATE_FTL;
  }

  @Override
  protected List<List<Object>> generateTable(final int[][] allCells, final Layout layout) {
    List<List<Object>> table = new ArrayList<>();
    for (int[] row : allCells) {
      List<Object> rowList = new ArrayList<>();
      for (int value : row) {
        if (Cell.isSolved(value)) {
          rowList.add(Cell.asString(Cell.toNumericValue(value), layout.getInitialsSize()));
        }
        else {
          rowList.add(null);
        }
      }
      table.add(rowList);
    }
    return table;
  }

}
