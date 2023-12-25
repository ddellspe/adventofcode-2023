package net.ddellspe.day24;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.MathUtils;
import net.ddellspe.utils.Point;

public class Day24 {
  private Day24() {}

  public static long part1(String filename, long min, long max) {
    List<String> lines = InputUtils.stringPerLine(filename, Day24.class);
    List<HailstonePart1> hailstones = lines.stream().map(HailstonePart1::new).toList();
    List<DecimalPoint> intersections = new ArrayList<>();
    for (int i = 0; i < hailstones.size() - 1; i++) {
      for (int j = i + 1; j < hailstones.size(); j++) {
        HailstonePart1 stone1 = hailstones.get(i);
        HailstonePart1 stone2 = hailstones.get(j);
        DecimalPoint intersection = stone1.intersects(stone2);
        if (intersection != null) {
          intersections.add(intersection);
        }
      }
    }
    BigDecimal minValue = new BigDecimal(min);
    BigDecimal maxValue = new BigDecimal(max);
    return intersections.stream()
        .filter(
            dp ->
                (dp.x.compareTo(minValue) >= 0
                    && dp.x.compareTo(maxValue) <= 0
                    && dp.y.compareTo(minValue) >= 0
                    && dp.y.compareTo(maxValue) <= 0))
        .count();
  }

  static class DecimalPoint {
    BigDecimal x;
    BigDecimal y;

    public DecimalPoint(BigDecimal x, BigDecimal y) {
      this.x = x;
      this.y = y;
    }
  }

  static class HailstonePart1 {
    BigDecimal x;
    BigDecimal y;
    BigDecimal dx;
    BigDecimal dy;

    public HailstonePart1(String line) {
      String[] digits =
          Arrays.stream(line.split("@"))
              .map(String::trim)
              .map(s -> s.split(","))
              .map(x -> Arrays.stream(x).map(String::trim).toArray())
              .flatMap(Arrays::stream)
              .map(String::valueOf)
              .toArray(String[]::new);
      x = new BigDecimal(digits[0]);
      y = new BigDecimal(digits[1]);
      dx = new BigDecimal(digits[3]);
      dy = new BigDecimal(digits[4]);
    }

    public DecimalPoint intersects(HailstonePart1 h) {
      BigDecimal m = dy.divide(dx, MathContext.DECIMAL64);
      BigDecimal mh = h.dy.divide(h.dx, MathContext.DECIMAL64);
      if (m.compareTo(mh) == 0) {
        return null;
      }
      BigDecimal c = y.subtract(m.multiply(x));
      BigDecimal ch = h.y.subtract(mh.multiply(h.x));
      BigDecimal xPos = ch.subtract(c).divide(m.subtract(mh), MathContext.DECIMAL64);
      BigDecimal yPos = m.multiply(xPos).add(c);
      if (xPos.compareTo(x) < 0 && dx.compareTo(BigDecimal.ZERO) > 0
          || xPos.compareTo(x) > 0 && dx.compareTo(BigDecimal.ZERO) < 0
          || xPos.compareTo(h.x) < 0 && h.dx.compareTo(BigDecimal.ZERO) > 0
          || xPos.compareTo(h.x) > 0 && h.dx.compareTo(BigDecimal.ZERO) < 0) {
        return null;
      }
      return new DecimalPoint(xPos, yPos);
    }
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day24.class);
    List<HailstonePart2> hailstones = lines.stream().map(HailstonePart2::new).toList();
    List<Long> velocitySums =
        hailstones.stream().mapToLong(HailstonePart2::getVelocitySum).boxed().toList();
    List<Point> sumGroupings =
        hailstones.stream().map(s -> new Point(s.getPositionSum(), s.getVelocitySum())).toList();
    long result = -1;
    long velocity = 0;
    while (result == -1) {
      if (velocitySums.contains(velocity)) {
        velocity++;
        continue;
      }
      long finalVelocity = velocity;
      List<Long> ms = new ArrayList<>();
      List<Long> ss = new ArrayList<>();
      LinkedList<Point> mAndS =
          new LinkedList<>(
              sumGroupings.stream()
                  .map(
                      p ->
                          new Point(
                              p.getY() - finalVelocity,
                              Math.floorMod(p.getX(), p.getY() - finalVelocity)))
                  .toList());
      mAndS =
          new LinkedList<>(
              mAndS.stream()
                  .map(p -> p.getX() < 0 ? new Point(p.getX() * -1, p.getY() + (p.getX() * -1)) : p)
                  .toList());
      mAndS =
          new LinkedList<>(
              mAndS.stream().sorted(Comparator.comparingLong(Point::getX).reversed()).toList());
      while (!mAndS.isEmpty()) {
        Point pt = mAndS.poll();
        ms.add(pt.getX());
        ss.add(pt.getY());
        mAndS.removeIf(p -> MathUtils.gcd(p.getX(), pt.getX()) != 1);
      }
      BigInteger product =
          ms.stream().map(BigInteger::valueOf).reduce(BigInteger.ONE, BigInteger::multiply);
      List<BigInteger> inverses =
          ms.stream().map(BigInteger::valueOf).map(product::divide).toList();
      List<BigInteger> mults = new ArrayList<>();
      for (int i = 0; i < inverses.size(); i++) {
        BigInteger inverse = inverses.get(i);
        BigInteger base = BigInteger.valueOf(ms.get(i));
        mults.add(inverse.multiply(modifiedGcd(inverse, base)));
      }
      BigInteger value = BigInteger.ZERO;
      for (int i = 0; i < mults.size(); i++) {
        value = value.add(mults.get(i).multiply(BigInteger.valueOf(ss.get(i))));
      }
      value = value.mod(product);
      final long velo = velocity;
      BigInteger finalValue = value;
      if (hailstones.stream()
          .map(h -> new Point(h.getPositionSum(), h.getVelocitySum()))
          .allMatch(
              p ->
                  isIntegerResult(
                      finalValue.subtract(BigInteger.valueOf(p.getX())).longValue(),
                      p.getY() - velo))) {
        result = value.longValue();
      }
      velocity++;
    }
    return result;
  }

  static boolean isIntegerResult(long numerator, long denominator) {
    return (long) Math.ceil((double) numerator / (double) denominator) == (numerator / denominator);
  }

  static BigInteger modifiedGcd(BigInteger value1, BigInteger value2) {
    if (value2.compareTo(BigInteger.ONE) == 0) {
      return BigInteger.ONE;
    }
    BigInteger a = value1;
    BigInteger b = value2;
    BigInteger x0 = BigInteger.ZERO;
    BigInteger x1 = BigInteger.ONE;
    while (a.compareTo(BigInteger.ONE) > 0) {
      BigInteger[] divmod = a.divideAndRemainder(b);
      a = b;
      b = divmod[1];
      BigInteger temp = x0;
      x0 = x1.subtract(divmod[0].multiply(x0));
      x1 = temp;
    }
    return x1.compareTo(BigInteger.ZERO) >= 0 ? x1 : x1.add(value2);
  }

  static class HailstonePart2 {
    long x;
    long y;
    long z;
    long dx;
    long dy;
    long dz;

    public HailstonePart2(String line) {
      String[] digits =
          Arrays.stream(line.split("@"))
              .map(String::trim)
              .map(s -> s.split(","))
              .map(x -> Arrays.stream(x).map(String::trim).toArray())
              .flatMap(Arrays::stream)
              .map(String::valueOf)
              .toArray(String[]::new);
      x = Long.parseLong(digits[0]);
      y = Long.parseLong(digits[1]);
      z = Long.parseLong(digits[2]);
      dx = Long.parseLong(digits[3]);
      dy = Long.parseLong(digits[4]);
      dz = Long.parseLong(digits[5]);
    }

    public long getPositionSum() {
      return x + y + z;
    }

    public long getVelocitySum() {
      return dx + dy + dz;
    }
  }
}
