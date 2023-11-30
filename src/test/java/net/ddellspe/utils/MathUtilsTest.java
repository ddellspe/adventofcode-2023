package net.ddellspe.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class MathUtilsTest {
  @Test
  public void lcmTests() {
    assertEquals(12L, MathUtils.lcm(Arrays.asList(3L, 4L)));
    assertEquals(12L, MathUtils.lcm(Arrays.asList(1L, 2L, 3L, 4L, 6L)));
  }

  @Test
  public void gcdTests() {
    assertEquals(5L, MathUtils.gcd(Arrays.asList(30L, 25L)));
    assertEquals(11L, MathUtils.gcd(Arrays.asList(121L, 44L, 33L)));
  }
}
