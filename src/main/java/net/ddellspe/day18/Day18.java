package net.ddellspe.day18;

import java.util.ArrayList;
import java.util.List;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day18 {
  private Day18() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day18.class);
    List<Point> corners = new ArrayList<>();
    Point pt = new Point(0, 0);
    corners.add(pt);
    long border = 0;
    for (String line : lines) {
      String[] data = line.split("[ ]+");
      int length = Integer.parseInt(data[1]);
      switch (data[0]) {
        case "R":
          pt = pt.move(new Point(length, 0));
          corners.add(pt);
          break;
        case "L":
          pt = pt.move(new Point(-length, 0));
          corners.add(pt);
          break;
        case "D":
          pt = pt.move(new Point(0, length));
          corners.add(pt);
          break;
        default:
          pt = pt.move(new Point(0, -length));
          corners.add(pt);
          break;
      }
      border += length;
    }
    long area = 0L;
    pt = null;
    for (Point point : corners) {
      if (pt == null) {
        pt = point;
        continue;
      }
      area += (pt.getY() + point.getY()) * (pt.getX() - point.getX());
      pt = point;
    }

    return (Math.abs(area) + border) / 2L + 1L;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day18.class);
    List<Point> corners = new ArrayList<>();
    Point pt = new Point(0, 0);
    corners.add(pt);
    long border = 0;
    for (String line : lines) {
      String data = line.split("#")[1];
      long length = Long.parseLong(data.substring(0, 5), 16);
      switch (data.substring(5, 6)) {
        case "0":
          pt = pt.move(new Point(length, 0));
          corners.add(pt);
          break;
        case "2":
          pt = pt.move(new Point(-length, 0));
          corners.add(pt);
          break;
        case "1":
          pt = pt.move(new Point(0, length));
          corners.add(pt);
          break;
        default:
          pt = pt.move(new Point(0, -length));
          corners.add(pt);
          break;
      }
      border += length;
    }
    long area = 0L;
    pt = null;
    for (Point point : corners) {
      if (pt == null) {
        pt = point;
        continue;
      }
      area += (pt.getY() + point.getY()) * (pt.getX() - point.getX());
      pt = point;
    }

    return (Math.abs(area) + border) / 2L + 1L;
  }
}
