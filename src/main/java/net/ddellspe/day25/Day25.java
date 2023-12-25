package net.ddellspe.day25;

import java.util.*;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;

public class Day25 {
  private Day25() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day25.class);
    Map<String, Set<String>> fullGraph = new HashMap<>();
    Set<Connection> connections = new HashSet<>();
    for (String line : lines) {
      String[] split = line.split(": ");
      String src = split[0];
      for (String dest : split[1].split(" ")) {
        fullGraph.putIfAbsent(src, new HashSet<>());
        fullGraph.putIfAbsent(dest, new HashSet<>());
        fullGraph.get(src).add(dest);
        fullGraph.get(dest).add(src);
        connections.add(new Connection(src, dest));
      }
    }
    List<Connection> allConnections = connections.stream().toList();
    long product = -1;
    for (int i = 0; i < allConnections.size() - 2; i++) {
      if (product > 0) {
        break;
      }
      for (int j = i + 1; j < allConnections.size() - 1; j++) {
        if (product > 0) {
          break;
        }
        for (int k = j + 1; k < allConnections.size(); k++) {
          if (product > 0) {
            break;
          }
          Map<String, Set<String>> nodes =
              fullGraph.entrySet().stream()
                  .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
          for (int l : List.of(i, j, k)) {
            Connection conn = allConnections.get(l);
            nodes.get(conn.source).remove(conn.destination);
            nodes.get(conn.destination).remove(conn.source);
          }
          List<Integer> groupSizes = new ArrayList<>();
          while (!nodes.isEmpty()) {
            Set<String> done = new HashSet<>();
            LinkedList<String> processing = new LinkedList<>();
            Iterator<String> nodeIterator = nodes.keySet().stream().iterator();
            if (nodeIterator.hasNext()) {
              processing.add(nodeIterator.next());
            }
            while (!processing.isEmpty()) {
              String node = processing.poll();
              if (!nodes.containsKey(node)) {
                continue;
              }
              for (String val : nodes.get(node)) {
                nodes.get(val).remove(node);
                processing.add(val);
              }
              nodes.remove(node);
              done.add(node);
            }
            groupSizes.add(done.size());
          }
          if (groupSizes.size() == 2) {
            product = (long) groupSizes.get(0) * (long) groupSizes.get(1);
          }
        }
      }
    }
    return product;
  }

  static class Connection {
    String source;
    String destination;

    public Connection(String source, String destination) {
      this.source = source;
      this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Connection that = (Connection) o;
      return Objects.equals(source, that.source) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
      return Objects.hash(source, destination);
    }
  }
}
