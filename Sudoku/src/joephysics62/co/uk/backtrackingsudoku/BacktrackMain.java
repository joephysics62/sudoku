package joephysics62.co.uk.backtrackingsudoku;

import static joephysics62.co.uk.grid.Coordinate.of;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.grid.Coordinate;

public class BacktrackMain {
  public static void main(final String[] args) throws IOException {
    final int[][] grid = {
                           {0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0},
                           {0, 5, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0}
                          };
    final Coordinate head_1 = of(0, 0);
    final Coordinate head_2 = of(0, 1);
    final Coordinate head_3 = of(0, 3);
    final Coordinate head_4 = of(0, 4);
    final Coordinate head_5 = of(0, 5);

    final Coordinate head_6 = of(1, 2);

    final Coordinate head_7 = of(2, 0);
    final Coordinate head_8 = of(2, 4);
    final Coordinate head_9 = of(2, 5);

    final Coordinate head_10 = of(3, 0);
    final Coordinate head_11 = of(3, 2);

    final Coordinate head_12 = of(4, 1);
    final Coordinate head_13 = of(4, 3);
    final Coordinate head_14 = of(4, 5);

    final Coordinate head_15 = of(5, 0);
    final Coordinate head_16 = of(5, 2);

    final List<ArithmeticGroup> groups = Arrays.asList(
        new ArithmeticGroup(Operator.MULTIPLY, 24, Arrays.asList(head_1, head_1.down(), head_1.down().right())),
        new ArithmeticGroup(Operator.SUBTRACT, 5,  Arrays.asList(head_2, head_2.right())),
        new ArithmeticGroup(Operator.SUBTRACT, 3,  Arrays.asList(head_3, head_3.down())),
        new ArithmeticGroup(Operator.ADD     , 8 , Arrays.asList(head_4, head_4.down())),
        new ArithmeticGroup(Operator.SUBTRACT, 1 , Arrays.asList(head_5, head_5.down())),

        new ArithmeticGroup(Operator.ADD     , 11, Arrays.asList(head_6, head_6.down(), head_6.down().right(), head_6.down().right().down())),

        new ArithmeticGroup(Operator.DIVIDE  , 3 , Arrays.asList(head_7, head_7.right())),
        new ArithmeticGroup(Operator.ADD     , 10, Arrays.asList(head_8, head_8.down())),
        new ArithmeticGroup(Operator.SUBTRACT, 1 , Arrays.asList(head_9, head_9.down())),

        new ArithmeticGroup(Operator.ADD,      8 , Arrays.asList(head_10, head_10.right(), head_10.down())),
        new ArithmeticGroup(Operator.ADD,      9 , Arrays.asList(head_11, head_11.down())),

        new ArithmeticGroup(Operator.ADD,      5 , Arrays.asList(head_12)),
        new ArithmeticGroup(Operator.DIVIDE,   3 , Arrays.asList(head_13, head_13.right())),
        new ArithmeticGroup(Operator.SUBTRACT, 3 , Arrays.asList(head_14, head_14.down())),

        new ArithmeticGroup(Operator.SUBTRACT, 1 , Arrays.asList(head_15, head_15.right())),
        new ArithmeticGroup(Operator.MULTIPLY, 6 , Arrays.asList(head_16, head_16.right(), head_16.right().right()))
        );
    //final Path inputFile = Paths.get("examples", "sudoku", "classic", "times-7999");
    //final BSudoku puzzle = BSudoku.readPuzzle(inputFile, 9, 3);
    final BKenKen puzzle = new BKenKen(grid, 6, groups);
    final long startTime = System.currentTimeMillis();
    for (final int[][] is : puzzle.solve()) {
      puzzle.printGrid(is);
    }
    System.out.println(System.currentTimeMillis() - startTime + "ms");
  }

}
