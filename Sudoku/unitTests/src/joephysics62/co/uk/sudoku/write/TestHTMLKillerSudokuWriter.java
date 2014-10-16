package joephysics62.co.uk.sudoku.write;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import joephysics62.co.uk.sudoku.gridmaths.FourColourSolver;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.KillerSudokuReader;
import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import freemarker.template.TemplateException;

public class TestHTMLKillerSudokuWriter {

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testRoundTrip() throws Exception {
    runRoundTrip("examples/killer/times3942.html", "times3942-roundTripped.html");
  }

  private void runRoundTrip(final String input, final String control) throws IOException, URISyntaxException, TemplateException {
    KillerSudokuReader reader = new KillerSudokuReader(Layout.CLASSIC_SUDOKU);
    Puzzle puzzle = reader.read(new File(input));
    HTMLWriter htmlKillerSudokuWriter = new HTMLKillerSudokuWriter(puzzle, new FourColourSolver());
    File actual = tempFolder.newFile();
    htmlKillerSudokuWriter.write(actual);
    Assert.assertEquals(
        new String(Files.readAllBytes(Paths.get(getClass().getResource(control).toURI())), Charset.forName("UTF-8")),
        new String(Files.readAllBytes(actual.toPath()), Charset.forName("UTF-8"))
    );
  }

}
