package joephysics62.co.uk.cellblocks;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.puzzle.Puzzle2D;
import joephysics62.co.uk.puzzle.impl.Puzzle2DImpl;
import joephysics62.co.uk.puzzle.impl.Puzzle2DReaderImpl;
import joephysics62.co.uk.xml.SvgBuilder;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class CellBlocks extends Puzzle2DImpl<BiMap<CellBlockHead, Coordinate>> {

  private final BiMap<CellBlockHead, Coordinate> _clues;
  private final BiMap<CellBlockHead, Coordinate> _solution;

  public CellBlocks(final Set<Coordinate> grid, final BiMap<CellBlockHead, Coordinate> clues, final BiMap<CellBlockHead, Coordinate> solution) {
    super(max(grid, Coordinate::getRow), max(grid, Coordinate::getCol));
    _clues = clues;
    _solution = solution;
  }

  private static int max(final Set<Coordinate> grid, final Function<Coordinate, Integer> func) {
    return grid.stream().map(func).max(Integer::compare).get();
  }

  public static class Reader extends Puzzle2DReaderImpl<CellBlocks> {

    @Override
    public CellBlocks read(final Path file) throws Exception {
      final Set<Coordinate> grid = new LinkedHashSet<>();
      final BiMap<CellBlockHead, Coordinate> map = HashBiMap.create();
      read(file, (cell, coord) -> {
        grid.add(coord);
        if (!cell.isEmpty()) {
          map.put(new CellBlockHead(Integer.valueOf(cell)), coord);
        }
      });
      return new CellBlocks(grid, map, map);
    }

  }

  @Override
  protected String clueAt(final Coordinate coord) {
    return Optional.ofNullable(_clues.inverse().get(coord))
        .map(CellBlockHead::getSize)
        .map(Object::toString)
        .orElse("  ");
  }

  @Override
  protected String answerAt(final Coordinate coord) {
    return Optional.ofNullable(_clues.inverse().get(coord))
                   .map(CellBlockHead::getSize)
                   .map(i -> (" " + i))
                   .orElse("  ");
  }

  @Override
  protected void renderAnswer(final SvgBuilder builder, final Coordinate coord, final int cellSize) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void renderPuzzle(final SvgBuilder builder, final Coordinate coord, final int cellSize) {
    // TODO Auto-generated method stub

  }

  @Override
  protected Path cssTemplate() {
    return Paths.get("templates", "cell-blocks.css");
  }

  @Override
  protected List<BiMap<CellBlockHead, Coordinate>> solveForSolutionList() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Puzzle2D puzzleForSolution(final BiMap<CellBlockHead, Coordinate> soln) {
    // TODO Auto-generated method stub
    return null;
  }


}
