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

import joephysics62.co.uk.kenken.constraint.Constraint;
import joephysics62.co.uk.kenken.constraint.UniqueConstraint;
import joephysics62.co.uk.kenken.constraint.arithmetic.AdditionConstraint;
import joephysics62.co.uk.kenken.constraint.arithmetic.DivisionConstraint;
import joephysics62.co.uk.kenken.constraint.arithmetic.MultiplicationConstraint;
import joephysics62.co.uk.kenken.constraint.arithmetic.SubtractionConstraint;
import joephysics62.co.uk.kenken.grid.Coordinate;
import joephysics62.co.uk.kenken.grid.GridCSVReader;

public class Reader {

  private final Pattern _pattern = Pattern.compile("([0-9]+)([x+-/])?");
  private final Map<Coordinate, ConstraintBuilder> _builders = new LinkedHashMap<>();

  private static final String EXAMPLE = "examples\\kenken\\times-3604.csv";

  public static void main(final String[] args) throws IOException {
    new Reader().read(new File(EXAMPLE));
  }

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

  public KenKen read(final File file) throws IOException {
    _builders.clear();
    final int maximum = GridCSVReader
        .newReader()
        .readFile(file, (coordinate, cell) -> {
            final ConstraintBuilder builder = getBuilder(coordinate, cell);
            builder.add(coordinate);
            _builders.put(coordinate, builder);
        });
    final Stream<Constraint> definedConstraints =
        _builders.values()
                 .stream()
                 .distinct()
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
    return null;
  }

  private static IntStream newStream(final int maximum) {
    return IntStream.rangeClosed(1, maximum);
  }

  private ConstraintBuilder getBuilder(final Coordinate coordinate, final String cell) {
    final Matcher matcher = _pattern.matcher(cell);
    if (matcher.matches()) {
      final int target = Integer.valueOf(matcher.group(1));
      final Operator operator = Operator.fromString(matcher.group(2));
      return new ConstraintBuilder(target, operator);
    }
    else {
      return _builders.get(readOther(coordinate, cell));
    }
  }

  private Coordinate readOther(final Coordinate coordinate, final String cell) {
    switch (cell) {
    case "^":
      return coordinate.up().get();
    case "<":
      return coordinate.left().get();
    default:
      throw new RuntimeException();
    }
  }

}
