package net.ddellspe.day06;

import java.util.Arrays;
import java.util.List;
import net.ddellspe.utils.InputUtils;

public class Day06 {
  private Day06() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day06.class);
    List<Long> times =
        Arrays.stream(lines.get(0).split(":")[1].trim().split("[ ]+"))
            .map(Long::parseLong)
            .toList();
    List<Long> records =
        Arrays.stream(lines.get(1).split(":")[1].trim().split("[ ]+"))
            .map(Long::parseLong)
            .toList();
    long result = 1L;
    for (int i = 0; i < times.size(); i++) {
      long time = times.get(i);
      long record = records.get(i);
      long count = 0;
      for (long start = 0; start <= time; start++) {
        if (start * (time - start) > record) {
          count++;
        }
      }
      result *= count;
    }
    return result;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day06.class);
    long time = Long.parseLong(lines.get(0).split(":")[1].trim().replace(" ", ""));
    long record = Long.parseLong(lines.get(1).split(":")[1].trim().replace(" ", ""));
    long result = 0;
    for (long start = 0; start <= time; start++) {
      if (start * (time - start) > record) {
        result++;
      }
    }
    return result;
  }
}
