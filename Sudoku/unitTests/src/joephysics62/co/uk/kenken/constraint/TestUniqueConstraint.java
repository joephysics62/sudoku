package joephysics62.co.uk.kenken.constraint;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Coordinate;

import org.junit.Test;

public class TestUniqueConstraint {

  @Test
  public void test() {
    final Set<Coordinate> coords = IntStream
                          .rangeClosed(1, 6)
                          .mapToObj(x -> Coordinate.of(1, x))
                          .collect(Collectors.toSet());
    final UniqueConstraint row = new UniqueConstraint(coords);
    final PuzzleAnswer answer = new PuzzleAnswer(coords, 6);
    answer.setSolvedValue(Coordinate.of(1, 1), 1);
    row.applyConstraint(answer);
    assertEquals(Collections.singleton(1), answer.possiblesAt(1, 1));
    final Set<Integer> allExludingOne = IntStream.rangeClosed(2, 6).boxed().collect(Collectors.toSet());
    for (int i = 2; i <= 6; i++) {
      assertEquals(allExludingOne, answer.possiblesAt(1, i));
    }
    answer.setSolvedValue(Coordinate.of(1, 5), 3);
    answer.setSolvedValue(Coordinate.of(1, 6), 4);
    row.applyConstraint(answer);
    final Set<Integer> unsolved = new LinkedHashSet<>(Arrays.asList(2, 5, 6));
    assertEquals(Collections.singleton(1), answer.possiblesAt(1, 1));
    assertEquals(unsolved, answer.possiblesAt(1, 2));
    assertEquals(unsolved, answer.possiblesAt(1, 3));
    assertEquals(unsolved, answer.possiblesAt(1, 4));
    assertEquals(Collections.singleton(3), answer.possiblesAt(1, 5));
    assertEquals(Collections.singleton(4), answer.possiblesAt(1, 6));



  }

}
