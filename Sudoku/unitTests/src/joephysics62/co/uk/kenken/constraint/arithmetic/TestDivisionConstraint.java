package joephysics62.co.uk.kenken.constraint.arithmetic;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.constraint.Constraint;
import joephysics62.co.uk.kenken.constraint.arithmetic.AdditionConstraint;
import joephysics62.co.uk.kenken.grid.Coordinate;

import org.junit.Test;

import com.google.common.collect.Sets;

public class TestDivisionConstraint {

  private final Coordinate _c1 = Coordinate.of(1, 1);
  private final Coordinate _c2 = Coordinate.of(1, 2);
  private final Coordinate _c3 = Coordinate.of(1, 3);
  private final Set<Coordinate> _coords = Sets.newHashSet(_c1, _c2, _c3);

  private final int _maximum = 6;
  private final Constraint _constraint = new AdditionConstraint(_coords, 23, _maximum);
  private final PuzzleAnswer _answer = new PuzzleAnswer(_coords, _maximum);

  @Test
  public void testIsSatisfiedBy() {
    assertEquals(true, isSatisfied());
    _answer.setSolvedValue(_c1, 6);
    assertEquals(true, isSatisfied());
    _answer.setSolvedValue(_c2, 9);
    assertEquals(true, isSatisfied());
    _answer.setSolvedValue(_c3, 8);
    assertEquals(true, isSatisfied());

    _answer.setSolvedValue(_c3, 7);
    assertEquals(false, isSatisfied());
  }

  private boolean isSatisfied() {
    return _constraint.isSatisfiedBy(_answer);
  }

}
