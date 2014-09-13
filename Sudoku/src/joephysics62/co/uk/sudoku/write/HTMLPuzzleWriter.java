package joephysics62.co.uk.sudoku.write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HTMLPuzzleWriter {
  private final Puzzle _puzzle;

  public HTMLPuzzleWriter(final Puzzle puzzle) {
    _puzzle = puzzle;
  }

  public void write(final File file, final String title) throws IOException, TemplateException {
    Configuration configuration = new Configuration();
    Map<String, Object> root = new HashMap<>();
    root.put("title", title);
    int[][] allCells = _puzzle.getAllCells();
    List<List<String>> table = new ArrayList<>();
    for (int[] row : allCells) {
      List<String> rowList = new ArrayList<>();
      for (int value : row) {
        if (Cell.isSolved(value)) {
          rowList.add(Cell.asString(Cell.convertToNiceValue(value), _puzzle.getPuzzleSize()));
        }
        else {
          rowList.add(null);
        }
      }
      table.add(rowList);
    }
    root.put("table", table);
    root.put("subTableHeight", _puzzle.getSubTableHeight());
    root.put("subTableWidth", _puzzle.getSubTableWidth());
    Template template = configuration.getTemplate("templates/sudokuTemplate.ftl", "UTF-8");
    template.process(root, new FileWriter(file));

  }
 }
