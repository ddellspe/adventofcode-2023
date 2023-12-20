package net.ddellspe.day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;

public class Day19 {
  private Day19() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day19.class);
    boolean processing = false;
    Map<String, List<Rule>> workflows = new HashMap<>();
    long sum = 0L;
    for (String line : lines) {
      if (!processing) {
        if (line.isBlank()) {
          processing = true;
          continue;
        }
        String name = line.substring(0, line.indexOf("{"));
        List<Rule> rules =
            Arrays.stream(line.substring(line.indexOf("{") + 1, line.length() - 1).split(","))
                .map(Rule::new)
                .toList();
        workflows.put(name, rules);
      } else {
        Map<String, Integer> part =
            Arrays.stream(line.substring(1, line.length() - 1).split(","))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> s[0], s -> Integer.parseInt(s[1])));
        sum += processPart1(part, workflows, "in");
      }
    }
    return sum;
  }

  static long processPart1(
      Map<String, Integer> part, Map<String, List<Rule>> workflows, String workflow) {
    if (workflow.equals("A")) {
      return part.values().stream().reduce(Integer::sum).get();
    } else if (workflow.equals("R")) {
      return 0L;
    } else {
      List<Rule> selectedRule = workflows.get(workflow);
      for (Rule flow : selectedRule) {
        if (flow.isTerminating()) {
          return processPart1(part, workflows, flow.workflow);
        }
        if (Integer.compare(part.get(flow.letter), flow.value) == flow.operand) {
          return processPart1(part, workflows, flow.workflow);
        }
      }
    }
    return 0L;
  }

  static class Rule {
    String letter;
    int operand;
    int value;
    String workflow;

    public Rule(String part) {
      if (part.contains(":")) {
        letter = part.substring(0, 1);
        value = Integer.parseInt(part.substring(2, part.indexOf(":")));
        workflow = part.substring(part.indexOf(":") + 1);
        if (part.contains("<")) {
          operand = -1;
        } else {
          operand = 1;
        }
      } else {
        workflow = part;
      }
    }

    public boolean isTerminating() {
      return letter == null;
    }
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day19.class);
    Map<String, List<Rule>> workflows = new HashMap<>();
    long sum = 0L;
    for (String line : lines) {
      if (line.isBlank()) {
        break;
      }
      String name = line.substring(0, line.indexOf("{"));
      List<Rule> rules =
          Arrays.stream(line.substring(line.indexOf("{") + 1, line.length() - 1).split(","))
              .map(Rule::new)
              .toList();
      workflows.put(name, rules);
    }
    Map<String, Range> part = new HashMap<>();
    part.put("x", new Range(1, 4001));
    part.put("m", new Range(1, 4001));
    part.put("a", new Range(1, 4001));
    part.put("s", new Range(1, 4001));
    return processPart2(part, workflows, "in");
  }

  static class Range {
    int start;
    int end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public long getRangeCount() {
      return end - start;
    }

    public Range getLessRange(int value) {
      if (start >= value) {
        return new Range(0, 0);
      } else {
        if (end >= value) {
          return new Range(start, value);
        } else {
          return new Range(start, end);
        }
      }
    }

    public Range getLessRemaining(int value) {
      if (start >= value) {
        return new Range(start, end);
      } else {
        if (end >= value) {
          return new Range(value, end);
        } else {
          return new Range(0, 0);
        }
      }
    }

    public Range getMoreRange(int value) {
      if (end <= value) {
        return new Range(0, 0);
      } else {
        if (start <= value) {
          return new Range(value + 1, end);
        } else {
          return new Range(start, end);
        }
      }
    }

    public Range getMoreRemaining(int value) {
      if (end <= value) {
        return new Range(start, end);
      } else {
        if (start <= value) {
          return new Range(start, value + 1);
        } else {
          return new Range(0, 0);
        }
      }
    }

    public Range copy() {
      return new Range(start, end);
    }
  }

  static long processPart2(
      Map<String, Range> part, Map<String, List<Rule>> workflows, String workflow) {
    List<Rule> selectedRule = workflows.get(workflow);
    long value = 0L;
    if (workflow.equals("A")) {
      return part.values().stream()
          .mapToLong(Range::getRangeCount)
          .reduce(1L, (prev, curr) -> prev * curr);
    } else if (workflow.equals("R")) {
      return 0L;
    }
    for (Rule flow : selectedRule) {
      Map<String, Range> successParts = new HashMap<>();
      Map<String, Range> failureParts = new HashMap<>();
      if (flow.isTerminating()) {
        value += processPart2(part, workflows, flow.workflow);
      } else if (flow.operand > 0) {
        for (String letter : "xmas".split("")) {
          if (letter.equals(flow.letter)) {
            successParts.put(letter, part.get(letter).getMoreRange(flow.value));
            failureParts.put(letter, part.get(letter).getMoreRemaining(flow.value));
          } else {
            successParts.put(letter, part.get(letter).copy());
            failureParts.put(letter, part.get(letter).copy());
          }
        }
        part = failureParts;
        value += processPart2(successParts, workflows, flow.workflow);
      } else {
        for (String letter : "xmas".split("")) {
          if (letter.equals(flow.letter)) {
            successParts.put(letter, part.get(letter).getLessRange(flow.value));
            failureParts.put(letter, part.get(letter).getLessRemaining(flow.value));
          } else {
            successParts.put(letter, part.get(letter).copy());
            failureParts.put(letter, part.get(letter).copy());
          }
        }
        part = failureParts;
        value += processPart2(successParts, workflows, flow.workflow);
      }
    }
    return value;
  }
}
