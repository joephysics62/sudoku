package joephysics62.co.uk.sudoku.write;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import joephysics62.co.uk.old.grid.maths.FourColourSolver;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.KillerSudokuHtmlReader;
import joephysics62.co.uk.old.sudoku.write.KillerSudokuHtmlWriter;
import joephysics62.co.uk.old.sudoku.write.PuzzleHtmlWriter;
import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import freemarker.template.TemplateException;

public class TestKillerSudokuHtmlWriter {

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testRoundTrip() throws Exception {
    runRoundTrip("examples/killer/times3942.html", "times3942-roundTripped.html");
  }

  private void runRoundTrip(final String input, final String control) throws IOException, URISyntaxException, TemplateException {
    KillerSudokuHtmlReader reader = new KillerSudokuHtmlReader(PuzzleLayout.CLASSIC_SUDOKU);
    Puzzle puzzle = reader.read(new File(input));
    PuzzleHtmlWriter htmlKillerSudokuWriter = new KillerSudokuHtmlWriter(puzzle, new FourColourSolver());
    File actual = tempFolder.newFile();
    htmlKillerSudokuWriter.write(actual);
    Assert.assertEquals(
        new String(Files.readAllBytes(Paths.get(getClass().getResource(control).toURI())), Charset.forName("UTF-8")),
        new String(Files.readAllBytes(actual.toPath()), Charset.forName("UTF-8"))
    );
  }

}
