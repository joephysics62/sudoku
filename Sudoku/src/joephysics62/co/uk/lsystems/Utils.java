package joephysics62.co.uk.lsystems;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

  public static List<Character> toChars(final String string) {
    return string.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
  }

  public static String toString(final List<Character> chars) {
    return chars.stream().map(c -> String.valueOf(c)).collect(Collectors.joining());
  }

}
