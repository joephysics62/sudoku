package joephysics62.co.uk.sudoku.model;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

public interface Puzzle<T extends Comparable<T>> extends CellGrid<T> {

  Collection<Restriction<T>> getAllRestrictions();

  Set<Restriction<T>> getRestrictions(Coord coord);

  boolean isSolved();

  boolean isUnsolveable();

  void write(PrintStream out);

  int completeness();

  Puzzle<T> deepCopy();

  Set<Cell<T>> getAllCells();

}