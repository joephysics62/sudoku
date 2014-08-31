package joephysics62.co.uk.sudoku.model;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

public interface Puzzle<T> {

  Set<String> getGroups(Coord cellId);

  Set<Cell<T>> getAllCells();

  Collection<Restriction<T>> getAllRestrictions();

  Set<Restriction<T>> getRestrictions(Coord cellId);

  boolean isSolved();

  void write(PrintStream out);

}