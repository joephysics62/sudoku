package joephysics62.co.uk.hidato;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import joephysics62.co.uk.grid.Coordinate;

public interface PuzzleReader {
  public static void read(final Path file, final BiConsumer<String, Coordinate> handler) throws IOException {
    final List<String> lines = Files.readAllLines(file);
    for (int row = 1; row <= lines.size(); row++) {
      final String line = lines.get(row - 1);
      final String[] cells = line.split("\\|");
      final List<String> cellsList = Arrays.asList(cells).subList(1, cells.length);
      for (int col = 1; col <= cellsList.size(); col++) {
        final String cell = cellsList.get(col - 1).trim();
        handler.accept(cell, Coordinate.of(row, col));
      }
    }
  }
}
