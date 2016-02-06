package joephysics62.co.uk.kenken.constraint;

import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Coordinate;

public interface Constraint {
  Set<Coordinate> getCoords();

  int numElements();

  void applyConstraint(PuzzleAnswer answer);

  boolean isSatisfiedBy(PuzzleAnswer answer);
}
