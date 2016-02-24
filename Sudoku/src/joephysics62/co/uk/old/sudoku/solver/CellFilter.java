package joephysics62.co.uk.old.sudoku.solver;

import java.util.List;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Puzzle;

public interface CellFilter {

  List<Coord> apply(Puzzle puzzle);

}