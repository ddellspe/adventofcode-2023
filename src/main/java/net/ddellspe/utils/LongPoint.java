package net.ddellspe.utils;

import java.util.Objects;

public class LongPoint {
  private long x;
  private long y;

  public LongPoint(long x, long y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LongPoint point = (LongPoint) o;
    return x == point.x && y == point.y;
  }

  public long getX() {
    return x;
  }

  public long getY() {
    return y;
  }

  public void setX(long x) {
    this.x = x;
  }

  public void setY(long y) {
    this.y = y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "LongPoint [x=" + x + ", y=" + y + "]";
  }
}
