package joephysics62.co.uk.puzzle.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.puzzle.Puzzle2D;
import joephysics62.co.uk.puzzle.Puzzle2DReader;

public abstract class Puzzle2DReaderImpl<T extends Puzzle2D> implements Puzzle2DReader<T> {
  protected void read(final Path file, final BiConsumer<String, Coordinate> handler) throws IOException {
    read(Files.readAllLines(file), handler);
  }

  protected void read(final List<String> lines, final BiConsumer<String, Coordinate> handler) {
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
