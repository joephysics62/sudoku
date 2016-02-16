package joephysics62.co.uk.kenken.constraint;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.Answer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public interface Constraint {
  Set<Coordinate> getCoords();

  Stream<Coordinate> applyConstraint(Answer answer);

  boolean isSatisfiedBy(Answer answer);

  Stream<Coordinate> coords();

  Stream<Cell> cells(Answer answer);

  void applyToCells(Answer answer, Consumer<? super Cell> action);
}
