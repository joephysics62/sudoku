package com.fenton.connect4;

import java.io.PrintStream;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

  private final int LINE_SIZE_TO_WIN = 4;
  private final int _width;
  private final int _height;
  private final Player[][] _pieces;
  private final int[] _currHeights;

  public Board(final int width, final int height) {
    _width = width;
    _height = height;
    _pieces = new Player[height][width];
    _currHeights = new int[width];
  }

  public boolean addToColumn(final Player player, final int column) {
    if (column < 0 || column > _width - 1) {
      return false;
    }
    if (_currHeights[column] >= _height) {
      return false;
    }
    _pieces[_currHeights[column]][column] = player;
    _currHeights[column]++;
    return true;
  }

  public void printBoard(final PrintStream pstream) {
    IntStream
      .range(0, _height)
      .map(r -> _height - r - 1)
      .forEach(rowf -> {
        final Stream<String> rowVals = IntStream.range(0, _width)
                                          .mapToObj(c -> _pieces[rowf][c])
                                          .map(v -> v == null ? " " : v.getIcon());
        printRow(rowVals, pstream);
      });
    printRow(IntStream.rangeClosed(1, _width).mapToObj(Integer::valueOf), pstream);
    pstream.println();
  }

  private <T> void printRow(final Stream<T> rowObjs, final PrintStream pstream) {
    pstream.print("|");
    rowObjs.map(o -> o + "|").forEach(pstream::print);
    pstream.println();
  }

  public boolean hasPlayedWinningMove(final Player curr, final int newPieceCol) {
    final int newPieceRow = _currHeights[newPieceCol] - 1;

    return isVerticalWin(curr, newPieceRow, newPieceCol)
        || isHorizontalWin(curr, newPieceRow, newPieceCol)
        || isDiagonalAscWin(curr, newPieceRow, newPieceCol)
        || isDiagonalDescWin(curr, newPieceRow, newPieceCol);
  }

  private int lineSizeInDirection(final int initialLineSize, final int newPieceRow, final int newPieceCol,
                                  final Player curr, final IntFunction<Integer> rowFunc, final IntFunction<Integer> colFunc) {
    int lineSize = initialLineSize;
    int row = newPieceRow;
    int col = newPieceCol;
    while(true) {
      row = rowFunc.apply(row);
      col = colFunc.apply(col);
      if (isPlayersPiece(curr, row, col)) {
        lineSize++;
      }
      else {
        break;
      }
    }
    return lineSize;
  }

  private boolean isVerticalWin(final Player curr, final int newPieceRow, final int newPieceCol) {
    final int lineSize = lineSizeInDirection(1, newPieceRow, newPieceCol, curr, r -> r - 1, c -> c);
    return lineSize >= LINE_SIZE_TO_WIN;
  }

  private boolean isHorizontalWin(final Player curr, final int row, final int col) {
    final int lineSize = lineSizeInDirection(1, row, col, curr, r -> r, c -> c - 1);
    if (lineSize >= LINE_SIZE_TO_WIN) {
      return true;
    }
    return lineSizeInDirection(lineSize, row, col, curr, r -> r, c -> c + 1) >= LINE_SIZE_TO_WIN;
  }

  private boolean isDiagonalAscWin(final Player curr, final int row, final int col) {
    final int lineSize = lineSizeInDirection(1, row, col, curr, r -> r + 1, c -> c + 1);
    if (lineSize >= LINE_SIZE_TO_WIN) {
      return true;
    }
    return lineSizeInDirection(lineSize, row, col, curr, r -> r - 1, c -> c - 1) >= LINE_SIZE_TO_WIN;
  }

  private boolean isDiagonalDescWin(final Player curr, final int row, final int col) {
    final int lineSize = lineSizeInDirection(1, row, col, curr, r -> r - 1, c -> c + 1);
    if (lineSize >= LINE_SIZE_TO_WIN) {
      return true;
    }
    return lineSizeInDirection(lineSize, row, col, curr, r -> r + 1, c -> c - 1) >= LINE_SIZE_TO_WIN;
  }

  private boolean isPlayersPiece(final Player player, final int row, final int col) {
    if (row < 0 || col < 0 || row >= _height || col >= _width) {
      return false;
    }
    return _pieces[row][col] == player;
  }

}
