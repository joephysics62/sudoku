package joephysics62.co.uk.sudoku.solver;

import java.util.List;

import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public interface CellFilter {

  List<Coord> apply(Puzzle puzzle);

}