package net.ddellspe.day12;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import net.ddellspe.utils.InputUtils;

public class Day12 {
  private Day12() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day12.class);
    long sum = 0L;
    for (String line : lines) {
      String record = line.split(" ")[0];
      List<Integer> groups =
          Arrays.stream(line.split(" ")[1].split(",")).mapToInt(Integer::parseInt).boxed().toList();
      Map<State, Long> memory = new HashMap<>();
      sum += calculateFillers(record, groups, memory);
    }
    return sum;
  }

  public static long calculateFillers(
      String record, List<Integer> groups, Map<State, Long> memory) {
    State st = new State(record, groups);
    if (memory.containsKey(st)) {
      return memory.get(st);
    }
    if (record.isBlank()) {
      return groups.isEmpty() ? 1L : 0L;
    }
    long arrangements = 0L;
    if (record.charAt(0) == '.') {
      arrangements = calculateFillers(record.substring(1), groups, memory);
    } else if (record.charAt(0) == '?') {
      arrangements =
          calculateFillers("." + record.substring(1), groups, memory)
              + calculateFillers("#" + record.substring(1), groups, memory);
    } else {
      if (groups.isEmpty()) {
        return 0L;
      } else {
        int damaged = groups.get(0);
        if (damaged <= record.length()
            && record.chars().limit(damaged).allMatch(c -> c == '#' || c == '?')) {
          List<Integer> newGroups = groups.subList(1, groups.size());
          if (damaged == record.length()) {
            arrangements = newGroups.isEmpty() ? 1 : 0;
          } else if (record.charAt(damaged) == '.') {
            arrangements = calculateFillers(record.substring(damaged + 1), newGroups, memory);
          } else if (record.charAt(damaged) == '?') {
            arrangements = calculateFillers('.' + record.substring(damaged + 1), newGroups, memory);
          }
        }
      }
    }
    memory.put(st, arrangements);
    return arrangements;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day12.class);
    long sum = 0L;
    Map<State, Long> memory = new HashMap<>();
    for (String line : lines) {
      String record =
          String.join("?", IntStream.range(0, 5).mapToObj(i -> line.split(" ")[0]).toList());
      List<Integer> groups =
          Arrays.stream(
                  String.join(",", IntStream.range(0, 5).mapToObj(i -> line.split(" ")[1]).toList())
                      .split(","))
              .mapToInt(Integer::parseInt)
              .boxed()
              .toList();
      sum += calculateFillers(record, groups, memory);
    }
    return sum;
  }

  public static class State {
    String record;
    List<Integer> groups;

    public State(String record, List<Integer> groups) {
      this.record = record;
      this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      State state = (State) o;
      return Objects.equals(record, state.record) && Objects.equals(groups, state.groups);
    }

    @Override
    public int hashCode() {
      return Objects.hash(record, groups);
    }
  }
}
