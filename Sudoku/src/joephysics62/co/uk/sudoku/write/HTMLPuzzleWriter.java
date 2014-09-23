package joephysics62.co.uk.sudoku.write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class HTMLPuzzleWriter {

  private static final String ENCODING = "UTF-8";
  private final Puzzle _puzzle;
  private final String _templateLocation;

  public HTMLPuzzleWriter(final Puzzle puzzle, final String templateLocation) {
    _puzzle = puzzle;
    _templateLocation = templateLocation;
  }

  public final void write(final File file) throws IOException, TemplateException {
    Configuration configuration = new Configuration();
    Map<String, Object> root = new HashMap<>();
    List<List<String>> table = generateTable(_puzzle.getAllCells(), _puzzle.getLayout());
    root.put("table", table);
    root.put("title", _puzzle.getTitle());
    addPuzzleSpecificParams(root, _puzzle.getLayout());
    Template template = configuration.getTemplate(_templateLocation, ENCODING);
    template.process(root, new FileWriter(file));
  }

  protected abstract void addPuzzleSpecificParams(Map<String, Object> root, final PuzzleLayout layout);

  protected abstract List<List<String>> generateTable(int[][] allCells, PuzzleLayout puzzleLayout);

 }
