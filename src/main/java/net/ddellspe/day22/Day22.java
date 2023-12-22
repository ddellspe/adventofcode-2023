package net.ddellspe.day22;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;
import net.ddellspe.utils.Point3D;
import net.ddellspe.utils.PointRange;

public class Day22 {
  private Day22() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day22.class);
    List<Brick> bricks = new ArrayList<>();
    for (String line : lines) {
      bricks.add(new Brick(line));
    }
    bricks.sort(Brick::compareTo);
    Tower originalTower = simulateDrop(new Tower(0, bricks));
    int nonFalls = 0;
    for (Brick brick : originalTower.tower) {
      List<Brick> tempBricks = new ArrayList<>(originalTower.tower);
      tempBricks.remove(brick);
      Tower t = simulateDrop(new Tower(0, tempBricks));
      if (t.falls == 0) {
        nonFalls++;
      }
    }
    return nonFalls;
  }

  static Tower simulateDrop(Tower input) {
    List<Brick> tower = input.tower;
    long minX =
        tower.stream().mapToLong(b -> b.getExtrema().getStartPoint().getX()).min().getAsLong();
    long maxX =
        tower.stream().mapToLong(b -> b.getExtrema().getEndPoint().getX()).max().getAsLong();
    long minY =
        tower.stream().mapToLong(b -> b.getExtrema().getStartPoint().getY()).min().getAsLong();
    long maxY =
        tower.stream().mapToLong(b -> b.getExtrema().getEndPoint().getY()).max().getAsLong();
    Map<Point, Long> tallest = new HashMap<>();
    for (long x = minX; x <= maxX; x++) {
      for (long y = minY; y <= maxY; y++) {
        tallest.put(new Point(x, y), 0L);
      }
    }
    List<Brick> newTower = new ArrayList<>();
    long falls = 0;
    for (Brick brick : tower) {
      Brick newBrick = brick.drop(tallest);
      if (newBrick.getMinZ() != brick.getMinZ()) {
        falls++;
      }
      newTower.add(newBrick);
      for (Point pt : newBrick.getXYPoints()) {
        tallest.put(pt, newBrick.getMaxZ());
      }
    }
    return new Tower(falls, newTower);
  }

  static class Tower {
    long falls;
    List<Brick> tower;

    public Tower(long falls, List<Brick> tower) {
      this.falls = falls;
      this.tower = tower;
    }
  }

  static class Brick implements Comparable<Brick> {
    Point3D point1;
    Point3D point2;

    public Brick(String line) {
      point1 = new Point3D(line.split("~")[0]);
      point2 = new Point3D(line.split("~")[1]);
    }

    public Brick(Point3D point1, Point3D point2) {
      this.point1 = point1;
      this.point2 = point2;
    }

    public PointRange getExtrema() {
      long minX = Math.min(point1.getX(), point2.getX());
      long maxX = Math.max(point1.getX(), point2.getX());
      long minY = Math.min(point1.getY(), point2.getY());
      long maxY = Math.max(point1.getY(), point2.getY());
      return (new PointRange(new Point(minX, minY), new Point(maxX, maxY)));
    }

    public Brick drop(Map<Point, Long> tallest) {
      long peak =
          getXYPoints().stream().mapToLong(p -> tallest.getOrDefault(p, 0L)).max().getAsLong();
      long dz = Math.max(getMinZ() - peak - 1, 0);
      return new Brick(
          new Point3D(point1.getX(), point1.getY(), point1.getZ() - dz),
          new Point3D(point2.getX(), point2.getY(), point2.getZ() - dz));
    }

    public long getMinZ() {
      return Math.min(point1.getZ(), point2.getZ());
    }

    public long getMaxZ() {
      return Math.max(point1.getZ(), point2.getZ());
    }

    public Set<Point> getXYPoints() {
      Set<Point> points = new HashSet<>();
      PointRange extremes = getExtrema();
      for (long x = extremes.getStartPoint().getX(); x <= extremes.getEndPoint().getX(); x++) {
        for (long y = extremes.getStartPoint().getY(); y <= extremes.getEndPoint().getY(); y++) {
          points.add(new Point(x, y));
        }
      }
      return points;
    }

    @Override
    public int compareTo(Brick o) {
      return Long.compare(
          Math.min(point1.getZ(), point2.getZ()), Math.min(o.point1.getZ(), o.point2.getZ()));
    }
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day22.class);
    List<Brick> bricks = new ArrayList<>();
    for (String line : lines) {
      bricks.add(new Brick(line));
    }
    bricks.sort(Brick::compareTo);
    Tower originalTower = simulateDrop(new Tower(0, bricks));
    long falls = 0;
    for (Brick brick : originalTower.tower) {
      List<Brick> tempBricks = new ArrayList<>(originalTower.tower);
      tempBricks.remove(brick);
      Tower t = simulateDrop(new Tower(0, tempBricks));
      falls += t.falls;
    }
    return falls;
  }
}
