package joephysics62.co.uk.kenken.constraint.arithmetic;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.constraint.Constraint;
import joephysics62.co.uk.kenken.grid.Coordinate;

import org.junit.Test;

import com.google.common.collect.Sets;

public class TestDivisionConstraint {

  private final Coordinate _left = Coordinate.of(1, 1);
  private final Coordinate _right = Coordinate.of(1, 2);
  private final int _maximum = 6;
  private final Set<Coordinate> _coords = Sets.newHashSet(_left, _right);
  private final Constraint _constraint = new DivisionConstraint(_coords, 2, _maximum);
  private final PuzzleAnswer _answer = new PuzzleAnswer(_coords, _maximum);

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
