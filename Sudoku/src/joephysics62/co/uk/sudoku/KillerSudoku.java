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

public class KillerSudoku extends NumericBacktrackPuzzle {

  private final Map<Coordinate, ArithmeticGroup> _groupLookup;
  private final int _subsize;

  public KillerSudoku(final int[][] puzzle, final int size, final int subsize, final List<ArithmeticGroup> groups) {
    super(puzzle, size);
    _subsize = subsize;
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
    final int subRowStart = row - row % _subsize;
    final int subColStart = col - col % _subsize;
    for (int subRow = subRowStart; subRow < subRowStart + _subsize; subRow++) {
      for (int subCol = subColStart; subCol < subColStart + _subsize; subCol++) {
        if (answer[subRow][subCol] == candidate) {
          return false;
        }
      }
    }
    final Coordinate candidateCoord = Coordinate.of(row, col);

    final ArithmeticGroup kenKenGroup = _groupLookup.get(candidateCoord);

    int runningTarget = candidate;
    final int target = kenKenGroup.getTarget();
    final int groupSize = kenKenGroup.getCells().size();

    if (candidate > target - (groupSize - 1) * groupSize / 2) {
      return false;
    }
    if (candidate < target - getSize() * (groupSize - 1) + (groupSize - 1) * (groupSize - 2) / 2) {
      return false;
    }

    for (final Coordinate coord : kenKenGroup.getCells()) {
      if (coord.equals(candidateCoord)) {
        continue;
      }
      final int otherVal = answer[coord.getRow()][coord.getCol()];
      if (otherVal == candidate) {
        return false;
      }
      if (otherVal == 0) {
        return true;
      }
      runningTarget += otherVal;
    }
    return runningTarget == target;
  }

  public static KillerSudoku readFile(final Path inputFile) throws IOException {
    final List<String> lines = Files.readAllLines(inputFile);
    final int size = lines.size() - 1;
    final int[][] puzzle = new int[size][size];

    final ListMultimap<String, Coordinate> mmap = ArrayListMultimap.create();
    for (int row = 0; row < size; row++) {
      final String[] rowArr = Arrays.stream(lines.get(row).split("\\|"))
                                  .skip(1).collect(Collectors.toList()).toArray(new String[] {});
      for (int col = 0; col < size; col++) {
        mmap.put(rowArr[col].trim(), Coordinate.of(row, col));
      }
    }
    final String[] groupDefs = lines.get(size).split(",");
    final List<ArithmeticGroup> groups = new ArrayList<>();
    for (final String groupDef : groupDefs) {
      final String[] split = groupDef.split("=");
      final String key = split[0];
      final String targetDef = split[1];
      groups.add(new ArithmeticGroup(Operator.ADD, Integer.valueOf(targetDef), mmap.get(key)));
    }
    return new KillerSudoku(puzzle, size, 3, groups);
  }

}
