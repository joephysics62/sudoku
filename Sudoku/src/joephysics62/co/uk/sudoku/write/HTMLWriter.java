package joephysics62.co.uk.sudoku.write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class HTMLWriter {

  private static final String ENCODING = "UTF-8";
  private final Puzzle _puzzle;
  private final String _templateName;

  public HTMLWriter(final Puzzle puzzle, final String templateName) {
    _puzzle = puzzle;
    _templateName = templateName;
  }

  public final void write(final File file) throws IOException, TemplateException {
    Configuration configuration = new Configuration();
    configuration.setClassForTemplateLoading(getClass(), "templates");
    Map<String, Object> root = new HashMap<>();
    List<List<Object>> table = generateTable();
    root.put("table", table);
    root.put("title", _puzzle.getTitle());
    addPuzzleSpecificParams(root, _puzzle.getLayout());
    Template template = configuration.getTemplate(_templateName, ENCODING);
    template.process(root, new FileWriter(file));
  }

  protected Puzzle getPuzzle() {
    return _puzzle;
  }

  protected abstract void addPuzzleSpecificParams(Map<String, Object> root, final Layout layout);

  protected abstract List<List<Object>> generateTable();

 }
