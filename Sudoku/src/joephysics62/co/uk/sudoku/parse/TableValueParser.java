package joephysics62.co.uk.sudoku.parse;

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

  public List<List<T>> parse(final File csv) throws IOException {
    List<List<T>> inputLists = new ArrayList<>();
    for (final String line : Files.readAllLines(csv.toPath(), Charset.forName("UTF-8"))) {
      String[] split = line.split("\\|");
      if (split.length != _puzzleSize + 1) {
        throw new RuntimeException("Bad input: has " + split.length + " |'s on a line: " + line);
      }
      final List<T> rowList = new ArrayList<>();
      for (int i = 1; i < split.length; i++) {
       String cellInput = split[i].trim() ;
       T input = _cellValueReader.parseCellValue(cellInput);
       rowList.add(input);
      }
      inputLists.add(rowList);
    }
    return inputLists;
  }

}
