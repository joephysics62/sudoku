package joephysics62.co.uk.hidato;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

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
      return "//";
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
