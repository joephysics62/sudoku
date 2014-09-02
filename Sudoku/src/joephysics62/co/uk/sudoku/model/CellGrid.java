package joephysics62.co.uk.sudoku.model;

public interface CellGrid<T extends Comparable<T>> {

  Cell<T> getCell(Coord coord);

}