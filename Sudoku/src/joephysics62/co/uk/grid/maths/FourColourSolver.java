package joephysics62.co.uk.grid.maths;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.arrays.IntegerArrayGrid;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;


public class FourColourSolver {
  private Map<Integer, Colour> _solution;

  public final Map<Integer, Colour> calculateColourMap(final IntegerArrayGrid groupIds) {
    final Multimap<Integer, Coord> cellsByGroup = LinkedHashMultimap.create();
    final Map<Coord, Integer> groupByCoord = new LinkedHashMap<>();
    for (final Coord coord : groupIds) {
      final int id = groupIds.get(coord);
      cellsByGroup.put(id, coord);
      groupByCoord.put(coord, id);
    }
    recurse(new LinkedHashMap<Integer, Colour>(), cellsByGroup, groupByCoord);
    return _solution;
  }

  private void recurse(final Map<Integer, Colour> currentMap, final Multimap<Integer, Coord> cellsByGroup, final Map<Coord, Integer> groupByCoord) {
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
