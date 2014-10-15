package joephysics62.co.uk.sudoku.gridmaths;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestFourColourSolver {

  @Test
  public void testSimple() {
    FourColourSolver fcs = new FourColourSolver();
    final int[][] arr = {
                         {1, 2},
                         {1, 2}
                        };
    Map<Integer, Colour> colourMap = fcs.calculateColourMap(arr);
    LinkedHashMap<Integer, Colour> expected = new LinkedHashMap<>();
    expected.put(1, Colour.A);
    expected.put(2, Colour.B);
    Assert.assertEquals(expected, colourMap);
  }

  @Test
  public void testThreeGroups() {
    FourColourSolver fcs = new FourColourSolver();
    final int[][] arr = {
                         {1, 2, 2},
                         {1, 2, 3},
                         {2, 2, 3}
                        };
    Map<Integer, Colour> colourMap = fcs.calculateColourMap(arr);
    LinkedHashMap<Integer, Colour> expected = new LinkedHashMap<>();
    expected.put(1, Colour.A);
    expected.put(2, Colour.B);
    expected.put(3, Colour.A);
    Assert.assertEquals(expected, colourMap);
  }

  @Test
  public void testMoreComplex() {
    FourColourSolver fcs = new FourColourSolver();
    final int[][] arr = {
                         {1, 2, 2, 3, 4, 4},
                         {1, 2, 5, 5, 4, 6},
                         {1, 7, 5, 5, 4, 6},
                         {7, 7, 8, 9, 9, 9},
                        {10,10,10,10, 9,11},
                        {12,12,13,13,13,13},
                        };
//    {A, B, B, A, B, B},
//    {A, B, C, C, B, A},
//    {A, D, C, C, B, A},
//    {D, D, A, D, D, D},
//    {B, B, B, B, D, A},
//    {A, A, C, C, C, C},
    Map<Integer, Colour> colourMap = fcs.calculateColourMap(arr);
    LinkedHashMap<Integer, Colour> expected = new LinkedHashMap<>();
     expected.put(1, Colour.A);
     expected.put(2, Colour.B);
     expected.put(3, Colour.A);
     expected.put(4, Colour.B);
     expected.put(5, Colour.C);
     expected.put(6, Colour.A);
     expected.put(7, Colour.D);
     expected.put(8, Colour.A);
     expected.put(9, Colour.D);
     expected.put(10, Colour.B);
     expected.put(11, Colour.A);
     expected.put(12, Colour.A);
     expected.put(13, Colour.C);
    Assert.assertEquals(expected, colourMap);
  }

}
