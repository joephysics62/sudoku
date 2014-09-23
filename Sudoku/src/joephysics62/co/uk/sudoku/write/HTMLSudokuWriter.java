package joephysics62.co.uk.sudoku.write;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class HTMLSudokuWriter extends HTMLPuzzleWriter {

  private static final String TEMPLATE_FTL = "templates/sudokuTemplate.ftl";

  public HTMLSudokuWriter(Puzzle puzzle) {
    super(puzzle, TEMPLATE_FTL);
  }

  @Override
  protected void addPuzzleSpecificParams(Map<String, Object> root, final PuzzleLayout layout) {
    root.put("subTableHeight", layout.getSubTableHeight());
    root.put("subTableWidth", layout.getSubTableWidth());
  }

  protected String getTemplateLocation() {
    return TEMPLATE_FTL;
  }

  @Override
  protected List<List<String>> generateTable(final int[][] allCells, final PuzzleLayout layout) {
    List<List<String>> table = new ArrayList<>();
    for (int[] row : allCells) {
      List<String> rowList = new ArrayList<>();
      for (int value : row) {
        if (Cell.isSolved(value)) {
          rowList.add(Cell.asString(Cell.convertToNiceValue(value), layout.getInitialsSize()));
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
