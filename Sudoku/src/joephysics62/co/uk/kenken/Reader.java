package joephysics62.co.uk.kenken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import joephysics62.co.uk.kenken.constraint.ArithmeticConstraint;
import joephysics62.co.uk.kenken.grid.Coordinate;

public class Reader {

  private static final String EXAMPLE = "examples\\kenken\\times-3604.csv";

  public static void main(final String[] args) throws IOException {
    new Reader().read(new File(EXAMPLE));
  }


  public KenKen read(final File file) throws IOException {
    final Pattern constraintPattern = Pattern.compile("([0-9]+)([x+-/])?");
    int row = 1;
    for (final String line : Files.readAllLines(file.toPath())) {
      int col = 1;
      for (final String rawCell : line.split(",")) {
        final Coordinate coordinate = Coordinate.of(row, col);
        final String cellStringT = rawCell.trim();
        final Matcher matcher = constraintPattern.matcher(cellStringT);
        if (matcher.matches()) {
          final int target = Integer.valueOf(matcher.group(1));
          final Operator operator = Operator.fromString(matcher.group(2));
          System.out.println(target + " " + operator);
        }
        col++;
      }
      row++;
    }
//    final ObjectMapper om = new ObjectMapper();
//    final PuzzleFile puzzle = om.readValue(file, PuzzleFile.class);
//    validate(puzzle);
//    return new KenKen(puzzle.getHeight(), puzzle.getArithmeticConstraints());
    return null;
  }

  public Map<Coordinate, ArithmeticConstraint> validate(final PuzzleFile puzzle) {
    final Map<Coordinate, ArithmeticConstraint> map = new LinkedHashMap<>();
    for (final ArithmeticConstraint arithmeticConstraint : puzzle.getArithmeticConstraints()) {
      for (final Coordinate coordinate : arithmeticConstraint.getCoords()) {
        if (null != map.put(coordinate, arithmeticConstraint)) {
          throw new RuntimeException(coordinate + " is associated with multiple arithmetic constraints");
        }
      }
    }
    for (int row = 1; row <= puzzle.getHeight(); row++) {
      for (int col = 1; col <= puzzle.getHeight(); col++) {
        final Coordinate coord = Coordinate.of(row, col);
        if (!map.containsKey(coord)) {
          throw new RuntimeException(coord + " is not in any arithmetic constraint");
        }
      }
    }
    return map;
  }
}
