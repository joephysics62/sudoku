package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;


public class TableValueParser {

  private final int _puzzleSize;

  public TableValueParser(final int puzzleSize) {
    _puzzleSize = puzzleSize;
  }

  public Integer[][] parse(final File csv) throws IOException {
    Integer[][] tableInts = new Integer[_puzzleSize][_puzzleSize];
    int rowNum = 1;
    for (final String line : Files.readAllLines(csv.toPath(), Charset.forName("UTF-8"))) {
      String[] split = line.split("\\|");
      if (split.length != _puzzleSize + 1) {
        throw new RuntimeException("Bad input: has " + split.length + " |'s on a line: " + line);
      }
      for (int colNum = 1; colNum < split.length; colNum++) {
        String cellInput = split[colNum].trim();
        tableInts[rowNum - 1][colNum - 1] = cellInput.isEmpty() ? null : Integer.valueOf(cellInput);
      }
      rowNum++;
    }
    return tableInts;
  }

}
