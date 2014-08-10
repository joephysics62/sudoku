package joephysics62.co.uk.sudoku.model;

public interface Restriction<T> {
  boolean satisfiedBy(CellGroup<T> group);
  void eliminateValues(CellGroup<T> group);
}
