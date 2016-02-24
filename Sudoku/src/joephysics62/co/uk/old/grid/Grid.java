package joephysics62.co.uk.old.grid;

public interface Grid<T> extends Iterable<Coord> {
  T get(Coord coord);

  void set(T value, Coord coord);

  GridLayout getLayout();
}
