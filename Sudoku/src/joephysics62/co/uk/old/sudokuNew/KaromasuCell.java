package joephysics62.co.uk.old.sudokuNew;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;


public class KaromasuCell extends Cell<Boolean> {
  public KaromasuCell() {
    super(new LinkedHashSet<Boolean>(Arrays.asList(Boolean.TRUE, Boolean.FALSE)));
  }
  public KaromasuCell(final Boolean given) {
    super(Collections.singleton(given));
  }

}
