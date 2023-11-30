package net.ddellspe.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class PointTest {
  @Test
  public void testEqualsAndHash() {
    Point orig = new Point(10, 20);
    Point same = new Point(10, 20);
    assertEquals(orig, same);
    assertEquals(orig.hashCode(), same.hashCode());
    assertEquals(orig, orig);
    //noinspection SimplifiableAssertion, ConstantConditions
    assertFalse(orig.equals(null));
    assertFalse(orig.equals(""));
    assertNotEquals(orig, new Point(10, 21));
    assertNotEquals(orig, new Point(11, 20));
    assertEquals("Point [x=10, y=20]", orig.toString());
  }

  @Test
  public void testSetters() {
    Point orig = new Point(10, 20);
    Point same = new Point(30, 40);
    orig.setX(30);
    orig.setY(40);
    assertEquals(orig, same);
  }

  @Test
  public void testGetters() {
    Point orig = new Point(10, 20);
    assertEquals(10, orig.getX());
    assertEquals(20, orig.getY());
    Set<Point> neighbors = new HashSet<>();
    neighbors.add(new Point(9, 20));
    neighbors.add(new Point(11, 20));
    neighbors.add(new Point(10, 21));
    neighbors.add(new Point(10, 19));
    assertEquals(neighbors, orig.getDirectNeighbors());
    neighbors.add(new Point(9, 19));
    neighbors.add(new Point(9, 21));
    neighbors.add(new Point(11, 19));
    neighbors.add(new Point(11, 21));
    assertEquals(neighbors, orig.getAllNeighbors());
  }

  @Test
  public void testPointsBetween() {
    Point pt1 = new Point(0, 0);
    Point pt2 = new Point(0, 2);
    Set<Point> pointsBetween = new HashSet<>();
    for (int i = 0; i <= 2; i++) {
      pointsBetween.add(new Point(0, i));
    }
    assertEquals(pointsBetween, pt1.getPointsBetween(pt2));
    pt2 = new Point(2, 0);
    pointsBetween.clear();
    for (int i = 0; i <= 2; i++) {
      pointsBetween.add(new Point(i, 0));
    }
    assertEquals(pointsBetween, pt1.getPointsBetween(pt2));
    pt2 = new Point(-2, 0);
    pointsBetween.clear();
    for (int i = -2; i <= 0; i++) {
      pointsBetween.add(new Point(i, 0));
    }
    assertEquals(pointsBetween, pt1.getPointsBetween(pt2));
    pt2 = new Point(0, -2);
    pointsBetween.clear();
    for (int i = -2; i <= 0; i++) {
      pointsBetween.add(new Point(0, i));
    }
    assertEquals(pointsBetween, pt1.getPointsBetween(pt2));
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              pt1.getPointsBetween(new Point(2, 2));
            });
    assertEquals("Points must either share an X or Y coordinate", exception.getMessage());
  }

  @Test
  public void testPointsGenerationManhattanDistance() {
    assertEquals(2, new Point(0, 0).getManhattanDistance(new Point(1, 1)));

    Set<Point> expectedPoints = new HashSet<>();
    expectedPoints.add(new Point(-3, 0));
    expectedPoints.add(new Point(-2, -1));
    expectedPoints.add(new Point(-2, 0));
    expectedPoints.add(new Point(-2, 1));
    expectedPoints.add(new Point(-1, -2));
    expectedPoints.add(new Point(-1, -1));
    expectedPoints.add(new Point(-1, 0));
    expectedPoints.add(new Point(-1, 1));
    expectedPoints.add(new Point(-1, 2));
    expectedPoints.add(new Point(0, -3));
    expectedPoints.add(new Point(0, -2));
    expectedPoints.add(new Point(0, -1));
    expectedPoints.add(new Point(0, 0));
    expectedPoints.add(new Point(0, 1));
    expectedPoints.add(new Point(0, 2));
    expectedPoints.add(new Point(0, 3));
    expectedPoints.add(new Point(1, -2));
    expectedPoints.add(new Point(1, -1));
    expectedPoints.add(new Point(1, 0));
    expectedPoints.add(new Point(1, 1));
    expectedPoints.add(new Point(1, 2));
    expectedPoints.add(new Point(2, -1));
    expectedPoints.add(new Point(2, 0));
    expectedPoints.add(new Point(2, 1));
    expectedPoints.add(new Point(3, 0));
    assertEquals(expectedPoints, new Point(0, 0).generatePointsAtManhattanDistance(3));
  }

  @Test
  public void testGetNeighbors() {
    assertEquals(
        Set.of(new Point(-1, -1), new Point(0, -1), new Point(1, -1)),
        new Point(0, 0).getNorthNeighbors());
    assertEquals(
        Set.of(new Point(-1, 1), new Point(0, 1), new Point(1, 1)),
        new Point(0, 0).getSouthNeighbors());
    assertEquals(
        Set.of(new Point(1, -1), new Point(1, 0), new Point(1, 1)),
        new Point(0, 0).getEastNeighbors());
    assertEquals(
        Set.of(new Point(-1, -1), new Point(-1, 0), new Point(-1, 1)),
        new Point(0, 0).getWestNeighbors());
  }

  @Test
  public void testParseFromString() {
    assertEquals(new Point(0, 0), new Point("0,0"));
  }
}
