package joephysics62.co.uk.old.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.constraints.UniqueSum;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser.InputCell;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class KillerSudokuHtmlReader implements PuzzleReader {

  private final PuzzleLayout _layout;

  private static final Logger LOG = Logger.getLogger(KillerSudokuHtmlReader.class);

  public KillerSudokuHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    final HTMLTableParser tableParser = new HTMLTableParser(_layout);
    final ArrayBuilder killerBuilder = new ArrayBuilder(_layout);

    final Map<String, Integer> sumByGroup = new LinkedHashMap<>();
    final Multimap<String, Coord> cellsByGroup = ArrayListMultimap.create();
    final Grid<InputCell> table = tableParser.parseTable(input);
    for (final Coord coord : table) {
      final InputCell inputCell = table.get(coord);
      handleInputCell(killerBuilder, sumByGroup, cellsByGroup, coord, inputCell);
    }
    if (!sumByGroup.keySet().equals(cellsByGroup.keySet())) {
      throw new RuntimeException("Sum keyset = " + sumByGroup.keySet() + " but group keyset = " + cellsByGroup.keySet());
    }
    final List<UniqueSum> uniqueSumConstraints = buildUniqueSumConstraints(sumByGroup, cellsByGroup);
    for (final Constraint constraint : uniqueSumConstraints) {
      killerBuilder.addConstraint(constraint);
    }
    final List<Constraint> geometricConstraints = new ArrayList<>();
    geometricConstraints.addAll(killerBuilder.addColumnUniquenessConstraints());
    geometricConstraints.addAll(killerBuilder.addRowUniquenessConstraints());
    geometricConstraints.addAll(killerBuilder.addSubTableUniquenessConstraints());

    final List<Constraint> implicitConstraints = createImplicitConstraints(uniqueSumConstraints, geometricConstraints);
    for (final Constraint constraint : implicitConstraints) {
      killerBuilder.addConstraint(constraint);
    }
    return killerBuilder.build();
  }

  private void handleInputCell(final ArrayBuilder killerBuilder, final Map<String, Integer> sumByGroup, final Multimap<String, Coord> cellsByGroup, final Coord coord, final InputCell inputCell) {
    final Set<String> classes = inputCell.getClasses();
    final Map<String, String> complexCellInput = inputCell.getComplexValue();
    if (!complexCellInput.isEmpty()) {
      // TODO: this is just to initialise the givens. Shouldn't need to do this!
      killerBuilder.addGiven(null, coord);

      final String group = readGroupFromClasses(classes, cellsByGroup, coord);
      if (complexCellInput.size() != 1) {
        throw new RuntimeException("Expect at most one div value in killer sudoku input.");
      }
      final int sumValue = Integer.valueOf(complexCellInput.entrySet().iterator().next().getValue());
      if (null != sumByGroup.put(group, sumValue)) {
        throw new RuntimeException("Bad input, more than sum for the same group class");
      }
    }
    else {
      // TODO: this is just to initialise the givens. Shouldn't need to do this!
      killerBuilder.addGiven(null, coord);
      readGroupFromClasses(classes, cellsByGroup, coord);
    }
  }

  private String readGroupFromClasses(final Set<String> classes, final Multimap<String, Coord> cellsByGroup, final Coord coord) {
    if (classes.size() != 1) {
      throw new RuntimeException("Bad input, more than more class on killer sudoku cell.");
    }
    final String group = classes.iterator().next();
    cellsByGroup.put(group, coord);
    return group;
  }

  private List<Constraint> createImplicitConstraints(final List<UniqueSum> uniqueSumConstraints, final List<Constraint> geometricConstraints) {
    final List<Constraint> out = new ArrayList<>();
    final int i = _layout.getInitialsSize();
    final int geometricConstraintSum = (i * (i + 1)) / 2;

    for (final Constraint geometricConstraint : geometricConstraints) {
      int derivedSum = geometricConstraintSum;
      final List<Coord> cells = new ArrayList<>(geometricConstraint.getCells());

      boolean addDerived = false;
      for (final UniqueSum uniqueSum : uniqueSumConstraints) {
        if (cells.containsAll(uniqueSum.getCells())) {
          derivedSum -= uniqueSum.getSum();
          cells.removeAll(uniqueSum.getCells());
          addDerived = true;
        }
      }
      if (addDerived) {
        final UniqueSum derivedConstraint = UniqueSum.of(derivedSum, _layout.getInitialsSize(), cells);
        LOG.debug("Adding derived constraint " + derivedConstraint);
        out.add(derivedConstraint);
      }
    }
    return out;
  }

  private List<UniqueSum> buildUniqueSumConstraints(final Map<String, Integer> sumByGroup, final Multimap<String, Coord> cellsByGroup) {
    final List<UniqueSum> uniqueSumConstraints = new ArrayList<>();
    for (final Entry<String, Integer> entry : sumByGroup.entrySet()) {
      final String groupId = entry.getKey();
      final UniqueSum uniqueSum = UniqueSum.of(entry.getValue(), _layout.getInitialsSize(), cellsByGroup.get(groupId));
      LOG.debug("Add unique sum constraint " + uniqueSum);
      uniqueSumConstraints.add(uniqueSum);
    }
    return uniqueSumConstraints;
  }

}
