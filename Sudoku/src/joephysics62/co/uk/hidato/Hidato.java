package joephysics62.co.uk.hidato;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.puzzle.Puzzle2D;
import joephysics62.co.uk.puzzle.impl.Puzzle2DImpl;
import joephysics62.co.uk.puzzle.impl.Puzzle2DReaderImpl;
import joephysics62.co.uk.xml.SvgBuilder;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Hidato extends Puzzle2DImpl<BiMap<Integer, Coordinate>> {
  private final Set<Coordinate> _grid;
  private final BiMap<Integer, Coordinate> _path;
  private final BiMap<Integer, Coordinate> _startClues;

  private Hidato(final Set<Coordinate> grid, final BiMap<Integer, Coordinate> path, final BiMap<Integer, Coordinate> startClues) {
    super(max(grid, Coordinate::getRow), max(grid, Coordinate::getCol));
    _path = path;
    _startClues = startClues;
    _grid = Collections.unmodifiableSet(grid);
  }

  private static int max(final Set<Coordinate> grid, final Function<Coordinate, Integer> func) {
    return grid.stream().map(func).max(Integer::compare).get();
  }

  @Override
  protected List<BiMap<Integer, Coordinate>> solveForSolutionList() {
    final List<BiMap<Integer, Coordinate>> solns = new ArrayList<>();
    recurse(_path.get(1), _path, solns);
    return solns;
  }

  @Override
  protected Puzzle2D puzzleForSolution(final BiMap<Integer, Coordinate> soln) {
    return new Hidato(_grid, soln, _startClues);
  }

  private void recurse(final Coordinate coord, final BiMap<Integer, Coordinate> current, final List<BiMap<Integer, Coordinate>> solns) {
    if (solns.size() > 2) {
      return;
    }
    if (current.size() == _grid.size()) {
      solns.add(current);
      return;
    }
    final Integer nextValue = current.inverse().get(coord) + 1;
    if (current.containsKey(nextValue)) {
      final Coordinate coordOfNext = current.get(nextValue);
      if (coord.arounds().anyMatch(coordOfNext::equals)) {
        recurse(coordOfNext, current, solns);
      }
      return;
    }
    coord.arounds()
         .filter(_grid::contains)
         .filter(c -> !current.containsValue(c))
         .forEach(c -> {
           final HashBiMap<Integer, Coordinate> copy = HashBiMap.create(current);
           copy.put(nextValue, c);
           recurse(c, copy, solns);
         });
  }

  @Override
  protected String clueAt(final Coordinate coord) {
    return valueAt(coord, _startClues);
  }

  @Override
  protected String answerAt(final Coordinate coord) {
    return valueAt(coord, _path);
  }

  private String valueAt(final Coordinate coord, final BiMap<Integer, Coordinate> path) {
    final BiMap<Coordinate, Integer> inverse = path.inverse();
    final Integer integer = inverse.get(coord);
    if (integer == null) {
      return _grid.contains(coord) ? "  " : "//";
    }
    if (integer < 10) {
      return " " + integer;
    }
    return integer.toString();
  }

  @Override
  protected Path cssTemplate() {
    return Paths.get("templates", "hidato.css");
  }

  @Override
  protected void renderAnswer(final SvgBuilder svg, final Coordinate coord, final int cellSize) {
    render(svg, coord, cellSize, _path);
  }

  @Override
  protected void renderPuzzle(final SvgBuilder svg, final Coordinate coord, final int cellSize) {
    render(svg, coord, cellSize, _startClues);
  }

  private void render(final SvgBuilder svg, final Coordinate coord, final int cellSize, final BiMap<Integer, Coordinate> path) {
    final int fontSize = 7 * cellSize / 12;
    final BiMap<Coordinate, Integer> inverse = path.inverse();
    if (_grid.contains(coord)) {
      final Integer value = inverse.get(coord);
      if (value != null) {
        final int x = (coord.getCol() - 1) * cellSize + cellSize / 2;
        final int y = coord.getRow() * cellSize - (cellSize - fontSize) / 2;
        svg.addText(Integer.toString(value), x, y, fontSize);
      }
    }
    else {
      final int x = (coord.getCol() - 1) * cellSize;
      final int y = (coord.getRow() - 1) * cellSize;
      svg.addRectangle(cellSize, cellSize, x, y);
    }
  }

  public static Hidato create(final int height, final int width) {
    final Set<Coordinate> grid = new LinkedHashSet<>();
    for (int row = 1; row <= height; row++) {
      for (int col = 1; col <= width; col++) {
        grid.add(Coordinate.of(row, col));
      }
    }
    if (grid.size() < 2) {
      throw new IllegalArgumentException();
    }
    // choose random starting point.
    final Random random = new Random();
    final Coordinate start = randCoord(height, width, random);
    Coordinate end;
    do {
      end = randCoord(height, width, random);
    } while (start.equals(end));
    final HashBiMap<Integer, Coordinate> clues = HashBiMap.create();
    clues.put(1, start);
    clues.put(grid.size(), end);

    final List<BiMap<Integer, Coordinate>> solns = new ArrayList<>();
    recurseBuildFinished(random, 1, grid, clues, solns);
    return new Hidato(grid, null, clues);
  }

  private static void recurseBuildFinished(final Random random, final Integer currentStep, final Set<Coordinate> grid, final BiMap<Integer, Coordinate> currentClues, final List<BiMap<Integer, Coordinate>> solns) {
    new Hidato(grid, currentClues, currentClues).writePuzzle(System.out);
    if (!solns.isEmpty()) {
      return;
    }
    if (grid.size() == currentClues.size()) {
      solns.add(currentClues);
      return;
    }
    final Coordinate coord = currentClues.get(currentStep);
    if (currentClues.containsKey(currentStep + 1)) {
      final boolean isValidStep = coord.arounds().anyMatch(c -> c.equals(currentClues.get(currentStep + 1)));
      if (isValidStep) {
        recurseBuildFinished(random, currentStep + 1, grid, currentClues, solns);
      }
      return;
    }
    final List<Coordinate> avaiableSpaces = coord.arounds().filter(c -> grid.contains(c))
        .filter(c -> !currentClues.containsValue(c))
        .collect(Collectors.toList());
    if (avaiableSpaces.isEmpty()) {
      return;
    }
    if (avaiableSpaces.size() == 1) {
      currentClues.put(currentStep + 1, avaiableSpaces.get(0));
      recurseBuildFinished(random, currentStep + 1, grid, currentClues, solns);
    }
    else {
      Collections.shuffle(avaiableSpaces);
      for (final Coordinate availableSpace : avaiableSpaces) {
        final BiMap<Integer, Coordinate> newCurrent = HashBiMap.create(currentClues);
        newCurrent.put(currentStep + 1, availableSpace);
        recurseBuildFinished(random, currentStep + 1, grid, newCurrent, solns);
      }
    }

  }

  private static Coordinate randCoord(final int height, final int width, final Random random) {
    return Coordinate.of(random.nextInt(height) + 1, random.nextInt(width) + 1);
  }

  public static class Reader extends Puzzle2DReaderImpl<Hidato> {

    private Reader() {};
    public static Reader create() { return new Reader(); }

    @Override
    public Hidato read(final Path file) throws IOException {
      final Set<Coordinate> grid = new LinkedHashSet<>();
      final BiMap<Integer, Coordinate> path = HashBiMap.create();
      read(file, (cell, coord) -> {
        if ("//".equals(cell)) {
          return;
        }
        grid.add(coord);
        if (cell.matches("[0-9]+")) {
          final Integer intValue = Integer.valueOf(cell);
          path.put(intValue, coord);
        }
      });
      return new Hidato(grid, path, path);
    }

  }
}
