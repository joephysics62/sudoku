package joephysics62.co.uk.plotting;

import java.awt.Color;

public interface ColorProvider<T> {

  Color getColouring(T value);

}
