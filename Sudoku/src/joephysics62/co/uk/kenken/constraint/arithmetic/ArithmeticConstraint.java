package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.Answer;
import joephysics62.co.uk.kenken.constraint.CoordinateSetConstraint;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public abstract class ArithmeticConstraint extends CoordinateSetConstraint {
  private final int _target;
  private final int _maximum;

  public ArithmeticConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords);
    _target = target;
    _maximum = maximum;
  }

  public int getTarget() { return _target; }

  public int getMaximum() {
    return _maximum;
  }

  @Override
  public final Stream<Coordinate> applyConstraint(final Answer answer) {
    final Set<Integer> eliminations = eliminatedValues().boxed().collect(Collectors.toSet());

    final Set<Coordinate> coords = getCoords();

    final Stream<Coordinate> updated = coords
                                        .stream()
                                        .filter(co -> answer.cellAt(co).removeAll(eliminations));
    if (cells(answer).allMatch(Cell::isSolved)) {
      return updated;
    }
    if (cells(answer).anyMatch(Cell::isSolved)) {
      return handlePartiallySolved(answer);
    }
    return updated;
  }

  protected abstract IntBinaryOperator binaryOperator();

  protected abstract IntBinaryOperator inverseOperator();

  protected abstract IntStream eliminatedValues();

  protected abstract Stream<Coordinate> handlePartiallySolved(Answer answer);


}