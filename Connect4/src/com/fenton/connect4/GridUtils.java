package com.fenton.connect4;

import java.util.ArrayList;
import java.util.List;

import com.fenton.abstractstrategy.Player;

public class GridUtils {

  public static int diagonalDescWeight(final Player[][] grid, final Player max, final int winSize) {
    final int height = grid.length;
    final int width = grid[0].length;
    int weight = 0;

    final List<Player> section = new ArrayList<>(winSize);
    for (int rowStart = winSize - 1; rowStart < height; rowStart++) {
      int col = 0;
      int row = rowStart;

      while (col < width && row >= 0) {
        final Player piece = grid[row][col];
        weight += weightForSection(section, piece, max, winSize);
        col++;
        row--;
      }
      section.clear();
    }

    for (int colStart = 1; colStart < width - winSize + 1; colStart++) {
      int col = colStart;
      int row = height - 1;

      while (col < width && row >= 0) {
        final Player piece = grid[row][col];
        weight += weightForSection(section, piece, max, winSize);
        col++;
        row--;
      }
      section.clear();
    }

    return weight;

  }

  public static int diagonalAscWeight(final Player[][] grid, final Player max, final int winSize) {
    final int height = grid.length;
    final int width = grid[0].length;

    int weight = 0;
    final List<Player> section = new ArrayList<>(winSize);
    for (int rowStart = height - winSize; rowStart >= 0; rowStart--) {
      int col = 0;
      int row = rowStart;

      while (col < width && row < height) {
        final Player piece = grid[row][col];
        weight += weightForSection(section, piece, max, winSize);
        col++;
        row++;
      }
      section.clear();
    }

    for (int colStart = 1; colStart < width - winSize + 1; colStart++) {
      int col = colStart;
      int row = 0;

      while (col < width && row < height) {
        final Player piece = grid[row][col];
        weight += weightForSection(section, piece, max, winSize);
        col++;
        row++;
      }
      section.clear();
    }

    return weight;
  }

  private static final int[] POWERS_10 = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000};

  private static int weightForSection(final List<Player> section, final Player piece, final Player max, final int winSize) {
    if (section.size() < winSize) {
      section.add(piece);
      return 0;
    }
    int weight = 0;

    int maxCount = 0;
    int minCount = 0;
    for (final Player sectionPiece : section) {
      if (sectionPiece == max) {
        maxCount++;
      }
      else if (sectionPiece != null) {
        minCount++;
      }
    }

    if (minCount == 0 && maxCount >= 2) {
      weight = POWERS_10[maxCount];
    }
    else if (maxCount == 0 && minCount >= 2) {
      weight = -POWERS_10[minCount];
    }
    // do stats on section
    // then
    section.remove(0);
    section.add(piece);
    return weight;
  }

  public static int horizontalWeight(final Player[][] grid, final Player max, final int winSize) {
    final int height = grid.length;
    final int width = grid[0].length;

    int weight = 0;
    final List<Player> section = new ArrayList<>(winSize);
    for (int row = 0; row < height; row++) {
      int linepos = 0;
      while (linepos < width) {
        final Player piece = grid[row][linepos];
        weight += weightForSection(section, piece, max, winSize);
        linepos++;
      }
      section.clear();
    }
    return weight;
  }

  public static int verticalWeight(final Player[][] grid, final Player max, final int winSize) {
    final int height = grid.length;
    final int width = grid[0].length;
    int weight = 0;
    final List<Player> section = new ArrayList<>(winSize);
    for (int col = 0; col < width; col++) {
      int linepos = 0;
      while (linepos < height) {
        final Player piece = grid[linepos][col];
        weight += weightForSection(section, piece, max, winSize);
        linepos++;
      }
      section.clear();
    }
    return weight;
  }


}
