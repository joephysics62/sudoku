package com.fenton.suguru;

import java.io.PrintStream;
import java.util.Set;

public class ConsolePrintingVisitor<T> implements GridVisitor<T> {

  private final PrintStream _printStream;

  public ConsolePrintingVisitor(final PrintStream printStream) {
    _printStream = printStream;
  }

  @Override
  public void start() {
  }

  @Override
  public void end() {
    _printStream.println();
  }

  @Override
  public void startRow(final int row) {
    _printStream.print("|");
  }

  @Override
  public void endRow(final int row) {
    _printStream.println();
  }

  @Override
  public void handleValue(final int row, final int col, final Set<T> set) {
//    if (set.size() == 1) {
//      _printStream.print(set.iterator().next());
//    }
//    else {
//      _printStream.print("?");
//    }
    _printStream.print(set);
    _printStream.print("|");
  }

}
