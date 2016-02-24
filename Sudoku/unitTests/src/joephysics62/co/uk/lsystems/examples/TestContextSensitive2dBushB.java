package joephysics62.co.uk.lsystems.examples;

import java.util.stream.Collectors;

import joephysics62.co.uk.old.lsystems.LSystem;
import joephysics62.co.uk.old.lsystems.examples.ContextSensitive2dBushB;

import org.junit.Assert;
import org.junit.Test;

public class TestContextSensitive2dBushB {

  @Test
  public void test() {
    runTest(0, "F1F1F1");
    runTest(1, "F1F0F1");
    runTest(2, "F1F1F1F1");
    runTest(3, "F1F0F0F1");
    runTest(4, "F1F0F1[-F1F1]F1");
  }

  private void runTest(final int iterations, final String expected) {
    final LSystem lsystem = new ContextSensitive2dBushB();
    final String actual = lsystem.generate(iterations).stream().map(c -> String.valueOf(c.getId())).collect(Collectors.joining());
    Assert.assertEquals(expected, actual);
  }

}
