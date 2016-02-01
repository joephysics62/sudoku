package joephysics62.co.uk.kenken;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cell {

  private final Set<Integer> _possibles;

  private Cell(final Set<Integer> possibles) {
    _possibles = possibles;
  }

  public static Cell unsolvedCell(final int maximum) {
    return new Cell(IntStream.rangeClosed(1, maximum).boxed().collect(Collectors.<Integer>toSet()));
  }

  public static Cell givenCell(final int given) {
    return new Cell(Collections.singleton(given));
  }


  public boolean remove(final Integer value) {
    return _possibles.remove(value);
  }

  public int numberOfPossibles() {
    return _possibles.size();
  }

  public boolean isSolved() {
    return numberOfPossibles() == 1;
  }

  public boolean isUnsolved() {
    return !isSolved();
  }

  public boolean isInconsistent() {
    return _possibles.isEmpty();
  }

  @Override
  public String toString() {
    return "Cell(possibles=" + _possibles + ")";
  }

}
