package joephysics62.co.uk.grid.map;

import java.util.LinkedHashMap;
import java.util.Map;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.FullGridIterable;
import joephysics62.co.uk.grid.GridLayout;

public class MapGrid<T> extends FullGridIterable<T> {

  private final Map<Coord, T> _map = new LinkedHashMap<>();

  public MapGrid(final GridLayout layout) {
    super(layout);
  }

  @Override
  public T get(Coord coord) {
    return _map.get(coord);
  }

  @Override
  public void set(T value, Coord coord) {
    _map.put(coord, value);
  }

}
