package joephysics62.co.uk.kenken.constraint.arithmetic;

import static junit.framework.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import joephysics62.co.uk.constraint.Constraint;
import joephysics62.co.uk.constraint.arithmetic.AdditionConstraint;
import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.kenken.Answer;

import org.junit.Test;

import com.google.common.collect.Sets;

public class TestAdditionConstraint {

  private final Coordinate _c1 = Coordinate.of(1, 1);
  private final Coordinate _c2 = Coordinate.of(1, 2);
  private final Coordinate _c3 = Coordinate.of(1, 3);
  private final Set<Coordinate> _coords = Sets.newHashSet(_c1, _c2, _c3);

  private final int _maximum = 6;
  private final Constraint _constraint = new AdditionConstraint(_coords, 14, _maximum);
  private final Answer _answer = new Answer(_coords, _maximum);

  @Test
  public void testIsSatisfiedBy() {
    assertEquals(true, isSatisfied());
    _answer.setSolvedValue(_c1, 6);
    assertEquals(true, isSatisfied());
    _answer.setSolvedValue(_c2, 5);
    assertEquals(true, isSatisfied());
    _answer.setSolvedValue(_c3, 3);
    assertEquals(true, isSatisfied());

    _answer.setSolvedValue(_c3, 4);
    assertEquals(false, isSatisfied());
  }

  @Test
  public void testApplyConstraintRemoveHighs() {
    final Constraint constraint = new AdditionConstraint(_coords, 5, _maximum);
    assertEquals(true, possiblesSet(constraint).allMatch(p -> p.equals(range(1, _maximum))));
    constraint.applyConstraint(_answer);
    assertEquals(true, possiblesSet(constraint).allMatch(p -> p.equals(range(1, 3))));
  }

  @Test
  public void testApplyConstraintRemoveLows() {
    final Constraint constraint = new AdditionConstraint(_coords, 15, _maximum);
    assertEquals(true, possiblesSet(constraint).allMatch(p -> p.equals(range(1, _maximum))));
    constraint.applyConstraint(_answer);
    assertEquals(true, possiblesSet(constraint).allMatch(p -> p.equals(range(3, 6))));
  }

  @Test
  public void testSetSomeValues() {
    final Constraint constraint = new AdditionConstraint(_coords, 15, _maximum);
    _answer.setSolvedValue(_c1, 6);
    _answer.setSolvedValue(_c2, 4);
    constraint.applyConstraint(_answer);
  }

  private Set<Integer> range(final int from, final int to) {
    return IntStream.rangeClosed(from, to).boxed().collect(Collectors.toSet());
  }

  private Stream<Set<Integer>> possiblesSet(final Constraint constraint) {
    return constraint.cells(_answer).map(Cell::getPossibles);
  }

  private boolean isSatisfied() {
    return _constraint.isSatisfiedBy(_answer);
  }

}
