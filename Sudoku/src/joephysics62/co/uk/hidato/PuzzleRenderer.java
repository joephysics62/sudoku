package joephysics62.co.uk.hidato;

import java.io.File;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.xml.SvgBuilder;

public class PuzzleRenderer {
  private final Path _cssFile;
  private final int _height;
  private final int _width;

  private PuzzleRenderer(final Path cssFile, final int height, final int width) {
    _cssFile = cssFile;
    _height = height;
    _width = width;
  }

  public static PuzzleRenderer newRenderer(final Path cssFile, final int height, final int width) {
    return new PuzzleRenderer(cssFile, height, width);
  }

  public void render(final File htmlFile, final int cellSize, final BiConsumer<SvgBuilder, Coordinate> handler) throws Exception {
    final int gridHeight = _height * cellSize;
    final int gridWidth = _width * cellSize;

    final SvgBuilder svgBuilder = SvgBuilder.newBuilder();
    svgBuilder.addSvg(gridWidth + cellSize, gridHeight + cellSize);
    svgBuilder.addCss(_cssFile);

    IntStream.rangeClosed(0, _height).forEach(x -> {
      svgBuilder.addLine(0, x * cellSize, gridWidth, x * cellSize);
    });
    IntStream.rangeClosed(0, _width).forEach(x -> {
      svgBuilder.addLine(x * cellSize, 0, x * cellSize, gridHeight);
    });
    for (int row = 1; row <= _height; row++) {
      for (int col = 1; col <= _width; col++) {
        handler.accept(svgBuilder, Coordinate.of(row, col));
      }
    }
    svgBuilder.write(htmlFile);

  }
}
