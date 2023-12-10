package net.ddellspe.day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day10 {
  private Day10() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day10.class);
    Map<Point, String> square = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (!String.valueOf(line.charAt(x)).equals(".")) {
          square.put(new Point(x, y), String.valueOf(line.charAt(x)));
        }
      }
    }
    Point startingPoint =
        square.entrySet().stream()
            .filter(e -> e.getValue().equals("S"))
            .map(Entry::getKey)
            .findFirst()
            .get();
    List<Point> path = new ArrayList<>();
    Point currentPoint = startingPoint;
    String direction = "";
    while (!currentPoint.equals(startingPoint) || path.isEmpty()) {
      path.add(currentPoint);
      String currentConnector = square.get(currentPoint);
      Point up = new Point(currentPoint.getX(), currentPoint.getY() - 1);
      String upConnector = square.getOrDefault(up, ".");
      Point down = new Point(currentPoint.getX(), currentPoint.getY() + 1);
      String downConnector = square.getOrDefault(down, ".");
      Point left = new Point(currentPoint.getX() - 1, currentPoint.getY());
      Point right = new Point(currentPoint.getX() + 1, currentPoint.getY());
      if ("|LJS".contains(currentConnector)
          && !"D".equals(direction)
          && "|F7S".contains(upConnector)) {
        currentPoint = up;
        direction = "U";
      } else if ("|F7S".contains(currentConnector)
          && !"U".equals(direction)
          && "|LJS".contains(downConnector)) {
        currentPoint = down;
        direction = "D";
      } else if ("-J7S".contains(currentConnector) && !"R".equals(direction)) {
        currentPoint = left;
        direction = "L";
      } else {
        currentPoint = right;
        direction = "R";
      }
    }
    path.add(currentPoint);
    return (long) path.size() / 2;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day10.class);
    Map<Point, String> square = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (!String.valueOf(line.charAt(x)).equals(".")) {
          square.put(new Point(x, y), String.valueOf(line.charAt(x)));
        }
      }
    }
    Point startingPoint =
        square.entrySet().stream()
            .filter(e -> e.getValue().equals("S"))
            .map(Entry::getKey)
            .findFirst()
            .get();
    List<Point> path = new ArrayList<>();
    Map<Point, String> directions = new HashMap<>();
    Point currentPoint = startingPoint;
    String direction = "";
    while (!currentPoint.equals(startingPoint) || path.isEmpty()) {
      path.add(currentPoint);
      directions.put(currentPoint, direction);
      String currentConnector = square.get(currentPoint);
      Point up = new Point(currentPoint.getX(), currentPoint.getY() - 1);
      String upConnector = square.getOrDefault(up, ".");
      Point down = new Point(currentPoint.getX(), currentPoint.getY() + 1);
      String downConnector = square.getOrDefault(down, ".");
      Point left = new Point(currentPoint.getX() - 1, currentPoint.getY());
      Point right = new Point(currentPoint.getX() + 1, currentPoint.getY());
      if ("|LJS".contains(currentConnector)
          && !"D".equals(direction)
          && "|F7S".contains(upConnector)) {
        currentPoint = up;
        direction = "U";
      } else if ("|F7S".contains(currentConnector)
          && !"U".equals(direction)
          && "|LJS".contains(downConnector)) {
        currentPoint = down;
        direction = "D";
      } else if ("-J7S".contains(currentConnector) && !"R".equals(direction)) {
        currentPoint = left;
        direction = "L";
      } else {
        currentPoint = right;
        direction = "R";
      }
    }
    directions.put(currentPoint, direction);

    // determine the letter for the starting point
    String initial = directions.get(path.get(1));
    String last = directions.get(startingPoint);
    if (initial.equals("U")) {
      if (last.equals("U")) {
        square.put(startingPoint, "|");
      } else if (last.equals("L")) {
        square.put(startingPoint, "L");
      } else {
        square.put(startingPoint, "J");
      }
    } else if (initial.equals("D")) {
      if (last.equals("L")) {
        square.put(startingPoint, "F");
      } else {
        square.put(startingPoint, "7");
      }
    } else {
      square.put(startingPoint, "-");
    }

    // get bounding boxes for each x and y
    long minY = path.stream().mapToLong(Point::getY).min().getAsLong();
    long maxY = path.stream().mapToLong(Point::getY).max().getAsLong();
    long minX = path.stream().mapToLong(Point::getX).min().getAsLong();
    long maxX = path.stream().mapToLong(Point::getX).max().getAsLong();
    Map<Long, Point> xExtremes = new HashMap<>();
    Map<Long, Point> yExtremes = new HashMap<>();
    for (long x = minX; x <= maxX; x++) {
      long finalX = x;
      long minValue =
          path.stream().filter(p -> p.getX() == finalX).mapToLong(Point::getY).min().getAsLong();
      long maxValue =
          path.stream().filter(p -> p.getX() == finalX).mapToLong(Point::getY).max().getAsLong();
      xExtremes.put(x, new Point(minValue, maxValue));
    }
    for (long y = minY; y <= maxY; y++) {
      long finalY = y;
      long minValue =
          path.stream().filter(p -> p.getY() == finalY).mapToLong(Point::getX).min().getAsLong();
      long maxValue =
          path.stream().filter(p -> p.getY() == finalY).mapToLong(Point::getX).max().getAsLong();
      yExtremes.put(y, new Point(minValue, maxValue));
    }

    Set<Point> inside = new HashSet<>();
    for (long y = minY; y <= maxY; y++) {
      long finalY = y;
      List<Point> points = path.stream().filter(p -> p.getY() == finalY).toList();
      for (long x = minX; x <= maxX; x++) {
        long finalX = x;
        // get the number of vertical pipes and pipes terminating at the bottom to the left of the
        // current x if it's odd, we're inside, even we're outside
        long verticalCount =
            points.stream()
                .filter(p -> p.getX() < finalX)
                .filter(p -> "|JL".contains(square.getOrDefault(p, ".")))
                .count();
        long xMin = yExtremes.get(y).getX();
        long xMax = yExtremes.get(y).getY();
        long yMin = xExtremes.get(x).getX();
        long yMax = xExtremes.get(x).getY();
        Point pt = new Point(x, y);
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
          if (!path.contains(pt) && (verticalCount % 2 == 1)) {
            inside.add(pt);
          }
        }
      }
    }
    return inside.size();
  }
}
