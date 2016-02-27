package joephysics62.co.uk.hidato;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import joephysics62.co.uk.grid.Coordinate;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Hidato {
  private final Set<Coordinate> _grid;
  private final BiMap<Integer, Coordinate> _path;
  private final Integer _height;
  private final Integer _width;

  private Hidato(final Set<Coordinate> grid, final BiMap<Integer, Coordinate> path) {
    _path = path;
    _grid = Collections.unmodifiableSet(grid);
    _height = max(grid, Coordinate::getRow);
    _width = max(grid, Coordinate::getCol);
  }

  private int max(final Set<Coordinate> grid, final Function<Coordinate, Integer> func) {
    return grid.stream().map(func).max(Integer::compare).get();
  }

  public HidatoSolution solve() {
    final List<BiMap<Integer, Coordinate>> solns = new ArrayList<>();
    recurse(_path.get(1), _path, solns);
    if (solns.isEmpty()) {
      return new HidatoSolution(Optional.empty(), SolutionType.NONE);
    }
    return new HidatoSolution(Optional.of(new Hidato(_grid, solns.get(0))),
                              solns.size() > 1 ? SolutionType.MULTIPLE : SolutionType.UNIQUE);
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

  public static Hidato read(final Path file) throws IOException {
    final Set<Coordinate> grid = new LinkedHashSet<>();
    final BiMap<Integer, Coordinate> path = HashBiMap.create();
    PuzzleReader.read(file, (cell, coord) -> {
      if ("//".equals(cell)) {
        return;
      }
      grid.add(coord);
      if (cell.matches("[0-9]+")) {
        final Integer intValue = Integer.valueOf(cell);
        path.put(intValue, coord);
      }
    });
    return new Hidato(grid, path);
  }

  public void write(final PrintStream out) {
    final BiMap<Coordinate, Integer> inverse = _path.inverse();
    PuzzleWriter.newWriter(_height, _width)
                .writeToStream(out, coord -> {
                  final Integer integer = inverse.get(coord);
                  if (integer == null) {
                    return "//";
                  }
                  if (integer < 10) {
                    return " " + integer;
                  }
                  return integer.toString();
                });
  }

  public void render(final File htmlFile) throws Exception {
    final Path templateFile = Paths.get("templates", "hidato.css");
    final PuzzleRenderer renderer = PuzzleRenderer.newRenderer(templateFile, _height, _width);

    final int cellSize = 50;
    final int fontSize = 7 * cellSize / 10;
    final int verticalOffset = cellSize / 4;

    final BiMap<Coordinate, Integer> inverse = _path.inverse();
    renderer.render(htmlFile, cellSize, (svg, coord) -> {
      if (_grid.contains(coord)) {
        final Integer value = inverse.get(coord);
        if (value != null) {
          final int horizontalOffset = value < 10 ? cellSize / 3 : cellSize / 8;
          svg.addText(Integer.toString(value),
                             (coord.getCol() - 1) * cellSize + horizontalOffset, coord.getRow() * cellSize - verticalOffset,
                             fontSize);
        }
      }
      else {
        svg.addRectangle(cellSize, cellSize, (coord.getCol() - 1) * cellSize, (coord.getRow() - 1) * cellSize);
      }
    });
  }
}
