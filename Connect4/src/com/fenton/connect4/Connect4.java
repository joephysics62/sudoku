package com.fenton.connect4;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fenton.abstractstrategy.AbstractStategyGame;
import com.fenton.abstractstrategy.Player;

public class Connect4 implements AbstractStategyGame<Integer> {

  private static final int LINE_SIZE_TO_WIN = 4;
  private final int _width;
  private final int _height;
  private final Player[][] _pieces;
  private final int[] _currHeights;
  private int _movesCount;

  private final long[][][] _zobrist;

  private long _hash;

  private final EnumMap<Player, Integer> _playerWeights;

  /**
   * |X|O|O|X|O| | |
     |O|X|X|X|O|X|X|
     |X|O|X|X|X|O|O|
     |X|X|X|O|O|O|X|
     |O|X|O|O|X|O|O|
     |O|O|X|X|O|X|O|

     causes error O to play next
   * @param width
   * @param height
   */

  public Connect4(final int width, final int height) {
    _width = width;
    _height = height;
    _pieces = new Player[height][width];
    _currHeights = new int[width];
    final Random r = new Random(123l);
    _hash = r.nextLong();
    _zobrist = initZobrist(width, height, r);

    _playerWeights = new EnumMap<>(Player.class);
    for (final Player player : Player.values()) {
      _playerWeights.put(player, 0);
    }
  }

  private long[][][] initZobrist(final int width, final int height, final Random random) {
    final long[][][] zobrist = new long[height][width][Player.values().length];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        for (final Player player : Player.values()) {
          zobrist[r][c][player.ordinal()] = random.nextLong();
        }
      }
    }
    return zobrist;
  }

  @Override
  public long hash() {
    return _hash;
  }

  private Connect4(final int width, final int height, final Player[][] pieces, final int[] currHeights,
                   final int movesCount, final long[][][] zobrist, final long hash, final EnumMap<Player, Integer> weights) {
    _width = width;
    _height = height;
    _pieces = pieces;
    _currHeights = currHeights;
    _movesCount = movesCount;
    _zobrist = zobrist;
    _hash = hash;
    _playerWeights = weights;
  }

  @Override
  public AbstractStategyGame<Integer> clone() {
    final Player[][] pieces = new Player[_height][_width];
    for (int row = 0; row < _height; row++) {
      pieces[row] = _pieces[row].clone();
    }
    return new Connect4(_width, _height, pieces, _currHeights.clone(), _movesCount, _zobrist, _hash, new EnumMap<>(_playerWeights));
  }

  @Override
  public boolean hasMovesRemaining() {
    return _movesCount < _width * _height;
  }

  @Override
  public boolean isValidMove(final Integer move) {
    return move >= 0 && move < _width && _currHeights[move] < _height;
  }

  @Override
  public List<Integer> validMoves() {
    final List<Integer> moves = new ArrayList<>();
    for (int col = 0; col < _width; col++) {
      if (isValidMove(col)) {
        moves.add(col);
      }
    }
    return moves;
  }

  private static final int[] WEIGHTS_FOR_LENGTH = new int[] {0, 0, 10, 100, 1000, 10000, 100000, 1000000};
  //                                                         0, 1, 2,  3,   4,    5,     6,      7

  @Override
  public boolean makeMove(final Integer move, final Player player) {
    final int heightAtCol = _currHeights[move];
    _pieces[heightAtCol][move] = player;
    _currHeights[move]++;
    _movesCount++;
    _hash ^= _zobrist[heightAtCol][move][player.ordinal()];

    int weightForPlayer = _playerWeights.get(player);

    for (final GridDirection gridDirection : GridDirection.values()) {
      if (isWinInDirection(heightAtCol, move, player, gridDirection)) {
        return true;
      }
      weightForPlayer += strengthsInDirection(heightAtCol, move, player, gridDirection);
    }
    _playerWeights.put(player, weightForPlayer);
    return false;
  }

  private int strengthsInDirection(final int row, final int col, final Player player, final GridDirection gridDirection) {
    int strength = 0;
    for (int setStart = -(LINE_SIZE_TO_WIN - 1); setStart <= 0; setStart++) {
      final int rowStart = row + setStart * gridDirection.rowStep();
      final int colStart = col + setStart * gridDirection.colStep();
      if (!isInBounds(rowStart, colStart)) {
        continue;
      }
      final int rowEnd = rowStart + (LINE_SIZE_TO_WIN - 1) * gridDirection.rowStep();
      final int colEnd = colStart + (LINE_SIZE_TO_WIN - 1) * gridDirection.colStep();
      if (!isInBounds(rowEnd, colEnd)) {
        continue;
      }
      int pieceCount = 0;
      boolean seenOpposition = false;
      for (int i = 0; i < LINE_SIZE_TO_WIN; i++) {
        final int rowInner = rowStart + i * gridDirection.rowStep();
        final int colInner = colStart + i * gridDirection.colStep();
        final Player pieceInner = _pieces[rowInner][colInner];
        if (pieceInner == player) {
          pieceCount++;
        }
        else if (pieceInner != null) {
          seenOpposition = true;
          break;
        }
      }
      if (!seenOpposition) {
        strength += WEIGHTS_FOR_LENGTH[pieceCount];
      }
    }
    return strength;
  }

  private boolean isWinInDirection(final int newPieceRow, final int newPieceCol, final Player player, final GridDirection direction) {
    int lineSize = 0;
    int row = newPieceRow;
    int col = newPieceCol;
    while(lineSize < LINE_SIZE_TO_WIN && isInBounds(row, col) && _pieces[row][col] == player) {
      row += direction.rowStep();
      col += direction.colStep();
      lineSize++;
    }
    row = newPieceRow - direction.rowStep();
    col = newPieceCol - direction.colStep();
    while(lineSize < LINE_SIZE_TO_WIN && isInBounds(row, col) && _pieces[row][col] == player) {
      row -= direction.rowStep();
      col -= direction.colStep();
      lineSize++;
    }
    return lineSize >= LINE_SIZE_TO_WIN;
  }

  private boolean isInBounds(final int row, final int col) {
    return row >= 0 && col >= 0 && row < _height && col < _width;
  }

  @Override
  public int boardVal(final Player player) {
    int weighting = 0;
    final Set<Entry<Player, Integer>> entrySet = _playerWeights.entrySet();
    for (final Entry<Player, Integer> entry : entrySet) {
      if (player == entry.getKey()) {
        weighting += entry.getValue();
      }
      else {
        weighting -= entry.getValue();
      }
    }
    return weighting;
  }

  @Override
  public void print(final PrintStream pstream) {
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

  @Override
  public Integer waitForUserMove() throws IOException {
    while (true) {
      final int readByte = System.in.read();
      final char readChar = (char) readByte;
      final String readString = String.valueOf(readChar);
      if (readString.matches("[1-" + _width + "]")) {
        return Integer.valueOf(readString) - 1;
      }
    }
  }

}
