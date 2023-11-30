package net.ddellspe.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Point3D implements Comparable {
  private long x;
  private long y;
  private long z;

  public Point3D(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Point3D(String coordinates) {
    String[] coords = coordinates.split(",");
    this.x = Integer.parseInt(coords[0]);
    this.y = Integer.parseInt(coords[1]);
    this.z = Integer.parseInt(coords[2]);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Point3D point = (Point3D) o;
    return x == point.x && y == point.y && z == point.z;
  }

  public long getX() {
    return x;
  }

  public long getY() {
    return y;
  }

  public long getZ() {
    return z;
  }

  public void setX(long x) {
    this.x = x;
  }

  public void setY(long y) {
    this.y = y;
  }

  public void setZ(long z) {
    this.z = z;
  }

  public List<Point3D> getDifference(Point3D pt) {
    List<Point3D> points = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      long x = pt.x;
      long y = pt.y;
      long z = pt.z;
      switch (i) {
        case 1:
          y = pt.z;
          z = pt.y;
          break;
        case 2:
          x = pt.y;
          y = pt.x;
          break;
        case 3:
          x = pt.y;
          y = pt.z;
          z = pt.x;
          break;
        case 4:
          x = pt.z;
          z = pt.x;
          break;
        case 5:
          x = pt.z;
          y = pt.x;
          z = pt.y;
          break;
      }
      for (int j = 0; j < 8; j++) {
        points.add(
            new Point3D(
                this.x + x * (j / 4 % 2 == 0 ? -1 : 1),
                this.y + y * (j / 2 % 2 == 0 ? -1 : 1),
                this.z + z * (j % 2 == 0 ? -1 : 1)));
      }
    }
    return points;
  }

  public Point3D getPointWithDiff(Point3D point, int orientation) {
    long x = this.x;
    long y = this.y;
    long z = this.z;
    switch (orientation / 8) {
      case 1:
        y = this.z;
        z = this.y;
        break;
      case 2:
        x = this.y;
        y = this.x;
        break;
      case 3:
        x = this.y;
        y = this.z;
        z = this.x;
        break;
      case 4:
        x = this.z;
        z = this.x;
        break;
      case 5:
        x = this.z;
        y = this.x;
        z = this.y;
        break;
    }
    int ori = orientation % 8;
    return new Point3D(
        point.getX() + (x * ((ori / 4 % 2 == 0) ? 1 : -1)),
        point.getY() + (y * ((ori / 2 % 2 == 0) ? 1 : -1)),
        point.getZ() + (z * ((ori % 2 == 0) ? 1 : -1)));
  }

  public long manhattanDistance(Point3D pt2) {
    return Math.abs(x - pt2.getX()) + Math.abs(y - pt2.getY()) + Math.abs(z - pt2.getZ());
  }

  public Set<Point3D> getDirectNeighbors() {
    Set<Point3D> points = new HashSet<>();
    for (long x = -1; x <= 1; x++) {
      for (long y = -1; y <= 1; y++) {
        for (long z = -1; z <= 1; z++) {
          if (Math.abs(x) + Math.abs(y) + Math.abs(z) == 1) {
            points.add(new Point3D(this.x + x, this.y + y, this.z + z));
          }
        }
      }
    }
    return points;
  }

  public Set<Point3D> getAllNeighbors() {
    Set<Point3D> points = new HashSet<>();
    for (long x = -1; x <= 1; x++) {
      for (long y = -1; y <= 1; y++) {
        for (long z = -1; z <= 1; z++) {
          if (x == 0 && y == 0 && z == 0) {
            continue;
          }
          points.add(new Point3D(this.x + x, this.y + y, this.z + z));
        }
      }
    }
    return points;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public String toString() {
    return "Point3D [x=" + x + ", y=" + y + ", z=" + z + "]";
  }

  @Override
  public int compareTo(Object o) {
    if (o.getClass() != Point3D.class) {
      throw new UnsupportedOperationException(
          "Unable to sort Point3D and " + o.getClass().getName());
    }
    Point3D point = (Point3D) o;
    if (x < point.x) {
      return -1;
    } else if (x == point.x) {
      if (y < point.y) {
        return -1;
      } else if (y == point.y) {
        return Long.compare(z, point.z);
      } else {
        return 1;
      }
    } else {
      return 1;
    }
  }
}
