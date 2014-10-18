package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.constraints.UniqueSum;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

import org.apache.log4j.Logger;

public class KillerSudokuHtmlReader implements PuzzleHtmlReader {

  private final PuzzleLayout _layout;

  private static final Logger LOG = Logger.getLogger(KillerSudokuHtmlReader.class);

  public KillerSudokuHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(File input) throws IOException {
    HTMLTableParser tableParser = new HTMLTableParser(_layout);
    final ArrayBuilder killerBuilder = new ArrayBuilder(_layout);

    final Map<String, Integer> sumByGroup = new LinkedHashMap<>();
    final Map<String, List<Coord>> cellsByGroup = new LinkedHashMap<>();
    tableParser.parseTable(input, new TableParserHandler() {

      @Override public void title(String title) { killerBuilder.addTitle(title); }

      @Override
      public void cell(Map<String, String> complexCellInput, Set<String> classes, int rowIndex, int colIndex) {
        // TODO: this is just to initialise the givens. Shouldn't need to do this!
        killerBuilder.addGiven(null, Coord.of(rowIndex + 1, colIndex + 1));

        final String group = readGroupFromClasses(classes, rowIndex, colIndex);
        if (complexCellInput.size() != 1) {
          throw new RuntimeException("Expect at most one div value in killer sudoku input.");
        }
        final int sumValue = Integer.valueOf(complexCellInput.entrySet().iterator().next().getValue());
        if (null != sumByGroup.put(group, sumValue)) {
          throw new RuntimeException("Bad input, more than sum for the same group class");
        }
      }

      @Override
      public void cell(String cellInput, Set<String> classes, int rowIndex, int colIndex) {
        // TODO: this is just to initialise the givens. Shouldn't need to do this!
        killerBuilder.addGiven(null, Coord.of(rowIndex + 1, colIndex + 1));

        readGroupFromClasses(classes, rowIndex, colIndex);
      }

      private String readGroupFromClasses(Set<String> classes, int rowIndex, int colIndex) {
        if (classes.size() != 1) {
          throw new RuntimeException("Bad input, more than more class on killer sudoku cell.");
        }
        final String group = classes.iterator().next();
        if (!cellsByGroup.containsKey(group)) {
          cellsByGroup.put(group, new ArrayList<Coord>());
        }
        cellsByGroup.get(group).add(Coord.of(rowIndex + 1, colIndex + 1));
        return group;
      }
    });
    if (!sumByGroup.keySet().equals(cellsByGroup.keySet())) {
      throw new RuntimeException("Sum keyset = " + sumByGroup.keySet() + " but group keyset = " + cellsByGroup.keySet());
    }
    final List<UniqueSum> uniqueSumConstraints = buildUniqueSumConstraints(sumByGroup, cellsByGroup);
    for (Constraint constraint : uniqueSumConstraints) {
      killerBuilder.addConstraint(constraint);
    }
    final List<Constraint> geometricConstraints = new ArrayList<>();
    geometricConstraints.addAll(killerBuilder.addColumnUniquenessConstraints());
    geometricConstraints.addAll(killerBuilder.addRowUniquenessConstraints());
    geometricConstraints.addAll(killerBuilder.addSubTableUniquenessConstraints());

    final List<Constraint> implicitConstraints = createImplicitConstraints(uniqueSumConstraints, geometricConstraints);
    for (Constraint constraint : implicitConstraints) {
      killerBuilder.addConstraint(constraint);
    }
    return killerBuilder.build();
  }

  private List<Constraint> createImplicitConstraints(final List<UniqueSum> uniqueSumConstraints, final List<Constraint> geometricConstraints) {
    final List<Constraint> out = new ArrayList<>();
    final int i = _layout.getInitialsSize();
    final int geometricConstraintSum = (i * (i + 1)) / 2;

    for (Constraint geometricConstraint : geometricConstraints) {
      int derivedSum = geometricConstraintSum;
      List<Coord> cells = new ArrayList<>(geometricConstraint.getCells());

      boolean addDerived = false;
      for (UniqueSum uniqueSum : uniqueSumConstraints) {
        if (cells.containsAll(uniqueSum.getCells())) {
          derivedSum -= uniqueSum.getSum();
          cells.removeAll(uniqueSum.getCells());
          addDerived = true;
        }
      }
      if (addDerived) {
        UniqueSum derivedConstraint = UniqueSum.of(derivedSum, _layout.getInitialsSize(), cells);
        LOG.debug("Adding derived constraint " + derivedConstraint);
        out.add(derivedConstraint);
      }
    }
    return out;
  }

  private List<UniqueSum> buildUniqueSumConstraints(final Map<String, Integer> sumByGroup, final Map<String, List<Coord>> cellsByGroup) {
    final List<UniqueSum> uniqueSumConstraints = new ArrayList<>();
    for (Entry<String, Integer> entry : sumByGroup.entrySet()) {
      String groupId = entry.getKey();
      UniqueSum uniqueSum = UniqueSum.of(entry.getValue(), _layout.getInitialsSize(), cellsByGroup.get(groupId));
      LOG.debug("Add unique sum constraint " + uniqueSum);
      uniqueSumConstraints.add(uniqueSum);
    }
    return uniqueSumConstraints;
  }

}
