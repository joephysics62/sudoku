package joephysics62.co.uk.old.constraints;

import java.util.List;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;

public interface Constraint {
  boolean eliminateValues(Grid<Integer> cellGrid);
  boolean forKnownValue(Grid<Integer> cellGrid, Coord coord);
  List<Coord> getCells();
  boolean isSatisfied(Grid<Integer> cellGrid);
}
