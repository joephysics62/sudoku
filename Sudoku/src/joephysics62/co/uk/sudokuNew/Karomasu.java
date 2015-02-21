package joephysics62.co.uk.sudokuNew;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Karomasu extends Puzzle<RectangularCoord, Boolean> {

  public Karomasu(final Map<RectangularCoord, Integer> clues) {
    super(
        clues.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Boolean.TRUE)),
        RectangularCoord.coordGenerators(11, 11)
    );
  }

  public static void main(final String[] args) {
    RectangularCoord.coordGenerators(11, 11).forEach(System.out::println);
  }

  @Override
  protected Cell<Boolean> newDefaultCell() {
    return new KaromasuCell();
  }

  @Override
  protected Cell<Boolean> newCell(final Boolean given) {
    return new KaromasuCell(given);
  }

  @Override
  protected Set<Restriction<RectangularCoord, Boolean>> fixedRestrictions() {
    return Collections.emptySet();
  }
}
