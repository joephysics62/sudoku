package joephysics62.co.uk.sudokuNew;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class KaromasuPlotter {

  public static void main(final String[] args) throws IOException {
    final GridDrawer gridDrawer = new GridDrawer(11, 11, 800, 0.05, Color.BLACK);
    ImageIO.write(gridDrawer.draw(), "png", new File("test.png"));
  }
}
