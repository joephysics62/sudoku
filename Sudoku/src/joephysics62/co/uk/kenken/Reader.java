package joephysics62.co.uk.kenken;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import joephysics62.co.uk.kenken.constraint.ArithmeticConstraint;
import joephysics62.co.uk.kenken.grid.Coordinate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Reader {

  private static final String EXAMPLE = "examples\\kenken\\times-3604.json";

  public static void main(final String[] args) throws IOException {
    new Reader().read(new File(EXAMPLE));
  }

  public KenKen read(final File file) throws IOException {
    final ObjectMapper om = new ObjectMapper();
    final PuzzleFile puzzle = om.readValue(file, PuzzleFile.class);
    validate(puzzle);
    return new KenKen(puzzle.getHeight(), puzzle.getArithmeticConstraints());
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
