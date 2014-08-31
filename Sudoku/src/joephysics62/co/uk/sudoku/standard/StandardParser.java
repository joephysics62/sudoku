package joephysics62.co.uk.sudoku.standard;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Puzzle;

public class StandardParser {

  public static int PUZZLE_SIZE = 9;

  public Puzzle<Integer> parse(final File csv) throws IOException {
    List<List<Integer>> inputLists = new ArrayList<>();
    for (final String line : Files.readAllLines(csv.toPath(), Charset.forName("UTF-8"))) {
      String[] split = line.split("\\|");
      if (split.length != PUZZLE_SIZE + 1) {
        throw new RuntimeException("Bad input: has " + split.length + " |'s on a line: " + line);
      }
      final List<Integer> rowList = new ArrayList<>();
      for (int i = 1; i < split.length; i++) {
       String cellInput = split[i].trim() ;
       Integer input;
       if (cellInput.isEmpty()) {
         input = null;
       }
       else {
         input = Integer.valueOf(cellInput);
       }
       rowList.add(input);
      }
      inputLists.add(rowList);
    }
    return StandardPuzzle.fromTableValues(inputLists);
  }

}
