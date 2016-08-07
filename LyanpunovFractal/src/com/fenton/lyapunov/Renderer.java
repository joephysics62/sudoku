package com.fenton.lyapunov;

import java.awt.Color;

interface Renderer<T extends Number> {
  Color toColor(final T dataValue);
}