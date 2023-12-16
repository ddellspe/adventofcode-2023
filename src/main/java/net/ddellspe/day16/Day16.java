package net.ddellspe.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day16 {
  private Day16() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day16.class);
    Map<Point, Character> tiles = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        tiles.put(new Point(x, y), line.charAt(x));
      }
    }
    Set<Beam> beamPaths = simulateBeams(tiles, new Beam(new Point(-1, 0), Direction.RIGHT));
    return beamPaths.stream().map(b -> b.point).collect(Collectors.toSet()).size();
  }

  private static Set<Beam> simulateBeams(Map<Point, Character> tiles, Beam startPoint) {
    LinkedList<Beam> beams = new LinkedList<>();
    Set<Beam> beamPaths = new HashSet<>();
    beams.add(startPoint);
    while (!beams.isEmpty()) {
      Beam beam = beams.poll();
      if (beamPaths.contains(beam)) {
        continue;
      }
      beamPaths.add(beam);
      Point movement = beam.point.move(beam.direction.movement);
      if (!tiles.containsKey(movement)) {
        continue;
      }
      Character c = tiles.get(movement);
      if (c == '.') {
        beams.add(new Beam(movement, beam.direction));
      } else if (c == '/') {
        if (beam.direction == Direction.UP) {
          beams.add(new Beam(movement, Direction.RIGHT));
        } else if (beam.direction == Direction.DOWN) {
          beams.add(new Beam(movement, Direction.LEFT));
        } else if (beam.direction == Direction.LEFT) {
          beams.add(new Beam(movement, Direction.DOWN));
        } else {
          beams.add(new Beam(movement, Direction.UP));
        }
      } else if (c == '\\') {
        if (beam.direction == Direction.UP) {
          beams.add(new Beam(movement, Direction.LEFT));
        } else if (beam.direction == Direction.DOWN) {
          beams.add(new Beam(movement, Direction.RIGHT));
        } else if (beam.direction == Direction.LEFT) {
          beams.add(new Beam(movement, Direction.UP));
        } else {
          beams.add(new Beam(movement, Direction.DOWN));
        }
      } else if (c == '-') {
        if (beam.direction == Direction.UP || beam.direction == Direction.DOWN) {
          beams.add(new Beam(movement, Direction.LEFT));
          beams.add(new Beam(movement, Direction.RIGHT));
        } else {
          beams.add(new Beam(movement, beam.direction));
        }
      } else {
        if (beam.direction == Direction.LEFT || beam.direction == Direction.RIGHT) {
          beams.add(new Beam(movement, Direction.UP));
          beams.add(new Beam(movement, Direction.DOWN));
        } else {
          beams.add(new Beam(movement, beam.direction));
        }
      }
    }
    beamPaths.remove(startPoint);
    return beamPaths;
  }

  private static long getEnergizedSpaces(Map<Point, Character> tiles, Beam startPoint) {
    Set<Beam> beams = simulateBeams(tiles, startPoint);
    return beams.stream().map(b -> b.point).collect(Collectors.toSet()).size();
  }

  enum Direction {
    UP(new Point(0, -1)),
    DOWN(new Point(0, 1)),
    LEFT(new Point(-1, 0)),
    RIGHT(new Point(1, 0));

    public final Point movement;

    Direction(Point point) {
      movement = point;
    }
  }

  static class Beam {
    Point point;
    Direction direction;

    public Beam(Point point, Direction direction) {
      this.point = point;
      this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Beam beam)) return false;
      return Objects.equals(point, beam.point) && direction == beam.direction;
    }

    @Override
    public int hashCode() {
      return Objects.hash(point, direction);
    }
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day16.class);
    Map<Point, Character> tiles = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        tiles.put(new Point(x, y), line.charAt(x));
      }
    }
    long yMax = tiles.keySet().stream().mapToLong(Point::getY).max().getAsLong();
    long xMax = tiles.keySet().stream().mapToLong(Point::getX).max().getAsLong();
    long maxCount = 0;
    // left side
    for (long y = 0; y <= yMax; y++) {
      maxCount =
          Math.max(
              getEnergizedSpaces(tiles, new Beam(new Point(-1, y), Direction.RIGHT)), maxCount);
    }
    // right side
    for (long y = 0; y <= yMax; y++) {
      maxCount =
          Math.max(
              getEnergizedSpaces(tiles, new Beam(new Point(xMax + 1, y), Direction.LEFT)),
              maxCount);
    }
    // top side
    for (long x = 0; x <= xMax; x++) {
      maxCount =
          Math.max(getEnergizedSpaces(tiles, new Beam(new Point(x, -1), Direction.DOWN)), maxCount);
    }
    // bottom side
    for (long x = 0; x <= xMax; x++) {
      maxCount =
          Math.max(
              getEnergizedSpaces(tiles, new Beam(new Point(x, yMax + 1), Direction.UP)), maxCount);
    }
    return maxCount;
  }
}
