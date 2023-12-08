package net.ddellspe.day08;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.MathUtils;

public class Day08 {
  private Day08() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day08.class);
    List<Integer> moves =
        Arrays.stream(lines.get(0).split("")).mapToInt(v -> v.equals("L") ? 0 : 1).boxed().toList();
    Map<String, List<String>> network = new HashMap<>();
    for (int i = 2; i < lines.size(); i++) {
      String data = lines.get(i);
      network.put(
          data.split("=")[0].trim(),
          Arrays.stream(data.split("=")[1].replace("(", "").replace(")", "").trim().split(","))
              .map(String::trim)
              .toList());
    }
    String node = "AAA";
    int index = 0;
    long count = 0;
    while (!node.equals("ZZZ")) {
      node = network.get(node).get(moves.get(index));
      index++;
      count++;
      index %= moves.size();
    }
    return count;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day08.class);
    List<Integer> moves =
        Arrays.stream(lines.get(0).split("")).mapToInt(v -> v.equals("L") ? 0 : 1).boxed().toList();
    Map<String, List<String>> network = new HashMap<>();
    for (int i = 2; i < lines.size(); i++) {
      String data = lines.get(i);
      network.put(
          data.split("=")[0].trim(),
          Arrays.stream(data.split("=")[1].replace("(", "").replace(")", "").trim().split(","))
              .map(String::trim)
              .toList());
    }
    List<String> nodes = network.keySet().stream().filter(s -> s.endsWith("A")).toList();
    Map<String, Long> moveCounts = nodes.stream().collect(Collectors.toMap(v -> v, v -> 0L));
    for (String node : nodes) {
      String tempNode = node;
      int index = 0;
      long count = 0;
      while (!tempNode.endsWith("Z")) {
        tempNode = network.get(tempNode).get(moves.get(index));
        index++;
        count++;
        index %= moves.size();
      }
      moveCounts.put(node, count);
    }
    return MathUtils.lcm(moveCounts.values().stream().toList());
  }
}
