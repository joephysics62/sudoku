package joephysics62.co.uk.kenken;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import joephysics62.co.uk.backtrackingsudoku.Operator;
import joephysics62.co.uk.constraint.Constraint;
import joephysics62.co.uk.constraint.UniqueConstraint;
import joephysics62.co.uk.constraint.arithmetic.AdditionConstraint;
import joephysics62.co.uk.constraint.arithmetic.DivisionConstraint;
import joephysics62.co.uk.constraint.arithmetic.MultiplicationConstraint;
import joephysics62.co.uk.constraint.arithmetic.SubtractionConstraint;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.grid.GridCSVReader;

public class Reader {

  private final Pattern _pattern = Pattern.compile("([0-9]+)([x+-/])?");

  private static class ConstraintBuilder {
    private final int _target;
    private final Operator _operator;
    private final Set<Coordinate> _coords = new LinkedHashSet<>();

    private ConstraintBuilder(final int target, final Operator operator) {
      _target = target;
      _operator = operator;
    }

    public void add(final Coordinate coordinate) {
      _coords.add(coordinate);
    }

    private Constraint build(final int maximum) {
      final Set<Coordinate> coords = Collections.unmodifiableSet(_coords);
      switch (_operator) {
      case ADD:
        return new AdditionConstraint(coords, _target, maximum);
      case SUBTRACT:
        return new SubtractionConstraint(coords, _target, maximum);
      case DIVIDE:
        return new DivisionConstraint(coords, _target, maximum);
      case MULTIPLY:
        return new MultiplicationConstraint(coords, _target, maximum);
      default:
        throw new RuntimeException();
      }
    }
  }

  private void recurseAddCells(final Coordinate co, final ConstraintBuilder builder, final int maximum, final Map<Coordinate, String> cellMap) {
   builder.add(co);
   navigate(builder, maximum, cellMap, co.left(), ">");
   navigate(builder, maximum, cellMap, co.right(), "<");
   navigate(builder, maximum, cellMap, co.up(), "V");
   navigate(builder, maximum, cellMap, co.down(), "^");
  }

  private void navigate(final ConstraintBuilder builder, final int maximum,
      final Map<Coordinate, String> cellMap, final Coordinate other,
      final String otherContinueValue) {
    if (cellMap.containsKey(other)) {
       final String cell = cellMap.get(other);
       if (cell.equals(otherContinueValue)) {
         recurseAddCells(other, builder, maximum, cellMap);
       }
     }
  }

  public Puzzle read(final File file) throws IOException {
    final Map<Coordinate, String> cellMap = new LinkedHashMap<>();
    final int maximum = GridCSVReader
        .newReader()
        .readFile(file, (coordinate, cell) -> {
          cellMap.put(coordinate, cell);
        });

    final Set<ConstraintBuilder> builders = new LinkedHashSet<>();
    cellMap.forEach((co, c) -> {
      final Matcher matcher = _pattern.matcher(c);
      if (matcher.matches()) {
        final int target = Integer.valueOf(matcher.group(1));
        final Operator operator = Operator.fromString(matcher.group(2));
        final ConstraintBuilder builder = new ConstraintBuilder(target, operator);
        builders.add(builder);
        recurseAddCells(co, builder, maximum, cellMap);
      }
    });

    final Stream<Constraint> definedConstraints = builders.stream()
                                                          .map(cb -> cb.build(maximum));

    // rows
    final Stream<Constraint> horizConstraints =
        newStream(maximum)
          .mapToObj(row -> newStream(maximum)
                           .mapToObj(col -> Coordinate.of(row, col))
                           .collect(Collectors.toSet()))
          .map(UniqueConstraint::new);

   final Stream<Constraint> vertConstraints =
        newStream(maximum)
          .mapToObj(col -> newStream(maximum)
                             .mapToObj(row -> Coordinate.of(row, col))
                             .collect(Collectors.toSet()))
          .map(UniqueConstraint::new);
    final Stream<Constraint> all = Stream.concat(definedConstraints,
                                                 Stream.concat(horizConstraints, vertConstraints));
    return new Puzzle(maximum, all);
  }

  private static IntStream newStream(final int maximum) {
    return IntStream.rangeClosed(1, maximum);
  }

}
