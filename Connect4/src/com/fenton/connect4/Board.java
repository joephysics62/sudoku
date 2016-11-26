package com.fenton.connect4;

import java.io.PrintStream;
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
    // check vertical
    int lineSize = 1;
    int row = newPieceRow;
    while (--row >= 0 && isPlayersPiece(curr, row, newPieceCol)) {
      if (++lineSize >= LINE_SIZE_TO_WIN) {
        return true;
      }
    }
    // check horizontal
    lineSize = 1;
    row = newPieceRow;
    int col = newPieceCol;
    while (--col >= 0 && isPlayersPiece(curr, newPieceRow, col)) {
      if (++lineSize >= LINE_SIZE_TO_WIN) {
        return true;
      }
    }
    col = newPieceCol;
    while (++col < _width && isPlayersPiece(curr, newPieceRow, col)) {
      if (++lineSize >= LINE_SIZE_TO_WIN) {
        return true;
      }
    }
    // check asc diagonal

    return false;
  }

  private boolean isPlayersPiece(final Player player, final int row, final int col) {
    return _pieces[row][col] == player;
  }

}
