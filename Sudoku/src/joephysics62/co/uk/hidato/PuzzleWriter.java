package joephysics62.co.uk.hidato;

import java.io.PrintStream;
import java.util.function.Function;

import joephysics62.co.uk.grid.Coordinate;

public class PuzzleWriter {

  private final int _height;
  private final int _width;

  private PuzzleWriter(final int height, final int width) {
    _height = height;
    _width = width;
  }

  public static PuzzleWriter newWriter(final int height, final int width) {
    return new PuzzleWriter(height, width);
  }

  public void writeToStream(final PrintStream out, final Function<Coordinate, String> func) {
    for (int row = 1; row <= _height; row++) {
      for (int col = 1; col <= _width; col++) {
        out.print("|");
        out.print(func.apply(Coordinate.of(row, col)));
      }
      out.println("|");
    }
  }
}
