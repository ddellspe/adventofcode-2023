package net.ddellspe.day23;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day23 {
  private Day23() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day23.class);
    Set<Point> paths = new HashSet<>();
    Map<Point, String> slopes = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          continue;
        }
        Point pt = new Point(x, y);
        paths.add(pt);
        if (">v".contains(String.valueOf(line.charAt(x)))) {
          slopes.put(pt, String.valueOf(line.charAt(x)));
        }
      }
    }
    Point endPoint = paths.stream().filter(p -> p.getY() == (lines.size() - 1)).findFirst().get();
    Point startPoint = paths.stream().filter(p -> p.getY() == 0).findFirst().get();
    Set<Point> visits = new HashSet<>();
    visits.add(startPoint);
    LinkedList<State> queue = new LinkedList<>();
    queue.add(new State(startPoint, visits, visits.size()));
    long longest = 0L;
    Map<Point, Long> best = new HashMap<>();
    while (!queue.isEmpty()) {
      State currentState = queue.poll();
      if (currentState.pt.equals(endPoint)) {
        longest = Math.max(longest, currentState.visited.size());
        continue;
      }
      best.put(currentState.pt, (long) currentState.visited.size());
      Point pt = currentState.pt;
      if (slopes.containsKey(pt)) {
        Point newPoint;
        if (slopes.get(pt).equals(">")) {
          newPoint = pt.move(new Point(1, 0));
        } else {
          newPoint = pt.move(new Point(0, 1));
        }
        if (!currentState.visited.contains(newPoint)) {
          Set<Point> visited = new HashSet<>(currentState.visited);
          visited.add(newPoint);
          queue.add(new State(newPoint, visited, visited.size()));
        }
      } else {
        for (Point newPoint : pt.getDirectNeighbors()) {
          if (paths.contains(newPoint) && !currentState.visited.contains(newPoint)) {
            Set<Point> visited = new HashSet<>(currentState.visited);
            visited.add(newPoint);
            queue.add(new State(newPoint, visited, visited.size()));
          }
        }
      }
    }
    return longest - 1;
  }

  static class State {
    Point pt;
    Set<Point> visited;
    int distance;

    public State(Point pt, Set<Point> visited, int distance) {
      this.pt = pt;
      this.visited = visited;
      this.distance = distance;
    }
  }

  static class Edge {
    Point pt;
    int distance;

    public Edge(Point pt, int distance) {
      this.pt = pt;
      this.distance = distance;
    }

    public void extend(Edge e) {
      pt = e.pt;
      distance += e.distance;
    }
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day23.class);
    Set<Point> paths = new HashSet<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          continue;
        }
        Point pt = new Point(x, y);
        paths.add(pt);
      }
    }
    Map<Point, List<Edge>> graph = new HashMap<>();
    for (Point pt : paths) {
      graph.put(
          pt,
          pt.getDirectNeighbors().stream()
              .filter(paths::contains)
              .map(p -> new Edge(p, 1))
              .toList());
    }
    while (true) {
      boolean merged = false;
      for (Map.Entry<Point, List<Edge>> entry : graph.entrySet()) {
        if (entry.getValue().size() == 2) {
          Edge edge1 = entry.getValue().get(0);
          Edge edge2 = entry.getValue().get(1);
          graph.get(edge1.pt).stream()
              .filter(e -> e.pt.equals(entry.getKey()))
              .forEach(e -> e.extend(edge2));
          graph.get(edge2.pt).stream()
              .filter(e -> e.pt.equals(entry.getKey()))
              .forEach(e -> e.extend(edge1));
          graph.remove(entry.getKey());
          merged = true;
          break;
        }
      }
      if (!merged) {
        break;
      }
    }
    Point endPoint = paths.stream().filter(p -> p.getY() == (lines.size() - 1)).findFirst().get();
    Point startPoint = paths.stream().filter(p -> p.getY() == 0).findFirst().get();
    Set<Point> visits = new HashSet<>();
    visits.add(startPoint);
    LinkedList<State> queue = new LinkedList<>();
    queue.add(new State(startPoint, visits, 0));
    long longest = 0L;
    Map<Point, Long> best = new HashMap<>();
    while (!queue.isEmpty()) {
      State currentState = queue.poll();
      if (currentState.pt.equals(endPoint)) {
        longest = Math.max(longest, currentState.distance);
        continue;
      }
      best.put(currentState.pt, (long) currentState.visited.size());
      Point pt = currentState.pt;
      for (Edge newPoint : graph.get(pt)) {
        if (!currentState.visited.contains(newPoint.pt)) {
          Set<Point> visited = new HashSet<>(currentState.visited);
          visited.add(newPoint.pt);
          queue.add(new State(newPoint.pt, visited, currentState.distance + newPoint.distance));
        }
      }
    }
    return longest;
  }
}
