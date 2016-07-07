package joephysics62.co.uk.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.grid.Coordinate;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class KenKen extends NumericBacktrackPuzzle {

  private final Map<Coordinate, ArithmeticGroup> _groupLookup;

  public KenKen(final int[][] puzzle, final int size, final List<ArithmeticGroup> groups) {
    super(puzzle, size);
    _groupLookup = new LinkedHashMap<>();
    for (final ArithmeticGroup kenKenGroup : groups) {
      for (final Coordinate coord: kenKenGroup.getCells())  {
        _groupLookup.put(coord, kenKenGroup);
      }
    }
  }

  @Override
  protected boolean isValidMove(final int candidate, final int row, final int col, final int[][] answer) {
    for (int i = 0; i < getSize(); i++) {
      if (answer[row][i] == candidate) {
        return false;
      }
      if (answer[i][col] == candidate) {
        return false;
      }
    }
    final Coordinate candidateCoord = Coordinate.of(row, col);

    final ArithmeticGroup kenKenGroup = _groupLookup.get(candidateCoord);

    int runningTarget = candidate;
    final int target = kenKenGroup.getTarget();
    for (final Coordinate coord : kenKenGroup.getCells()) {
      if (coord.equals(candidateCoord)) {
        continue;
      }
      final int otherVal = answer[coord.getRow()][coord.getCol()];
      if (otherVal == 0) {
        return true;
      }
      switch (kenKenGroup.getOp()) {
      case SUBTRACT:
        return candidate - otherVal == target || otherVal - candidate == target;
      case DIVIDE:
        return candidate / otherVal == target || otherVal / candidate == target;
      case ADD:
        runningTarget += otherVal;
        break;
      case MULTIPLY:
        runningTarget *= otherVal;
        break;
      default:
        throw new UnsupportedOperationException();
      }
    }
    return runningTarget == target;
  }

  public static KenKen readFile(final Path inputFile) throws IOException {
    final List<String> lines = Files.readAllLines(inputFile);
    final int size = lines.size() - 1;
    final int[][] puzzle = new int[size][size];

    final ListMultimap<String, Coordinate> mmap = ArrayListMultimap.create();
    for (int row = 0; row < size; row++) {
      final String[] rowArr = Arrays.stream(lines.get(row).split("\\|"))
                                  .skip(1).collect(Collectors.toList()).toArray(new String[] {});
      for (int col = 0; col < size; col++) {
        mmap.put(rowArr[col], Coordinate.of(row, col));
      }
    }
    final String[] groupDefs = lines.get(size).split(",");
    final List<ArithmeticGroup> groups = new ArrayList<>();
    for (final String groupDef : groupDefs) {
      final String[] split = groupDef.split("=");
      final String key = split[0];
      final String targetDef = split[1];
      final int defLength = targetDef.length();
      final int target = Integer.valueOf(targetDef.substring(0, defLength - 1));
      final Operator operator = Operator.fromString(targetDef.substring(defLength - 1, defLength));
      groups.add(new ArithmeticGroup(operator, target, mmap.get(key)));
    }
    return new KenKen(puzzle, size, groups);
  }

}
