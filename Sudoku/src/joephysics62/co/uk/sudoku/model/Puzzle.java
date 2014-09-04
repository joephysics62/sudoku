package joephysics62.co.uk.sudoku.model;

import java.util.Collection;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Restriction;

public interface Puzzle<T extends Comparable<T>> extends CellGrid<T> {

  Collection<Restriction<T>> getAllRestrictions();

  Set<Restriction<T>> getRestrictions(Coord coord);

  boolean isSolved();

  boolean isUnsolveable();

  int completeness();

  Puzzle<T> deepCopy();

  Collection<Cell<T>> getAllCells();

}