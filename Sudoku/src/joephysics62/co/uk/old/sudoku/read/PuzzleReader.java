package joephysics62.co.uk.old.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.old.sudoku.model.Puzzle;

public interface PuzzleReader {

  Puzzle read(File input) throws IOException;

}
