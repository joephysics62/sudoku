package com.fenton.connect4;

import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import junit.framework.Assert;

public class TestGridUtils {

  private final Integer X = null;

  @Test
  public void testLineCounts() {
    final Integer[][] grid =
      {
          {X, 1, 1, 1, X, 0},
          {X, X, 1, 1, 1, 1},
          {1, 1, X, 1, 1, X},
          {1, 0, 1, 0, 1, 0},
          {X, X, X, X, X, X},
    };
    Assert.assertEquals(
        ImmutableMap.of(
            2, 2,
            3, 1,
            4, 1),
        GridUtils.horizontalLineCount(grid, 1));
    Assert.assertEquals(
        Collections.emptyMap(),
        GridUtils.horizontalLineCount(grid, 0));

    Assert.assertEquals(
        ImmutableMap.of(
            2, 2,
            3, 2),
        GridUtils.verticalLineCount(grid, 1));
    Assert.assertEquals(
        Collections.emptyMap(),
        GridUtils.verticalLineCount(grid, 0));


    Assert.assertEquals(
        ImmutableMap.of(
            2, 1,
            3, 1,
            4, 1),
        GridUtils.diagonalDescLineCount(grid, 1));
    Assert.assertEquals(
        Collections.emptyMap(),
        GridUtils.diagonalDescLineCount(grid, 0));

    Assert.assertEquals(
        ImmutableMap.of(
            2, 2,
            3, 1,
            4, 1),
        GridUtils.diagonalAscLineCount(grid, 1));
    Assert.assertEquals(
        Collections.emptyMap(),
        GridUtils.diagonalAscLineCount(grid, 0));
  }

}
