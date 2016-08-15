package com.fenton;

import java.awt.Color;

public interface Renderer<T> {
  Color toColor(final T dataValue);
}