package joephysics62.co.uk.cellblocks;

import java.nio.file.Path;
import java.nio.file.Paths;

import joephysics62.co.uk.cellblocks.CellBlocks.Reader;

public class Main {

  private static final Path EXAMPLE = Paths.get("examples", "cellBlocks", "times-2504.txt");

  public static void main(final String[] args) throws Exception {
    final Reader reader = new CellBlocks.Reader();
    final CellBlocks blocks = reader.read(EXAMPLE);
    blocks.writePuzzle(System.out);
  }
}
