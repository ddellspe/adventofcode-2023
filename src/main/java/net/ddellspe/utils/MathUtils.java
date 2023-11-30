package net.ddellspe.utils;

import java.util.List;

public class MathUtils {
  private MathUtils() {}

  public static long lcm(List<Long> values) {
    return values.stream().reduce(1L, (a, b) -> a * (b / MathUtils.gcd(a, b)));
  }

  public static long gcd(List<Long> values) {
    return values.stream().reduce(0L, MathUtils::gcd);
  }

  public static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }
}
