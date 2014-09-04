package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;

public interface PuzzleBuilder<T extends Comparable<T>> {

  Puzzle<T> read(File input) throws IOException;

}
