package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class FutoshikiBuilder extends ArrayPuzzleBuilder {

  public FutoshikiBuilder(final PuzzleLayout layout) {
    super(layout);
    if (layout.hasSubtables()) {
      throw new IllegalArgumentException("Futoshikis do not have subtables");
    }
  }

  @Override
  public Puzzle build() {
    final List<Constraint> allConstraints = new ArrayList<>();
    allConstraints.addAll(getConstraints());
    allConstraints.addAll(createRowUniquenessConstraints(getLayout()));
    allConstraints.addAll(createColumnUniquenessConstraints(getLayout()));
    return newArrayPuzzle(allConstraints);
  }

}
