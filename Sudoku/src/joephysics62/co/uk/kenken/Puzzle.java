package joephysics62.co.uk.kenken;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.constraint.Constraint;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Puzzle {

  private final Multimap<Coordinate, Constraint> _coordsToConstraints = HashMultimap.create();
  private final int _height;
  private final Set<Constraint> _constraints;

  public Puzzle(final int height, final Stream<Constraint> constraints) {
    _height = height;
    constraints.forEach(c -> c.getCoords().forEach(co -> _coordsToConstraints.put(co, c)));
    _constraints = new LinkedHashSet<>(_coordsToConstraints.values());
  }

  public List<Answer> solvedUnique() {
    final Answer answer = new Answer(_coordsToConstraints.keySet(), _height);

    final List<Answer> solutions = new ArrayList<Answer>();
    recursiveSolved(answer, solutions);
    return solutions;
  }

  private void recursiveSolved(final Answer answer, final List<Answer> solutions) {
    if (answer.isInconsistent()) {
      return;
    }
    if (answer.isSolved()) {
      final boolean allSatisfied = _constraints.stream().allMatch(c -> c.isSatisfiedBy(answer));
      if (allSatisfied) {
        solutions.add(answer);
      }
      return;
    }
    final Set<Coordinate> changedCells = applyAllConstraints(answer);
    if (!changedCells.isEmpty()) {
      recursiveSolved(answer, solutions);
      return;
    }
    final Optional<Coordinate> opt = answer.bestUnsolved();
    if (!opt.isPresent()) {
      recursiveSolved(answer, solutions);
      return;
    }
    final Coordinate bestunsolved = opt.get();
    final Cell bestUnsolvedCell = answer.cellAt(bestunsolved);

    for (final Integer candidate : bestUnsolvedCell.getPossibles()) {
      final Answer clonedAnswer = answer.clone();
      clonedAnswer.setSolvedValue(bestunsolved, candidate);
      recursiveSolved(clonedAnswer, solutions);
    }
  }

  private void checkForSolved(final Answer answer) {
    _coordsToConstraints.keySet()
      .stream()
      .filter(co -> answer.cellAt(co).isSolved())
      .forEach(co -> answer.setSolvedValue(co, answer.cellAt(co).getSolvedValue()));
  }

  private Set<Coordinate> applyAllConstraints(final Answer answer) {
    Stream<Coordinate> coords = Stream.empty();
    for (final Constraint constraint : _constraints) {
      coords = Stream.concat(coords, constraint.applyConstraint(answer));
    }
    checkForSolved(answer);
    return coords.collect(Collectors.toSet());
  }


}
