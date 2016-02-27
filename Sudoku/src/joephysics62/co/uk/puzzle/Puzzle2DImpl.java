package joephysics62.co.uk.puzzle;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.IntStream;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.xml.SvgBuilder;

public abstract class Puzzle2DImpl implements Puzzle2D {

  protected final int _height;
  protected final int _width;

  public Puzzle2DImpl(final int height, final int width) {
    _height = height;
    _width = width;
  }

  @Override
  public void writePuzzle(final PrintStream out) {
    write(out, this::clueAt);
  }
  protected abstract String clueAt(final Coordinate coord);

  private void write(final PrintStream out, final Function<Coordinate, String> func) {
    for (int row = 1; row <= _height; row++) {
      for (int col = 1; col <= _width; col++) {
        out.print("|");
        out.print(func.apply(Coordinate.of(row, col)));
      }
      out.println("|");
    }
  }

  @Override
  public void writeAnswer(final PrintStream out) {
    write(out, this::answerAt);
  }
  protected abstract String answerAt(final Coordinate coord);

  @Override
  public void render(final File htmlFile, final int cellSize) throws Exception {
    final int gridHeight = _height * cellSize;
    final int gridWidth = _width * cellSize;

    final SvgBuilder svgBuilder = SvgBuilder.newBuilder();
    svgBuilder.addSvg(gridWidth + cellSize, gridHeight + cellSize);
    svgBuilder.addCss(cssTemplate());

    IntStream.rangeClosed(0, _height).forEach(x -> {
      svgBuilder.addLine(0, x * cellSize, gridWidth, x * cellSize);
    });
    IntStream.rangeClosed(0, _width).forEach(x -> {
      svgBuilder.addLine(x * cellSize, 0, x * cellSize, gridHeight);
    });
    for (int row = 1; row <= _height; row++) {
      for (int col = 1; col <= _width; col++) {
        handle(svgBuilder, Coordinate.of(row, col), cellSize);
      }
    }
    svgBuilder.write(htmlFile);
  }
  protected abstract void handle(final SvgBuilder builder, final Coordinate coord, int cellSize);
  protected abstract Path cssTemplate();
}
