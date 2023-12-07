package net.ddellspe.day07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ddellspe.day07.Day07.CardPart1;
import net.ddellspe.day07.Day07.CardPart2;
import net.ddellspe.day07.Day07.HandPart1;
import net.ddellspe.day07.Day07.HandPart2;
import org.junit.jupiter.api.Test;

public class Day07Test {
  Day07Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(6440L, Day07.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 07 Part 1 Answer is: " + Day07.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(5905L, Day07.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 07 Part 2 Answer is: " + Day07.part2("input.txt"));
  }

  @Test
  public void coverageTests() {
    HandPart1 hand1 = new HandPart1("AAAAA");
    HandPart1 secondHand1 = new HandPart1("AAAAA");
    CardPart1 card1 = new CardPart1("A");

    HandPart2 hand2 = new HandPart2("AAAAA");
    HandPart2 secondHand2 = new HandPart2("AAAAA");
    CardPart2 card2 = new CardPart2("A");

    assertEquals(0, hand1.compareTo(secondHand1));
    assertTrue(hand1.equals(hand1));
    assertFalse(hand1.equals(null));
    assertFalse(hand1.equals(hand2));
    assertTrue(card1.equals(card1));
    assertFalse(card1.equals(null));
    assertFalse(card1.equals(card2));

    assertEquals(0, hand2.compareTo(secondHand2));
    assertTrue(hand2.equals(hand2));
    assertFalse(hand2.equals(null));
    assertFalse(hand2.equals(hand1));
    assertTrue(card2.equals(card2));
    assertFalse(card2.equals(null));
    assertFalse(card2.equals(card1));

    assertEquals(1, new HandPart1("AA234").compareTo(new HandPart1("AAAAA")));
    assertEquals(1, new HandPart1("A2345").compareTo(new HandPart1("AAAAA")));
    assertEquals(1, new HandPart2("A2345").compareTo(new HandPart2("AAAAA")));
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new HandPart1("AAAAA").getCardAt(5);
            });
    assertEquals("Card Index '5' too high only 5 cards available", exception.getMessage());
    IllegalArgumentException exceptionPart2 =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new HandPart2("AAAAA").getCardAt(5);
            });
    assertEquals("Card Index '5' too high only 5 cards available", exceptionPart2.getMessage());
  }
}
