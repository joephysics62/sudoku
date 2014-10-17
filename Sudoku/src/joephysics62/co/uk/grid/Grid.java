package joephysics62.co.uk.grid;



public interface Grid<T> {
  T get(Coord coord);

  void set(T value, Coord coord);
}
