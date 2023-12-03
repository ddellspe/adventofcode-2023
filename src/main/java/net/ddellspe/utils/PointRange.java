package net.ddellspe.utils;

import java.util.Objects;

public class PointRange {
  private final Point startPoint;
  private final Point endPoint;

  public PointRange(Point startPoint, Point endPoint) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
  }

  public Point getStartPoint() {
    return startPoint;
  }

  public Point getEndPoint() {
    return endPoint;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startPoint, endPoint);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointRange pointRange = (PointRange) o;
    return startPoint.equals(pointRange.startPoint) && endPoint.equals(pointRange.endPoint);
  }

  @Override
  public String toString() {
    return "PointRange{" + "startPoint=" + startPoint + ", endPoint=" + endPoint + '}';
  }

  public boolean inHorizontalRange(Point point) {
    if (point.getY() == startPoint.getY() && point.getY() == endPoint.getY()) {
      return point.getX() >= startPoint.getX() && point.getX() <= endPoint.getX();
    }
    return false;
  }
}
