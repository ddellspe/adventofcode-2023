package net.ddellspe.day17;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day17 {
  private Day17() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day17.class);
    Map<Point, Integer> heatLoss = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        heatLoss.put(new Point(x, y), Integer.parseInt(String.valueOf(line.charAt(x))));
      }
    }
    int maxX =
        heatLoss.keySet().stream()
            .mapToLong(Point::getX)
            .mapToInt(Math::toIntExact)
            .max()
            .getAsInt();
    int maxY =
        heatLoss.keySet().stream()
            .mapToLong(Point::getY)
            .mapToInt(Math::toIntExact)
            .max()
            .getAsInt();
    Point start = new Point(0, 0);
    Point end = new Point(maxX, maxY);
    return calculateHeatCost(heatLoss, start, end, 0, 3);
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day17.class);
    Map<Point, Integer> heatLoss = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        heatLoss.put(new Point(x, y), Integer.parseInt(String.valueOf(line.charAt(x))));
      }
    }
    int maxX =
        heatLoss.keySet().stream()
            .mapToLong(Point::getX)
            .mapToInt(Math::toIntExact)
            .max()
            .getAsInt();
    int maxY =
        heatLoss.keySet().stream()
            .mapToLong(Point::getY)
            .mapToInt(Math::toIntExact)
            .max()
            .getAsInt();
    Point start = new Point(0, 0);
    Point end = new Point(maxX, maxY);
    return calculateHeatCost(heatLoss, start, end, 4, 10);
  }

  public static long calculateHeatCost(
      Map<Point, Integer> heatLosses,
      Point start,
      Point end,
      int minimumStreak,
      int maximumStreak) {
    PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(s -> s.cost));
    queue.add(
        new State(
            start.move(Direction.RIGHT.movement),
            Direction.RIGHT,
            1,
            heatLosses.get(start.move(Direction.RIGHT.movement))));
    queue.add(
        new State(
            start.move(Direction.DOWN.movement),
            Direction.DOWN,
            1,
            heatLosses.get(start.move(Direction.DOWN.movement))));
    Set<State> visited = new HashSet<>();
    long minCost = Long.MAX_VALUE;
    while (!queue.isEmpty()) {
      State state = queue.poll();
      if (state.currentPoint.equals(end) && minimumStreak <= state.streak) {
        return state.cost;
      }
      if (visited.contains(state.getCostFreeState())) {
        continue;
      }
      visited.add(state.getCostFreeState());
      if (state.streak < maximumStreak
          && heatLosses.containsKey(state.currentPoint.move(state.direction.movement))) {
        Point newPosition = state.currentPoint.move(state.direction.movement);
        queue.add(
            new State(
                newPosition,
                state.direction,
                state.streak + 1,
                state.cost + heatLosses.get(newPosition)));
      }
      if (minimumStreak <= state.streak) {
        final Point leftPoint;
        final Direction leftDirection;
        final Point rightPoint;
        final Direction rightDirection;
        switch (state.direction) {
          case UP -> {
            leftDirection = Direction.LEFT;
            leftPoint = state.currentPoint.move(leftDirection.movement);
            rightDirection = Direction.RIGHT;
            rightPoint = state.currentPoint.move(rightDirection.movement);
          }
          case DOWN -> {
            leftDirection = Direction.RIGHT;
            leftPoint = state.currentPoint.move(leftDirection.movement);
            rightDirection = Direction.LEFT;
            rightPoint = state.currentPoint.move(rightDirection.movement);
          }
          case LEFT -> {
            leftDirection = Direction.DOWN;
            leftPoint = state.currentPoint.move(leftDirection.movement);
            rightDirection = Direction.UP;
            rightPoint = state.currentPoint.move(rightDirection.movement);
          }
          default -> {
            leftDirection = Direction.UP;
            leftPoint = state.currentPoint.move(leftDirection.movement);
            rightDirection = Direction.DOWN;
            rightPoint = state.currentPoint.move(rightDirection.movement);
          }
        }
        if (heatLosses.containsKey(leftPoint)) {
          queue.add(new State(leftPoint, leftDirection, 1, state.cost + heatLosses.get(leftPoint)));
        }
        if (heatLosses.containsKey(rightPoint)) {
          queue.add(
              new State(rightPoint, rightDirection, 1, state.cost + heatLosses.get(rightPoint)));
        }
      }
    }
    return minCost;
  }

  static class State {
    Point currentPoint;
    Direction direction;
    int streak;
    long cost;

    public State(Point point, Direction direction, int streak, long cost) {
      this.currentPoint = point;
      this.direction = direction;
      this.streak = streak;
      this.cost = cost;
    }

    public State getCostFreeState() {
      return new State(currentPoint, direction, streak, 0L);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof State state)) return false;
      return cost == state.cost
          && streak == state.streak
          && Objects.equals(currentPoint, state.currentPoint)
          && direction == state.direction;
    }

    @Override
    public int hashCode() {
      return Objects.hash(currentPoint, direction, cost, streak);
    }
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
}
