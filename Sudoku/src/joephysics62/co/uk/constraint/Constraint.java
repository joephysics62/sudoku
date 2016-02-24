package joephysics62.co.uk.constraint;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.kenken.Answer;

public interface Constraint {
  Set<Coordinate> getCoords();

  Stream<Coordinate> applyConstraint(Answer answer);

  boolean isSatisfiedBy(Answer answer);

  Stream<Coordinate> coords();

  Stream<Cell> cells(Answer answer);

  void applyToCells(Answer answer, Consumer<? super Cell> action);
}
