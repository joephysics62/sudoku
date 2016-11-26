package com.fenton.connect4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

  private final int LINE_SIZE_TO_WIN = 4;
  private final int _width;
  private final int _height;
  private final List<List<Player>> _pieces;

  public Board(final int width, final int height) {
    _width = width;
    _height = height;
    _pieces = init(width);
  }

  public boolean addToColumn(final Player player, final int column) {
    if (column < 0 || column > _width - 1) {
      return false;
    }
    final List<Player> columnPieces = _pieces.get(column);
    if (columnPieces.size() >= _height) {
      return false;
    }
    columnPieces.add(player);
    return true;
  }

  public void printBoard(final PrintStream pstream) {
    IntStream
      .range(0, _height)
      .map(r -> _height - r)
      .forEach(rowf -> {
        final Stream<String> rowVals = IntStream.range(0, _width)
                                          .mapToObj(_pieces::get)
                                          .map(col -> {
                                            if (col.size() < rowf) {
                                              return " ";
                                            }
                                            return col.get(rowf - 1).getIcon();
                                          });
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

  private List<List<Player>> init(final int width) {
    final List<List<Player>> pieces = new ArrayList<>(width);
    for (int col = 0; col < width; col++) {
      pieces.add(new ArrayList<>());
    }
    return pieces;
  }

  public boolean hasPlayedWinningMove(final Player curr, final int newPieceCol) {
    final List<Player> columnList = _pieces.get(newPieceCol);
    final int newPieceRow = columnList.size() - 1;
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
    return _pieces.get(col).size() > row && player == _pieces.get(col).get(row);
  }

}
