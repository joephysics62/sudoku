package joephysics62.co.uk.hidato;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    _height = grid.stream().map(Coordinate::getRow).max(Integer::compare).get();
    _width = grid.stream().map(Coordinate::getCol).max(Integer::compare).get();
  }

  public Hidato solve() {
    final List<BiMap<Integer, Coordinate>> solns = new ArrayList<>();
    recurse(_path.get(1), _path, solns);
    return new Hidato(_grid, solns.get(0));
  }

  private void recurse(final Coordinate coord, final BiMap<Integer, Coordinate> current, final List<BiMap<Integer, Coordinate>> solns) {
    if (solns.size() > 0) {
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

    final List<String> lines = Files.readAllLines(file);
    for (int row = 1; row <= lines.size(); row++) {
      final String line = lines.get(row - 1);
      final String[] cells = line.split("\\|");
      final List<String> cellsList = Arrays.asList(cells).subList(1, cells.length);
      for (int col = 1; col <= cellsList.size(); col++) {
        final String cell = cellsList.get(col - 1).trim();
        if ("//".equals(cell)) {
          continue;
        }
        final Coordinate coord = Coordinate.of(row, col);
        grid.add(coord);
        if (cell.matches("[0-9]+")) {
          final Integer intValue = Integer.valueOf(cell);
          path.put(intValue, coord);
        }
      }
    }
    return new Hidato(grid, path);
  }

  public void writeToPrintStream(final PrintStream out) {
    final BiMap<Coordinate, Integer> inverse = _path.inverse();
    for (int row = 1; row <= _height; row++) {
      for (int col = 1; col <= _width; col++) {
        out.print("|");
        final Integer integer = inverse.get(Coordinate.of(row, col));
        if (integer == null) {
          out.print("//");
        }
        else if (integer < 10) {
          out.print(" " + integer);
        }
        else {
          out.print(integer);
        }
      }
      out.println("|");
    }
  }
}
