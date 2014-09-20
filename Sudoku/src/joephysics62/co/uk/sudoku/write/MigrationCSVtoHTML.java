package joephysics62.co.uk.sudoku.write;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.FutoshikiHTMLReader;
import freemarker.template.TemplateException;

public class MigrationCSVtoHTML {

  public static void main(String[] args) throws IOException, TemplateException {
    String dir = "examples/sudoku/super";
    File readDir = new File(dir);
    for (File csv : readDir.listFiles()) {
      FutoshikiHTMLReader reader = new FutoshikiHTMLReader(5);
      Puzzle puzzle = reader.read(csv);
      HTMLPuzzleWriter writer = new HTMLPuzzleWriter(puzzle);
      String basename = csv.getName().substring(0, csv.getName().lastIndexOf('.'));
      writer.write(new File(readDir, basename + ".html"), basename);
    }
  }

}
