package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

import org.apache.log4j.Logger;

public class KakuroHTMLReader implements Reader {

  public static void main(String[] args) throws Exception {
    KakuroHTMLReader kakuroHTMLReader = new KakuroHTMLReader(new Layout(8, 8, 0, 0, 9));
    kakuroHTMLReader.read(new File("examples/kakuro/wikipedia.html"));
  }

  private static final Logger LOG = Logger.getLogger(KakuroHTMLReader.class);

  private final Layout _layout;

  public KakuroHTMLReader(final Layout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(File input) throws IOException {
    HTMLTableParser tableParser = new HTMLTableParser(_layout.getHeight(), _layout.getWidth());
    tableParser.parseTable(input, new TableParserHandler() {

      @Override
      public void title(String title) {
      }

      @Override
      public void cell(final Map<String, String> complexCellInput, final int rowIndex, final int colIndex) {
        LOG.debug(String.format("At html table (%s, %s) found cell with complex content %s", rowIndex, colIndex, complexCellInput));
      }

      @Override
      public void cell(final String cellInput, final int rowIndex, final int colIndex) {
        if (!cellInput.isEmpty()) {
          throw new UnsupportedOperationException();
        }
        LOG.debug(String.format("At html table (%s, %s) found cell with simple cell content '%s'", rowIndex, colIndex, cellInput));
      }
    });
    return null;
  }

}
