package net.ddellspe.day20;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.MathUtils;

public class Day20 {
  private Day20() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day20.class);
    Map<String, List<String>> srcToDest = new HashMap<>();
    List<String> flipFlops = new ArrayList<>();
    List<String> conjunctions = new ArrayList<>();
    for (String line : lines) {
      String[] data = line.split(" -> ");
      String src = data[0];
      if (!src.equals("broadcaster")) {
        if (src.startsWith("%")) {
          flipFlops.add(src.substring(1));
        } else {
          conjunctions.add(src.substring(1));
        }
        src = src.substring(1);
      }
      srcToDest.put(src, Arrays.stream(data[1].split(", ")).toList());
    }
    Map<String, Module> modules = new HashMap<>();
    for (String conjunction : conjunctions) {
      modules.put(
          conjunction,
          new Conjunction(
              srcToDest.entrySet().stream()
                  .filter(e -> e.getValue().contains(conjunction))
                  .map(Map.Entry::getKey)
                  .toList(),
              srcToDest.get(conjunction)));
    }
    for (String flipFlop : flipFlops) {
      modules.put(flipFlop, new FlipFlop(srcToDest.get(flipFlop)));
    }
    List<Pulse> pulses = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      LinkedList<Pulse> queue = new LinkedList<>();
      pulses.add(new Pulse(PulseType.LOW, "button", "broadcaster"));
      for (String destination : srcToDest.get("broadcaster")) {
        queue.add(new Pulse(PulseType.LOW, "broadcaster", destination));
      }
      while (!queue.isEmpty()) {
        Pulse pulse = queue.poll();
        pulses.add(pulse);
        if (modules.containsKey(pulse.destination)) {
          List<Pulse> newPulses = modules.get(pulse.destination).sendPulse(pulse);
          queue.addAll(newPulses);
        }
      }
    }
    long highCount = pulses.stream().filter(Pulse::isHigh).count();
    long lowCount = pulses.size() - highCount;
    return lowCount * highCount;
  }

  static enum PulseType {
    HIGH,
    LOW
  }

  static class Pulse {
    PulseType type;
    String source;
    String destination;

    public Pulse(PulseType type, String source, String destination) {
      this.type = type;
      this.source = source;
      this.destination = destination;
    }

    public boolean isHigh() {
      return type == PulseType.HIGH;
    }
  }

  static interface Module {
    public List<Pulse> sendPulse(Pulse pulse);
  }

  static class FlipFlop implements Module {
    boolean on;
    List<String> destinations;

    public FlipFlop(List<String> destinations) {
      on = false;
      this.destinations = destinations;
    }

    @Override
    public List<Pulse> sendPulse(Pulse pulse) {
      if (!pulse.isHigh()) {
        on = !on;
        if (on) {
          return destinations.stream()
              .map(d -> new Pulse(PulseType.HIGH, pulse.destination, d))
              .toList();
        } else {
          return destinations.stream()
              .map(d -> new Pulse(PulseType.LOW, pulse.destination, d))
              .toList();
        }
      } else {
        return List.of();
      }
    }
  }

  static class Conjunction implements Module {
    List<String> inputs;
    List<Pulse> pulses;
    List<String> destinations;

    public Conjunction(List<String> inputs, List<String> destinations) {
      this.inputs = inputs;
      pulses = new ArrayList<>();
      for (String input : inputs) {
        pulses.add(new Pulse(PulseType.LOW, input, input));
      }
      this.destinations = destinations;
    }

    @Override
    public List<Pulse> sendPulse(Pulse pulse) {
      pulses.set(inputs.indexOf(pulse.source), pulse);
      if (pulses.stream().allMatch(Pulse::isHigh)) {
        return destinations.stream()
            .map(d -> new Pulse(PulseType.LOW, pulse.destination, d))
            .toList();
      } else {
        return destinations.stream()
            .map(d -> new Pulse(PulseType.HIGH, pulse.destination, d))
            .toList();
      }
    }
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day20.class);
    Map<String, List<String>> srcToDest = new HashMap<>();
    List<String> flipFlops = new ArrayList<>();
    List<String> conjunctions = new ArrayList<>();
    for (String line : lines) {
      String[] data = line.split(" -> ");
      String src = data[0];
      if (!src.equals("broadcaster")) {
        if (src.startsWith("%")) {
          flipFlops.add(src.substring(1));
        } else {
          conjunctions.add(src.substring(1));
        }
        src = src.substring(1);
      }
      srcToDest.put(src, Arrays.stream(data[1].split(", ")).toList());
    }
    Map<String, Module> modules = new HashMap<>();
    for (String conjunction : conjunctions) {
      modules.put(
          conjunction,
          new Conjunction(
              srcToDest.entrySet().stream()
                  .filter(e -> e.getValue().contains(conjunction))
                  .map(Map.Entry::getKey)
                  .toList(),
              srcToDest.get(conjunction)));
    }
    for (String flipFlop : flipFlops) {
      modules.put(flipFlop, new FlipFlop(srcToDest.get(flipFlop)));
    }
    String targetConj =
        srcToDest.entrySet().stream()
            .filter(e -> e.getValue().contains("rx"))
            .map(Map.Entry::getKey)
            .findFirst()
            .get();
    Module module = modules.get(targetConj);
    Map<String, Long> cutoffs = new HashMap<>();
    for (String input : ((Conjunction) module).inputs) {
      cutoffs.put(input, 0L);
    }
    long buttons = 0L;
    while (cutoffs.values().stream().reduce(1L, (p, c) -> p * c) == 0L) {
      buttons++;
      LinkedList<Pulse> queue = new LinkedList<>();
      for (String destination : srcToDest.get("broadcaster")) {
        queue.add(new Pulse(PulseType.LOW, "broadcaster", destination));
      }
      while (!queue.isEmpty()) {
        Pulse pulse = queue.poll();
        if (pulse.destination.equals(targetConj) && pulse.isHigh()) {
          cutoffs.put(pulse.source, buttons - cutoffs.get(pulse.source));
        }
        if (modules.containsKey(pulse.destination)) {
          List<Pulse> newPulses = modules.get(pulse.destination).sendPulse(pulse);
          queue.addAll(newPulses);
        }
      }
    }
    return MathUtils.lcm(cutoffs.values().stream().toList());
  }
}
