package joephysics62.co.uk.puzzle.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.puzzle.Puzzle2D;
import joephysics62.co.uk.puzzle.PuzzleSolution;
import joephysics62.co.uk.puzzle.SolutionType;
import joephysics62.co.uk.xml.SvgBuilder;

public abstract class Puzzle2DImpl<S> implements Puzzle2D {

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
  public final PuzzleSolution<? extends Puzzle2D> solve() {
    final List<S> solutions = solveForSolutionList();
    if (solutions.isEmpty()) {
      return new PuzzleSolution<Puzzle2D>(Optional.empty(), SolutionType.NONE);
    }
    final Puzzle2D solvedCodeword = puzzleForSolution(solutions.get(0));
    return new PuzzleSolution<Puzzle2D>(Optional.of(solvedCodeword), solutions.size() > 1 ? SolutionType.MULTIPLE : SolutionType.UNIQUE);
  }

  protected abstract List<S> solveForSolutionList();

  protected abstract Puzzle2D puzzleForSolution(S soln);

  @Override
  public void writeAnswer(final PrintStream out) {
    write(out, this::answerAt);
  }
  protected abstract String answerAt(final Coordinate coord);

  private static interface GridHandle {
    void render(SvgBuilder builder, Coordinate coord, int cellSize);
  }

  @Override
  public void renderPuzzle(final File htmlFile, final int cellSize) throws Exception {
    final GridHandle gridHandle = new GridHandle() {
      @Override
      public void render(final SvgBuilder builder, final Coordinate coord, final int cellSize) {
        renderPuzzle(builder, coord, cellSize);
      }
    };
    render(htmlFile, cellSize, gridHandle);
  }

  @Override
  public void renderAnswer(final File htmlFile, final int cellSize) throws Exception {
    final GridHandle gridHandle = new GridHandle() {
      @Override
      public void render(final SvgBuilder builder, final Coordinate coord, final int cellSize) {
        renderAnswer(builder, coord, cellSize);
      }
    };
    render(htmlFile, cellSize, gridHandle);
  }

  private void render(final File htmlFile, final int cellSize, final GridHandle gridHandle) throws ParserConfigurationException, IOException, TransformerException {
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
        gridHandle.render(svgBuilder, Coordinate.of(row, col), cellSize);
      }
    }
    svgBuilder.write(htmlFile);
  }

  protected abstract void renderAnswer(final SvgBuilder builder, final Coordinate coord, int cellSize);
  protected abstract void renderPuzzle(final SvgBuilder builder, final Coordinate coord, int cellSize);

  protected abstract Path cssTemplate();
}
