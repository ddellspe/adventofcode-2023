package net.ddellspe.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PointRangeTest {
  @Test
  public void testEqualsAndHash() {
    PointRange orig = new PointRange(new Point(0, 0), new Point(0, 1));
    PointRange same = new PointRange(new Point(0, 0), new Point(0, 1));
    assertEquals(orig, same);
    assertEquals(orig.hashCode(), same.hashCode());
    assertEquals(orig, orig);
    //noinspection SimplifiableAssertion, ConstantConditions
    assertFalse(orig.equals(null));
    assertFalse(orig.equals(""));
    assertNotEquals(orig, new PointRange(new Point(0, 0), new Point(0, 2)));
    assertNotEquals(orig, new PointRange(new Point(0, 1), new Point(0, 1)));
    assertEquals(
        "PointRange{startPoint=Point [x=0, y=0], endPoint=Point [x=0, y=1]}", orig.toString());
  }

  @Test
  public void testGetters() {
    PointRange orig = new PointRange(new Point(0, 0), new Point(0, 1));
    assertEquals(orig.getStartPoint(), new Point(0, 0));
    assertEquals(orig.getEndPoint(), new Point(0, 1));
  }

  @Test
  public void testInHorizontalRange() {
    PointRange orig = new PointRange(new Point(4, 2), new Point(6, 2));
    assertTrue(orig.inHorizontalRange(new Point(4, 2)));
    assertTrue(orig.inHorizontalRange(new Point(5, 2)));
    assertTrue(orig.inHorizontalRange(new Point(6, 2)));
    assertFalse(orig.inHorizontalRange(new Point(0, 0)));
    assertFalse(orig.inHorizontalRange(new Point(3, 2)));
    assertFalse(orig.inHorizontalRange(new Point(7, 2)));

    PointRange weirdRange = new PointRange(new Point(4, 2), new Point(6, 4));
    assertFalse(weirdRange.inHorizontalRange(new Point(7, 2)));
  }
}
