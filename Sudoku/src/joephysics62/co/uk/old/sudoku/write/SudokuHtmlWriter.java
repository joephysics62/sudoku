package joephysics62.co.uk.old.sudoku.write;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;

public class SudokuHtmlWriter extends PuzzleHtmlWriter {

  private static final String TEMPLATE_FTL = "sudokuTemplate.ftl";

  public SudokuHtmlWriter(Puzzle puzzle) throws URISyntaxException {
    super(puzzle, TEMPLATE_FTL);
  }

  @Override
  protected void addPuzzleSpecificParams(Map<String, Object> root, final Puzzle puzzle) {
    root.put("subTableHeight", puzzle.getLayout().getSubTableHeight());
    root.put("subTableWidth", puzzle.getLayout().getSubTableWidth());
  }

  protected String getTemplateLocation() {
    return TEMPLATE_FTL;
  }

  @Override
  protected List<List<Object>> generateTable() {
    Puzzle puzzle = getPuzzle();
    PuzzleLayout layout = puzzle.getLayout();
    List<List<Object>> table = new ArrayList<>();
    for (int rowNum = 1; rowNum <= layout.getHeight(); rowNum++) {
      final List<Object> rowList = new ArrayList<>();
      for (int colNum = 1; colNum <= layout.getWidth(); colNum++) {
        final int value = puzzle.get(Coord.of(rowNum, colNum));
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