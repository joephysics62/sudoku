package joephysics62.co.uk.sudoku.model;

import java.util.Collection;
import java.util.Set;

public interface Puzzle<T> {

  Set<String> getGroups(String cellId);

  Set<Cell<T>> getAllCells();

  Collection<Restriction<T>> getAllRestrictions();

  Set<Restriction<T>> getRestrictions(String cellId);

  boolean isSolved();

}