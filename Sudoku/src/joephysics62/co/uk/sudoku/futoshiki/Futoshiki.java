package joephysics62.co.uk.sudoku.futoshiki;

import java.util.Set;

import joephysics62.co.uk.sudoku.model.MapBackedPuzzle;

public class Futoshiki<T extends Comparable<T>> extends MapBackedPuzzle<T> {

  public Futoshiki(Set<T> inits) {
    super(inits);
  }

}
