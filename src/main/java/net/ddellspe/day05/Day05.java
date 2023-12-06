package net.ddellspe.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import net.ddellspe.utils.InputUtils;

public class Day05 {
  private Day05() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day05.class);
    List<Long> seeds = new ArrayList<>();
    List<FullMap> maps = new ArrayList<>();

    FullMap current = null;
    for (String line : lines) {
      if (line.isEmpty()) {
        if (current != null) {
          maps.add(current);
        }
        current = new FullMap();
        continue;
      }
      if (line.startsWith("seeds")) {
        seeds =
            Arrays.stream(line.split(":")[1].trim().split("[ ]+")).map(Long::parseLong).toList();
        continue;
      } else if (line.contains(":")) {
        continue;
      }
      Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
      current.addMap(values);
    }
    maps.add(current);

    long minLocation = Long.MAX_VALUE;
    for (long seed : seeds) {
      long result = seed;
      for (FullMap map : maps) {
        result = map.map(result);
      }
      if (result < minLocation) {
        minLocation = result;
      }
    }
    return minLocation;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day05.class);
    List<Long> seeds = new ArrayList<>();
    TreeSet<Range> ranges = new TreeSet<>();
    List<FullMap> maps = new ArrayList<>();

    FullMap current = null;
    for (String line : lines) {
      if (line.isEmpty()) {
        if (current != null) {
          maps.add(current);
        }
        current = new FullMap();
        continue;
      }
      if (line.startsWith("seeds")) {
        seeds =
            Arrays.stream(line.split(":")[1].trim().split("[ ]+")).map(Long::parseLong).toList();
        continue;
      } else if (line.contains(":")) {
        continue;
      }
      Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
      current.addMap(values);
    }
    maps.add(current);

    for (int i = 0; i < seeds.size(); i += 2) {
      ranges.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1));
    }
    mergeRanges(ranges);
    for (FullMap map : maps) {
      ranges = map.map(ranges);
    }
    return ranges.first().start;
  }

  private static void mergeRanges(TreeSet<Range> ranges) {
    Range prev = null;
    for (Iterator<Range> it = ranges.iterator(); it.hasNext(); ) {
      Range range = it.next();
      if (prev == null) {
        prev = range;
        continue;
      }
      if (prev.overlapOrAdjacent(range)) {
        prev.start = Math.min(prev.start, range.start);
        prev.end = Math.max(prev.end, range.end);
        it.remove();
      } else {
        prev = range;
      }
    }
  }

  private static class FullMap {
    private final List<RangeMap> maps = new ArrayList<>();

    public void addMap(Long[] specification) {
      maps.add(new RangeMap(specification[0], specification[1], specification[2]));
    }

    public long map(long source) {
      for (RangeMap map : maps) {
        Long candidate = map.map(source);
        if (candidate != null) {
          return candidate;
        }
      }
      return source;
    }

    public TreeSet<Range> map(TreeSet<Range> ranges) {
      TreeSet<Range> mapped = new TreeSet<>();
      TreeSet<Range> unmapped = ranges;
      for (RangeMap map : maps) {
        TreeSet<Range> step = new TreeSet<>();
        map.map(unmapped, step, mapped);
        unmapped = step;
      }
      mapped.addAll(unmapped);
      mergeRanges(mapped);
      return mapped;
    }
  }

  private static class RangeMap {
    private final Range source;
    private final long targetStart;

    public RangeMap(long targetStart, long sourceStart, long length) {
      source = new Range(sourceStart, sourceStart + length - 1);
      this.targetStart = targetStart;
    }

    public Long map(long value) {
      if (source.contains(value)) {
        return targetStart + (value - source.start);
      }
      return null;
    }

    public void map(TreeSet<Range> in, TreeSet<Range> unmapped, TreeSet<Range> mapped) {
      for (Range range : in) {
        if (source.end < range.start || source.start > range.end) {
          unmapped.add(range);
          continue;
        }
        if (range.start < source.start) {
          unmapped.add(new Range(range.start, source.start - 1));
        }
        if (range.end > source.end) {
          unmapped.add(new Range(source.end + 1, range.end));
        }
        long mappedStart = targetStart + (Math.max(source.start, range.start) - source.start);
        long mappedEnd = targetStart + (Math.min(source.end, range.end) - source.start);
        mapped.add(new Range(mappedStart, mappedEnd));
      }
    }
  }

  static class Range implements Comparable<Range> {
    long start;
    long end;

    public Range(long start, long end) {
      this.start = start;
      this.end = end;
    }

    public boolean overlapOrAdjacent(Range range) {
      return range.contains(start) || range.contains(end + 1) || contains(range.start);
    }

    public boolean contains(long value) {
      return value >= start && value <= end;
    }

    @Override
    public int compareTo(Range o) {
      long r = start - o.start;
      if (r == 0) {
        r = end - o.end;
      }
      if (r < 0) {
        return -1;
      } else if (r > 0) {
        return 1;
      } else {
        return 0;
      }
    }
  }
}
