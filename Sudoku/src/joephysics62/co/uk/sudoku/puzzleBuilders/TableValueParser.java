package joephysics62.co.uk.sudoku.puzzleBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class TableValueParser<T> {

  private final int _puzzleSize;
  private final CellValueReader<T> _cellValueReader;

  public TableValueParser(final int puzzleSize, final CellValueReader<T> cellValueReader) {
    _puzzleSize = puzzleSize;
    _cellValueReader = cellValueReader;
  }

  public List<List<T>>parse(final File csv) throws IOException {
    List<List<T>> inputLists = new ArrayList<>();
    for (final String line : Files.readAllLines(csv.toPath(), Charset.forName("UTF-8"))) {
      final List<T> row = new ArrayList<>();
      String[] split = line.split("\\|");
      if (split.length != _puzzleSize + 1) {
        throw new RuntimeException("Bad input: has " + split.length + " |'s on a line: " + line);
      }
      for (int colNum = 1; colNum < split.length; colNum++) {
       String cellInput = split[colNum].trim() ;
       row.add(_cellValueReader.parseCellValue(cellInput));
      }
      inputLists.add(row);
    }
    return inputLists;
  }

}
