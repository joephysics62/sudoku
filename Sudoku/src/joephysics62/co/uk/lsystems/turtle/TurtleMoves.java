package joephysics62.co.uk.lsystems.turtle;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TurtleMoves implements Iterable<Turtle> {

  private final Map<Character, Turtle> _moveMap = new LinkedHashMap<>();

  public TurtleMoves(final Turtle... turtles) {
    for (final Turtle turtle : turtles) {
      _moveMap.put(turtle.id(), turtle);
    }
  }

  public Turtle byId(final char id) {
    return _moveMap.get(id);
  }

  @Override
  public Iterator<Turtle> iterator() {
    return _moveMap.values().iterator();
  }

}
