package net.ddellspe.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;

public class Day05 {
  private Day05() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day05.class);
    Set<Long> seeds = new HashSet<>();
    List<MappingRange> seedSoilMap = new ArrayList<>();
    List<MappingRange> soilFertilizerMap = new ArrayList<>();
    List<MappingRange> fertilizerWaterMap = new ArrayList<>();
    List<MappingRange> waterLightMap = new ArrayList<>();
    List<MappingRange> lightTemperatureMap = new ArrayList<>();
    List<MappingRange> temperatureHumidityMap = new ArrayList<>();
    List<MappingRange> humidityLocationMap = new ArrayList<>();
    int section = 0;
    for (String line : lines) {
      if (line.isEmpty()) {
        continue;
      }
      if (line.startsWith("seeds")) {
        seeds =
            Arrays.stream(line.split(":")[1].trim().split("[ ]+"))
                .map(Long::parseLong)
                .collect(Collectors.toSet());
      } else if (line.startsWith("humidity-to-location") || section == 7) {
        if (section != 7) {
          section = 7;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        humidityLocationMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("temperature-to-humidity") || section == 6) {
        if (section != 6) {
          section = 6;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        temperatureHumidityMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("light-to-temperature") || section == 5) {
        if (section != 5) {
          section = 5;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        lightTemperatureMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("water-to-light") || section == 4) {
        if (section != 4) {
          section = 4;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        waterLightMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("fertilizer-to-water") || section == 3) {
        if (section != 3) {
          section = 3;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        fertilizerWaterMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("soil-to-fertilizer") || section == 2) {
        if (section != 2) {
          section = 2;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        soilFertilizerMap.add(new MappingRange(values[0], values[1], values[2]));
      } else {
        if (section != 1) {
          section = 1;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        seedSoilMap.add(new MappingRange(values[0], values[1], values[2]));
      }
    }
    long minLocation = Long.MAX_VALUE;
    for (Long seed : seeds) {
      long soil =
          seedSoilMap.stream()
              .filter(range -> range.inRange(seed))
              .findFirst()
              .map(mappingRange -> mappingRange.process(seed))
              .orElse(seed);
      long fertilizer =
          soilFertilizerMap.stream()
              .filter(range -> range.inRange(soil))
              .findFirst()
              .map(mappingRange -> mappingRange.process(soil))
              .orElse(soil);
      long water =
          fertilizerWaterMap.stream()
              .filter(range -> range.inRange(fertilizer))
              .findFirst()
              .map(mappingRange -> mappingRange.process(fertilizer))
              .orElse(fertilizer);
      long light =
          waterLightMap.stream()
              .filter(range -> range.inRange(water))
              .findFirst()
              .map(mappingRange -> mappingRange.process(water))
              .orElse(water);
      long temperature =
          lightTemperatureMap.stream()
              .filter(range -> range.inRange(light))
              .findFirst()
              .map(mappingRange -> mappingRange.process(light))
              .orElse(light);
      long humidity =
          temperatureHumidityMap.stream()
              .filter(range -> range.inRange(temperature))
              .findFirst()
              .map(mappingRange -> mappingRange.process(temperature))
              .orElse(temperature);
      long location =
          humidityLocationMap.stream()
              .filter(range -> range.inRange(humidity))
              .findFirst()
              .map(mappingRange -> mappingRange.process(humidity))
              .orElse(humidity);
      if (location < minLocation) {
        minLocation = location;
      }
    }
    return minLocation;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day05.class);
    List<Long> seeds = new ArrayList<>();
    List<MappingRange> seedSoilMap = new ArrayList<>();
    List<MappingRange> soilFertilizerMap = new ArrayList<>();
    List<MappingRange> fertilizerWaterMap = new ArrayList<>();
    List<MappingRange> waterLightMap = new ArrayList<>();
    List<MappingRange> lightTemperatureMap = new ArrayList<>();
    List<MappingRange> temperatureHumidityMap = new ArrayList<>();
    List<MappingRange> humidityLocationMap = new ArrayList<>();
    int section = 0;
    for (String line : lines) {
      if (line.isEmpty()) {
        continue;
      }
      if (line.startsWith("seeds")) {
        seeds =
            Arrays.stream(line.split(":")[1].trim().split("[ ]+"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
      } else if (line.startsWith("humidity-to-location") || section == 7) {
        if (section != 7) {
          section = 7;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        humidityLocationMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("temperature-to-humidity") || section == 6) {
        if (section != 6) {
          section = 6;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        temperatureHumidityMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("light-to-temperature") || section == 5) {
        if (section != 5) {
          section = 5;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        lightTemperatureMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("water-to-light") || section == 4) {
        if (section != 4) {
          section = 4;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        waterLightMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("fertilizer-to-water") || section == 3) {
        if (section != 3) {
          section = 3;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        fertilizerWaterMap.add(new MappingRange(values[0], values[1], values[2]));
      } else if (line.startsWith("soil-to-fertilizer") || section == 2) {
        if (section != 2) {
          section = 2;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        soilFertilizerMap.add(new MappingRange(values[0], values[1], values[2]));
      } else {
        System.out.println(line);
        System.out.println(section);
        if (section != 1) {
          section = 1;
          continue;
        }
        Long[] values = Arrays.stream(line.split("[ ]+")).map(Long::parseLong).toArray(Long[]::new);
        seedSoilMap.add(new MappingRange(values[0], values[1], values[2]));
      }
    }
    long minLocation = Long.MAX_VALUE;
    for (int i = 0; i < seeds.size(); i += 2) {
      for (long seedVal = seeds.get(i); seedVal < seeds.get(i) + seeds.get(i + 1); seedVal++) {
        final long seed = seedVal;
        long soil =
            seedSoilMap.stream()
                .filter(range -> range.inRange(seed))
                .findFirst()
                .map(mappingRange -> mappingRange.process(seed))
                .orElse(seed);
        long fertilizer =
            soilFertilizerMap.stream()
                .filter(range -> range.inRange(soil))
                .findFirst()
                .map(mappingRange -> mappingRange.process(soil))
                .orElse(soil);
        long water =
            fertilizerWaterMap.stream()
                .filter(range -> range.inRange(fertilizer))
                .findFirst()
                .map(mappingRange -> mappingRange.process(fertilizer))
                .orElse(fertilizer);
        long light =
            waterLightMap.stream()
                .filter(range -> range.inRange(water))
                .findFirst()
                .map(mappingRange -> mappingRange.process(water))
                .orElse(water);
        long temperature =
            lightTemperatureMap.stream()
                .filter(range -> range.inRange(light))
                .findFirst()
                .map(mappingRange -> mappingRange.process(light))
                .orElse(light);
        long humidity =
            temperatureHumidityMap.stream()
                .filter(range -> range.inRange(temperature))
                .findFirst()
                .map(mappingRange -> mappingRange.process(temperature))
                .orElse(temperature);
        long location =
            humidityLocationMap.stream()
                .filter(range -> range.inRange(humidity))
                .findFirst()
                .map(mappingRange -> mappingRange.process(humidity))
                .orElse(humidity);
        if (location < minLocation) {
          minLocation = location;
        }
      }
    }
    return minLocation;
  }

  private static class MappingRange {
    private final long start;
    private final long length;
    private final long change;

    public MappingRange(long destination, long source, long size) {
      start = source;
      length = size;
      change = destination - source;
    }

    public boolean inRange(long value) {
      return value >= start && value <= (start + length);
    }

    public long process(long value) {
      return value + change;
    }
  }
}
