package net.ddellspe.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;

public class Day04 {
  private Day04() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day04.class);
    long sum = 0L;
    for (String line : lines) {
      String data = line.split(": ")[1];
      Set<Integer> winners =
          Arrays.stream(data.split("\\|")[0].trim().split(" +"))
              .map(Integer::parseInt)
              .collect(Collectors.toSet());
      Set<Integer> myNums =
          Arrays.stream(data.split("\\|")[1].trim().split(" +"))
              .map(Integer::parseInt)
              .collect(Collectors.toSet());
      long count = myNums.stream().filter(winners::contains).count();
      if (count > 0) {
        sum += (long) Math.pow(2, count - 1);
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day04.class);
    List<Integer> cards = new ArrayList<>();
    cards.add(0);
    for (int i = 1; i < lines.size() + 1; i++) {
      cards.add(1);
    }
    for (String line : lines) {
      int cardNumber = Integer.parseInt(line.split(": ")[0].split(" +")[1]);
      String data = line.split(": ")[1];
      Set<Integer> winners =
          Arrays.stream(data.split("\\|")[0].trim().split(" +"))
              .map(Integer::parseInt)
              .collect(Collectors.toSet());
      Set<Integer> myNums =
          Arrays.stream(data.split("\\|")[1].trim().split(" +"))
              .map(Integer::parseInt)
              .collect(Collectors.toSet());
      long count = myNums.stream().filter(winners::contains).count();
      if (count > 0) {
        int cardCount = cards.get(cardNumber);
        for (int i = 0; i < count; i++) {
          cards.set(cardNumber + i + 1, cards.get(cardNumber + i + 1) + cardCount);
        }
      }
    }
    return cards.stream().reduce(0, Integer::sum).longValue();
  }
}
