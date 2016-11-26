package com.fenton.connect4;

public enum Player {
  RED("X"),
  YELLOW("O");

  private final String _icon;

  private Player(final String icon) {
    _icon = icon;
  }

  public String getIcon() {
    return _icon;
  }
}
