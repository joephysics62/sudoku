package joephysics62.co.uk.sudoku.solver;

import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public interface CellPickingStrategy {

  Coord cellToGuess(Puzzle puzzle);

}