package com.fenton.abstractstrategy;

public enum Player {
  RED("X", true),
  YELLOW("O", false);

  private final String _icon;
  private final boolean _isHuman;

  private Player(final String icon, final boolean isHuman) {
    _icon = icon;
    _isHuman = isHuman;
  }

  public String getIcon() {
    return _icon;
  }

  public boolean isHuman() {
    return _isHuman;
  }

  public Player nextPlayer() {
    return values() [(ordinal() + 1) % values().length];
  }

}
