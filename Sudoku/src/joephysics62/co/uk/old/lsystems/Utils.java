package joephysics62.co.uk.old.lsystems;

import java.util.List;
import java.util.stream.Collectors;

import joephysics62.co.uk.old.lsystems.turtle.IModule;

public class Utils {

  public static List<Character> toChars(final String string) {
    return string.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
  }

  public static String toString(final List<IModule> chars) {
    return chars.stream().map(c -> c.getId()).map(c -> String.valueOf(c)).collect(Collectors.joining());
  }

}
