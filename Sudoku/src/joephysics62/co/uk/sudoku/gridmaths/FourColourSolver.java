package joephysics62.co.uk.sudoku.gridmaths;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Coord;

public class FourColourSolver {
  private Map<Integer, Colour> _solution;

  public final Map<Integer, Colour> calculateColourMap(final int[][] groupIds) {
    final Map<Integer, Set<Coord>> cellsByGroup = new LinkedHashMap<>();
    final Map<Coord, Integer> groupByCoord = new LinkedHashMap<>();
    for (int rowIndex = 0; rowIndex < groupIds.length; rowIndex++) {
      final int[] row = groupIds[rowIndex];
      for (int colIndex = 0; colIndex < row.length; colIndex++) {
        final int id = row[colIndex];
        if (!cellsByGroup.containsKey(id)) {
          cellsByGroup.put(id, new LinkedHashSet<Coord>());
        }
        Coord coord = Coord.of(rowIndex + 1, colIndex + 1);
        cellsByGroup.get(id).add(coord);
        groupByCoord.put(coord, id);
      }
    }
    recurse(new LinkedHashMap<Integer, Colour>(), cellsByGroup, groupByCoord);
    return _solution;
  }

  private void recurse(final Map<Integer, Colour> currentMap, final Map<Integer, Set<Coord>> cellsByGroup, final Map<Coord, Integer> groupByCoord) {
    if (_solution != null) {
      return;
    }
    if (isSolution(currentMap, groupByCoord, false)) {
      _solution = currentMap;
      return;
    }
    for (Integer groupId : cellsByGroup.keySet()) {
      if (currentMap.containsKey(groupId)) {
        continue;
      }
      for (Colour colour : Colour.values()) {
        final Map<Integer, Colour> newMap = new LinkedHashMap<>(currentMap);
        newMap.put(groupId, colour);
        if (isSolution(newMap, groupByCoord, true)) {
          recurse(newMap, cellsByGroup, groupByCoord);
        }
      }
    }
  }

  private boolean isSolution(Map<Integer, Colour> candidate, Map<Coord, Integer> groupByCoord, boolean partial) {
    if (!partial && !candidate.keySet().containsAll(groupByCoord.values())) {
      return false;
    }
    for (final Coord coord : groupByCoord.keySet()) {
      final Integer groupId = groupByCoord.get(coord);
      final Colour thisColour = candidate.get(groupId);
      if (thisColour == null) {
        if (partial) {
          continue;
        }
        else {
          return false;
        }
      }
      final List<Coord> neighbours = Arrays.asList(
          Coord.of(coord.getRow() - 1, coord.getCol()),
          Coord.of(coord.getRow() + 1, coord.getCol()),
          Coord.of(coord.getRow(), coord.getCol() - 1),
          Coord.of(coord.getRow(), coord.getCol() + 1)
      );
      for (final Coord neighbour : neighbours) {
        if (groupByCoord.keySet().contains(neighbour)) {
          if (!groupByCoord.get(neighbour).equals(groupId) && thisColour == candidate.get(groupByCoord.get(neighbour))) {
            return false;
          }
        }
      }
    }
    return true;
  }

}
