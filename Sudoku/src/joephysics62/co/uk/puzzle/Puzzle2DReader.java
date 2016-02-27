package joephysics62.co.uk.puzzle;

import java.nio.file.Path;

public interface Puzzle2DReader<T extends Puzzle2D> {
  T read(Path file) throws Exception;
}
