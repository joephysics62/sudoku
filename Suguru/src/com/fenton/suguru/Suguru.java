package com.fenton.suguru;

import static java.util.function.Function.identity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Suguru {

  private final int _width;
  private final int _height;
  private final Map<Coord, Group> _groupByCoord;
  private final Grid<Integer> _initialGrid;

  public Suguru(final int width, final int height, final int[][] clues, final String[][] groups) {
    _width = width;
    _height = height;
    _groupByCoord = readGroups(groups);
    _initialGrid = calculateInitialGrid(clues, _groupByCoord);
  }

  public Solution<Integer> solve() {
    final Grid<Integer> workingGrid = _initialGrid.clone();
    Coord.overGrid(_width, _height)
         .forEach(from -> from.surroundsWithDiagonals(_width, _height)
                              .forEach(to -> applyConstraint(from, to, workingGrid)));

    workingGrid.traverse(new ConsolePrintingVisitor<>(System.out));

    for (final Group group : _groupByCoord.values()) {
      for (final Coord from : group.getCoords()) {
        for (final Coord to : group.getCoords()) {
          if (!from.equals(to)) {
            applyConstraint(from, to, workingGrid);
          }
        }
      }
    }
    workingGrid.traverse(new ConsolePrintingVisitor<>(System.out));

    return new Solution<>(SolutionType.NONE, Optional.<Grid<Integer>>empty());
  }

  public void applyConstraint(final Coord from, final Coord to, final Grid<Integer> grid) {
    final Set<Integer> fromValues = grid.values(from);
    if (fromValues.size() > 1) {
      return;
    }
    if (fromValues.size() == 0) {
      // bad soln
      return;
    }
    final Integer fromValueFixed = fromValues.iterator().next();
    grid.removeValue(to, fromValueFixed);
  }

  public Grid<Integer> getInitialGrid() {
    return _initialGrid;
  }

  private Map<Coord, Group> readGroups(final String[][] groups) {
    final Map<Coord, Group> groupByCoord = new LinkedHashMap<>();

    final Map<String, Group> groupDefs = new LinkedHashMap<>();
    Coord.overGrid(_width, _height).forEach(coord -> {
      final String groupString = coord.fromArray(groups);
      if (!groupDefs.containsKey(groupString)) {
        groupDefs.put(groupString, new Group());
      }
      final Group group = groupDefs.get(groupString);
      group.addToGroup(coord);

      groupByCoord.put(coord, group);
    });
    return groupByCoord;
  }

  private Grid<Integer> calculateInitialGrid(final int[][] clues, final Map<Coord, Group> groupByCoord) {
    final Map<Coord, Set<Integer>> grid =
        Coord.overGrid(_width, _height)
             .collect(Collectors.toMap(identity(), coord -> {
               final int given = coord.fromIntArray(clues);
               final Group group = groupByCoord.get(coord);
               return given > 0 ? Collections.<Integer>singleton(given) : intSet(group.size());
     }));
    return new Grid<>(grid, _width, _height);
  }

  private Set<Integer> intSet(final int size) {
    return IntStream.rangeClosed(1, size)
                    .mapToObj(Integer::valueOf)
                    .collect(Collectors.toSet());
  }

  public static Suguru fromFile(final Path suguruFile) throws IOException {
    final List<String> lines = Files.readAllLines(suguruFile);
    final int width = lines.get(0).split("\\|").length - 1;
    final int height = lines.indexOf("");

    final int[][] clues = new int[height][width];
    final String[][] groups = new String[height][width];

    for (int row = 0; row < height; row++) {
      final String clueString = lines.get(row);
      final String groupString = lines.get(1 + height + row);
      clues[row] = streamRow(clueString).mapToInt(Suguru::fromClue).toArray();
      groups[row] = streamRow(groupString).toArray(String[]::new);
    }
    return new Suguru(width, height, clues, groups);
  }

  private static Stream<String> streamRow(final String rowString) {
    return Arrays.stream(rowString.split("\\|"))
                 .skip(1).map(String::trim);
  }

  private static int fromClue(final String clueString) {
    if (clueString.isEmpty()) {
      return 0;
    }
    return Integer.valueOf(clueString);
  }

}
