package net.ddellspe.day09;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import net.ddellspe.utils.InputUtils;

public class Day09 {
  private Day09() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day09.class);
    long sum = 0L;
    for (String line : lines) {
      Map<Integer, List<Long>> numberMap = new HashMap<>();
      int zeroLevel = -1;
      int level = 0;
      List<Long> numbers =
          Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).boxed().toList();
      numberMap.put(level, numbers);
      do {
        List<Long> finalNumbers = numberMap.get(level);
        if (IntStream.range(1, finalNumbers.size())
            .allMatch(i -> finalNumbers.get(i) - finalNumbers.get(i - 1) == 0)) {
          zeroLevel = level;
        } else {
          level++;
          numbers =
              IntStream.range(1, finalNumbers.size())
                  .mapToLong(i -> finalNumbers.get(i) - finalNumbers.get(i - 1))
                  .boxed()
                  .toList();
          numberMap.put(level, numbers);
        }
      } while (zeroLevel < 0);
      long diffValue = 0L;
      for (int lvl = zeroLevel; lvl >= 0; lvl--) {
        diffValue += numberMap.get(lvl).get(numberMap.get(lvl).size() - 1);
      }
      sum += diffValue;
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day09.class);
    long sum = 0L;
    for (String line : lines) {
      Map<Integer, List<Long>> numberMap = new HashMap<>();
      int zeroLevel = -1;
      int level = 0;
      List<Long> numbers =
          Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).boxed().toList();
      numberMap.put(level, numbers);
      do {
        List<Long> finalNumbers = numberMap.get(level);
        if (IntStream.range(1, finalNumbers.size())
            .allMatch(i -> finalNumbers.get(i) - finalNumbers.get(i - 1) == 0)) {
          zeroLevel = level;
        } else {
          level++;
          numbers =
              IntStream.range(1, finalNumbers.size())
                  .mapToLong(i -> finalNumbers.get(i) - finalNumbers.get(i - 1))
                  .boxed()
                  .toList();
          numberMap.put(level, numbers);
        }
      } while (zeroLevel < 0);
      long diffValue = 0L;
      for (int lvl = zeroLevel; lvl >= 0; lvl--) {
        diffValue = numberMap.get(lvl).get(0) - diffValue;
      }
      sum += diffValue;
    }
    return sum;
  }
}
