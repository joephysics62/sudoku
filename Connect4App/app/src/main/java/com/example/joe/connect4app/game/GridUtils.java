package com.example.joe.connect4app.game;

import java.util.LinkedHashMap;
import java.util.Map;

public class GridUtils {

    private static IntFunc TO_ZERO = new IntFunc() {
        @Override public int apply(int input) {
            return 0;
        }
    };
    
    private static class ToStatic implements IntFunc {
        private final int _staticVal;

        private ToStatic(int staticVal) {
            _staticVal = staticVal;
        }

        @Override public int apply(int input) {
            return _staticVal;
        }
    }

    private static BiIntFunc TO_LEFT = new BiIntFunc() {
        @Override public int apply(int left, int right) {
            return left;
        }
    };

    private static BiIntFunc TO_RIGHT = new BiIntFunc() {
        @Override public int apply(int left, int right) {
            return right;
        }
    };

    private static BiIntFunc SUM = new BiIntFunc() {
        @Override public int apply(int left, int right) {
            return left + right;
        }
    };

    private static BiIntFunc DIFF = new BiIntFunc() {
        @Override public int apply(int left, int right) {
            return left - right;
        }
    };

    public static <T> Map<Integer, Integer> horizontalLineCount(final T[][] grid, final T player) {
        final int height = grid.length;
        final int width = grid[0].length;

        return lineCount(grid, player,
                0, height,
                TO_ZERO, new ToStatic(width),
                TO_LEFT, TO_RIGHT);
    }

    public static <T> Map<Integer, Integer> verticalLineCount(final T[][] grid, final T player) {
        final int height = grid.length;
        final int width = grid[0].length;
        return lineCount(grid, player,
                0, width,
                TO_ZERO, new ToStatic(height),
                TO_RIGHT, TO_LEFT);
    }

    public static <T> Map<Integer, Integer> diagonalAscLineCount(final T[][] grid, final T player) {
        final int height = grid.length;
        final int width = grid[0].length;
        return lineCount(grid, player,
                1 - width, height,
                new IntFunc() {
                    @Override public int apply(int x) {
                        return Math.max(0, -x);
                    }
                },
                new IntFunc() {
                    @Override public int apply(int x) {
                        return Math.min(width, height - x);
                    }
                },
                SUM, TO_RIGHT);
    }

    public static <T> Map<Integer, Integer> diagonalDescLineCount(final T[][] grid, final T player) {
        final int height = grid.length;
        final int width = grid[0].length;
        return lineCount(grid, player,
                0, height + width - 1,
                new IntFunc() {
                    @Override public int apply(int x) {
                        return Math.max(0, x - height + 1);
                    }
                },
                new IntFunc() {
                    @Override public int apply(int x) {
                        return Math.min(width, x + 1);
                    }
                },
                DIFF, TO_RIGHT);
    }

    private static <T> Map<Integer, Integer> lineCount(
            final T[][] grid, final T player,
            final int xStart, final int xEndExcl,
            final IntFunc yStart, final IntFunc yEndExcl,
            final BiIntFunc rowF, final BiIntFunc colF) {
        final Map<Integer, Integer> out = new LinkedHashMap<>();

        for (int x = xStart; x < xEndExcl; x++) {
            int lineSize = 0;
            for (int y = yStart.apply(x); y < yEndExcl.apply(x); y++) {
                final int row = rowF.apply(x, y);
                final int col = colF.apply(x, y);
                if (player == grid[row][col]) {
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