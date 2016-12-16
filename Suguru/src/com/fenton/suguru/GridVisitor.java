package com.fenton.suguru;

import java.util.Set;

public interface GridVisitor<T> {

  void start();

  void end();

  void startRow(int row);

  void endRow(int row);

  void handleValue(int row, int col, Set<T> set);

}
