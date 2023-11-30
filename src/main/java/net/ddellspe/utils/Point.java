package net.ddellspe.utils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Point {
  private long x;
  private long y;

  public Point(long x, long y) {
    this.x = x;
    this.y = y;
  }

  public Point(String point) {
    this.x = Integer.parseInt(point.split(",")[0]);
    this.y = Integer.parseInt(point.split(",")[1]);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Point point = (Point) o;
    return x == point.x && y == point.y;
  }

  public Set<Point> getPointsBetween(Point point) {
    if (this.x != point.x && this.y != point.y) {
      throw new IllegalStateException("Points must either share an X or Y coordinate");
    }
    Set<Point> pointsBetween = new HashSet<>();
    if (this.x < point.x) {
      for (long x = this.x; x <= point.x; x++) {
        pointsBetween.add(new Point(x, this.y));
      }
    } else if (this.x > point.x) {
      for (long x = point.x; x <= this.x; x++) {
        pointsBetween.add(new Point(x, this.y));
      }
    } else {
      if (this.y < point.y) {
        for (long y = this.y; y <= point.y; y++) {
          pointsBetween.add(new Point(this.x, y));
        }
      } else {
        for (long y = point.y; y <= this.y; y++) {
          pointsBetween.add(new Point(this.x, y));
        }
      }
    }
    return pointsBetween;
  }

  public long getManhattanDistance(Point point) {
    return (Math.abs(this.x - point.x) + Math.abs(this.y - point.y));
  }

  public Set<Point> generatePointsAtManhattanDistance(int distance) {
    Set<Point> points = new HashSet<>();
    for (int deltaX = 0; deltaX <= distance; deltaX++) {
      for (int deltaY = 0; deltaY <= distance; deltaY++) {
        if (deltaX + deltaY <= distance) {
          points.add(new Point(x + deltaX, y + deltaY));
          points.add(new Point(x + deltaX, y - deltaY));
          points.add(new Point(x - deltaX, y + deltaY));
          points.add(new Point(x - deltaX, y - deltaY));
        }
      }
    }
    return points;
  }

  public Set<Point> getNorthNeighbors() {
    Set<Point> points = new HashSet<>();
    points.add(new Point(x - 1, y - 1));
    points.add(new Point(x, y - 1));
    points.add(new Point(x + 1, y - 1));
    return points;
  }

  public Set<Point> getEastNeighbors() {
    Set<Point> points = new HashSet<>();
    points.add(new Point(x + 1, y - 1));
    points.add(new Point(x + 1, y));
    points.add(new Point(x + 1, y + 1));
    return points;
  }

  public Set<Point> getSouthNeighbors() {
    Set<Point> points = new HashSet<>();
    points.add(new Point(x - 1, y + 1));
    points.add(new Point(x, y + 1));
    points.add(new Point(x + 1, y + 1));
    return points;
  }

  public Set<Point> getWestNeighbors() {
    Set<Point> points = new HashSet<>();
    points.add(new Point(x - 1, y - 1));
    points.add(new Point(x - 1, y));
    points.add(new Point(x - 1, y + 1));
    return points;
  }

  public Set<Point> getDirectNeighbors() {
    Set<Point> points = new HashSet<>();
    points.add(new Point(x + 1, y));
    points.add(new Point(x - 1, y));
    points.add(new Point(x, y + 1));
    points.add(new Point(x, y - 1));
    return points;
  }

  public Set<Point> getAllNeighbors() {
    Set<Point> points = new HashSet<>();
    for (long x = -1; x <= 1; x++) {
      for (long y = -1; y <= 1; y++) {
        if (x == 0 && y == 0) {
          continue;
        }
        points.add(new Point(this.x + x, this.y + y));
      }
    }
    return points;
  }

  public long getX() {
    return x;
  }

  public long getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Point [x=" + x + ", y=" + y + "]";
  }
}
