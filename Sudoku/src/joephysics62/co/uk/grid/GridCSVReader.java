package joephysics62.co.uk.grid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class GridCSVReader {
  private GridCSVReader() {
  }

  public static GridCSVReader newReader() {
    return new GridCSVReader();
  }

  public int readFile(final File file, final CellReader cellReader) throws IOException {
    int row = 0;
    for (final String line : Files.readAllLines(file.toPath())) {
      row++;
      int col = 0;
      for (final String rawCell : line.split(",")) {
        cellReader.accept(Coordinate.of(row, ++col), rawCell.trim());
      }
    }
    return row;
  }
}
