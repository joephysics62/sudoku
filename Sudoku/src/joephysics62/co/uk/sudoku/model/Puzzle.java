package joephysics62.co.uk.sudoku.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

public interface Puzzle<T> {

  Set<Cell<T>> getAllCells();

  Collection<Restriction<T>> getAllRestrictions();

  Set<Restriction<T>> getRestrictions(Cell<T> cell);

  boolean isSolved();

  void write(PrintStream out);

  int completeness();

  void loadValues(File input) throws IOException;

}