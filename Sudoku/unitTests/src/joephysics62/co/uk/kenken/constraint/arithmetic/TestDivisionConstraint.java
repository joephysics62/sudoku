package joephysics62.co.uk.kenken.constraint.arithmetic;

import static junit.framework.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import joephysics62.co.uk.constraint.Constraint;
import joephysics62.co.uk.constraint.arithmetic.DivisionConstraint;
import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.kenken.Answer;

import org.junit.Test;

import com.google.common.collect.Sets;

public class TestDivisionConstraint {

  private final Coordinate _left = Coordinate.of(1, 1);
  private final Coordinate _right = Coordinate.of(1, 2);
  private final int _maximum = 6;
  private final Set<Coordinate> _coords = Sets.newHashSet(_left, _right);
  private final Constraint _constraint = new DivisionConstraint(_coords, 2, _maximum);
  private final Answer _answer = new Answer(_coords, _maximum);

  @Test
  public void testApplyConstraint() {
    final DivisionConstraint constraint = new DivisionConstraint(_coords, 3, _maximum);
    possiblesSet(constraint).forEach(r -> assertEquals(range(1, _maximum), r));
    constraint.applyConstraint(_answer);
    constraint.applyToCells(_answer, c -> assertEquals(Sets.newHashSet(1, 2, 3, 6), c.getPossibles()));
    possiblesSet(constraint).forEach(r -> assertEquals(Sets.newHashSet(1, 2, 3, 6), r));
  }

  private Stream<Set<Integer>> possiblesSet(final Constraint constraint) {
    return constraint.cells(_answer).map(Cell::getPossibles);
  }

  private Set<Integer> range(final int from, final int to) {
    return IntStream.rangeClosed(from, to).boxed().collect(Collectors.toSet());
  }

  @Test
  public void testIsSatisfiedBy() {
    assertEquals(true, isSatisfied());
    setValue(_left, 6);
    assertEquals(true, isSatisfied());
    setValue(_right, 3);
    assertEquals(true, isSatisfied());
    setValue(_right, 2);
    assertEquals(false, isSatisfied());

    setValue(_left, 3);
    setValue(_right, 6);
    assertEquals(true, isSatisfied());

    setValue(_left, 1);
    setValue(_right, 2);
    assertEquals(true, isSatisfied());
  }

  private void setValue(final Coordinate coord, final int value) {
    _answer.setSolvedValue(coord, value);
  }

  private boolean isSatisfied() {
    return _constraint.isSatisfiedBy(_answer);
  }

}
