package net.ddellspe.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import net.ddellspe.utils.InputUtils;

public class Day15 {
  private Day15() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day15.class);
    long sum = 0L;
    List<String> words = Arrays.stream(lines.get(0).split(",")).toList();
    for (String word : words) {
      sum += word.chars().reduce(0, (total, element) -> ((total + element) * 17) % 256);
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day15.class);
    List<List<Lens>> boxes =
        new ArrayList<>(IntStream.range(0, 256).mapToObj(i -> new ArrayList<Lens>()).toList());
    List<String> words = Arrays.stream(lines.get(0).split(",")).toList();
    for (String word : words) {
      Lens lens = new Lens(word);
      int index = lens.label.chars().reduce(0, (total, element) -> ((total + element) * 17) % 256);
      List<Lens> lenses = boxes.get(index);
      Optional<Lens> alignedLens =
          lenses.stream().filter(l -> l.label.equals(lens.label)).findFirst();
      if (alignedLens.isPresent()) {
        int rmIndex = lenses.indexOf(alignedLens.get());
        lenses.remove(rmIndex);
        if (lens.hasFocalLength()) {
          lenses.add(rmIndex, lens);
        }
      } else {
        if (lens.hasFocalLength()) {
          lenses.add(lens);
        }
      }
      boxes.set(index, lenses);
    }
    long sum = 0L;
    for (int box = 0; box < 256; box++) {
      List<Lens> lenses = boxes.get(box);
      for (int pos = 0; pos < lenses.size(); pos++) {
        sum += (long) (box + 1) * (pos + 1) * lenses.get(pos).focalLength;
      }
    }
    return sum;
  }

  private static class Lens {
    String label;
    int focalLength;

    public Lens(String data) {
      label = data.contains("=") ? data.split("=")[0] : data.split("-")[0];
      focalLength = data.contains("=") ? Integer.parseInt(data.split("=")[1]) : 0;
    }

    public boolean hasFocalLength() {
      return focalLength != 0;
    }
  }
}
