package com.fenton.connect4;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class GridUtils {
  public static <T> Map<Integer, Integer> horizontalLineCount(final T[][] grid, final T player) {
    final int height = grid.length;
    final int width = grid[0].length;
    return lineCount(grid, player,
                     0, height,
                     x -> 0, x -> width,
                     (x, y) -> x, (x, y) -> y);
  }

  public static <T> Map<Integer, Integer> verticalLineCount(final T[][] grid, final T player) {
    final int height = grid.length;
    final int width = grid[0].length;
    return lineCount(grid, player,
                     0, width,
                     x -> 0, x-> height,
                     (x, y) -> y, (x, y) -> x);
  }

  public static <T> Map<Integer, Integer> diagonalAscLineCount(final T[][] grid, final T player) {
    final int height = grid.length;
    final int width = grid[0].length;
    return lineCount(grid, player,
                     1 - width, height,
                     x -> Math.max(0, -x), x -> Math.min(width, height - x),
                     (x, y) -> y + x, (x, y) -> y);
  }

  public static <T> Map<Integer, Integer> diagonalDescLineCount(final T[][] grid, final T player) {
    final int height = grid.length;
    final int width = grid[0].length;
    return lineCount(grid, player,
                     0, height + width - 1,
                     x -> Math.max(0, x - height + 1), x -> Math.min(width, x + 1),
                     (x, y) -> x - y, (x, y) -> y);
  }

  private static <T> Map<Integer, Integer> lineCount(
      final T[][] grid, final T player,
      final int xStart, final int xEndExcl,
      final IntFunction<Integer> yStart, final IntFunction<Integer> yEndExcl,
      final BiFunction<Integer, Integer, Integer> rowF,
      final BiFunction<Integer, Integer, Integer> colF) {
    final Map<Integer, Integer> out = new LinkedHashMap<>();

    for (int x = xStart; x < xEndExcl; x++) {
      int lineSize = 0;
      for (int y = yStart.apply(x); y < yEndExcl.apply(x); y++) {
        final int row = rowF.apply(x, y);
        final int col = colF.apply(x, y);
        if (Objects.equals(player, grid[row][col])) {
          lineSize++;
        }
        else {
          if (lineSize > 1) {
            updateMap(out, lineSize);
          }
          lineSize = 0;
        }
      }
      if (lineSize > 1) {
        updateMap(out, lineSize);
      }
    }
    return out;
  }


  private static void updateMap(final Map<Integer, Integer> out, final int lineSize) {
    out.put(lineSize, 1 + (out.containsKey(lineSize) ? out.get(lineSize) : 0));
  }
}
