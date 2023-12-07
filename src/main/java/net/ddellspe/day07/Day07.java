package net.ddellspe.day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.ddellspe.utils.InputUtils;

public class Day07 {
  private Day07() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day07.class);
    List<HandPart1> hands = new ArrayList<>();
    Map<HandPart1, Long> bids = new HashMap<>();
    for (String line : lines) {
      String[] lineSplit = line.split(" ");
      HandPart1 hand = new HandPart1(lineSplit[0]);
      hands.add(hand);
      bids.put(hand, Long.parseLong(lineSplit[1]));
    }
    List<HandPart1> sortedHands = hands.stream().sorted(Comparator.reverseOrder()).toList();
    return sortedHands.stream()
        .mapToLong(hand -> ((long) sortedHands.indexOf(hand) + 1L) * bids.get(hand))
        .sum();
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day07.class);
    List<HandPart2> hands = new ArrayList<>();
    Map<HandPart2, Long> bids = new HashMap<>();
    for (String line : lines) {
      String[] lineSplit = line.split(" ");
      HandPart2 hand = new HandPart2(lineSplit[0]);
      hands.add(hand);
      bids.put(hand, Long.parseLong(lineSplit[1]));
    }
    List<HandPart2> sortedHands = hands.stream().sorted(Comparator.reverseOrder()).toList();
    return sortedHands.stream()
        .mapToLong(hand -> ((long) sortedHands.indexOf(hand) + 1L) * bids.get(hand))
        .sum();
  }

  public static class CardPart1 implements Comparable<CardPart1> {
    private final String card;
    private static final String ORDER = "AKQJT98765432";

    public CardPart1(String card) {
      this.card = card;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CardPart1 card1 = (CardPart1) o;
      return Objects.equals(card, card1.card);
    }

    @Override
    public int hashCode() {
      return Objects.hash(card);
    }

    @Override
    public int compareTo(CardPart1 o) {
      return Integer.compare(ORDER.indexOf(toString()), ORDER.indexOf(o.toString()));
    }

    @Override
    public String toString() {
      return card;
    }
  }

  public static class HandPart1 implements Comparable<HandPart1> {
    private final List<CardPart1> cards;

    public HandPart1(String hand) {
      cards = Arrays.stream(hand.split("")).map(CardPart1::new).toList();
    }

    public boolean isFiveOfAKind() {
      return cards.stream().distinct().count() == 1L;
    }

    public boolean isFourOfAKind() {
      return cards.stream().distinct().count() == 2L
          && cards.stream()
                  .distinct()
                  .mapToLong(c -> cards.stream().filter(c::equals).count())
                  .max()
                  .getAsLong()
              == 4L;
    }

    public boolean isFullHouse() {
      return cards.stream().distinct().count() == 2L
          && cards.stream()
                  .distinct()
                  .mapToLong(c -> cards.stream().filter(c::equals).count())
                  .max()
                  .getAsLong()
              == 3L;
    }

    public boolean isThreeOfAKind() {
      return cards.stream().distinct().count() == 3L
          && cards.stream()
                  .distinct()
                  .mapToLong(c -> cards.stream().filter(c::equals).count())
                  .max()
                  .getAsLong()
              == 3L;
    }

    public boolean isTwoPair() {
      return cards.stream().distinct().count() == 3L
          && cards.stream()
                  .distinct()
                  .filter(c -> cards.stream().filter(c::equals).count() == 2)
                  .count()
              == 2L;
    }

    public boolean isPair() {
      return cards.stream().distinct().count() == 4L;
    }

    public boolean isHighCard() {
      return cards.stream().distinct().count() == 5L;
    }

    public CardPart1 getCardAt(int index) {
      if (index >= cards.size()) {
        throw new IllegalArgumentException(
            String.format("Card Index '%d' too high only %d cards available", index, cards.size()));
      }
      return cards.get(index);
    }

    @Override
    public int compareTo(HandPart1 o) {
      if (isFiveOfAKind() && !o.isFiveOfAKind()) {
        return -1;
      } else if (isFourOfAKind() && o.isFiveOfAKind()) {
        return 1;
      } else if (isFourOfAKind() && !o.isFourOfAKind()) {
        return -1;
      } else if (isFullHouse() && (o.isFiveOfAKind() || o.isFourOfAKind())) {
        return 1;
      } else if (isFullHouse() && !o.isFullHouse()) {
        return -1;
      } else if (isThreeOfAKind() && (o.isFiveOfAKind() || o.isFourOfAKind() || o.isFullHouse())) {
        return 1;
      } else if (isThreeOfAKind() && !o.isThreeOfAKind()) {
        return -1;
      } else if (isTwoPair()
          && (o.isFiveOfAKind() || o.isFourOfAKind() || o.isFullHouse() || o.isThreeOfAKind())) {
        return 1;
      } else if (isTwoPair() && !o.isTwoPair()) {
        return -1;
      } else if (isPair()
          && (o.isFiveOfAKind()
              || o.isFourOfAKind()
              || o.isFullHouse()
              || o.isThreeOfAKind()
              || o.isTwoPair())) {
        return 1;
      } else if (isPair() && !o.isPair()) {
        return -1;
      } else if (isHighCard()
          && (o.isFiveOfAKind()
              || o.isFourOfAKind()
              || o.isFullHouse()
              || o.isThreeOfAKind()
              || o.isTwoPair()
              || o.isPair())) {
        return 1;
      }
      for (int i = 0; i < cards.size(); i++) {
        int comp = getCardAt(i).compareTo(o.getCardAt(i));
        if (comp != 0) {
          return comp;
        }
      }
      return 0;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      HandPart1 hand = (HandPart1) o;
      return Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
      return Objects.hash(cards);
    }
  }

  public static class CardPart2 implements Comparable<CardPart2> {
    private final String card;
    private static final String ORDER = "AKQT98765432J";

    public CardPart2(String card) {
      this.card = card;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CardPart2 card1 = (CardPart2) o;
      return Objects.equals(card, card1.card);
    }

    @Override
    public int hashCode() {
      return Objects.hash(card);
    }

    @Override
    public int compareTo(CardPart2 o) {
      return Integer.compare(ORDER.indexOf(toString()), ORDER.indexOf(o.toString()));
    }

    @Override
    public String toString() {
      return card;
    }

    public boolean isJoker() {
      return card.equals("J");
    }
  }

  public static class HandPart2 implements Comparable<HandPart2> {
    private final List<CardPart2> cards;

    public HandPart2(String hand) {
      cards = Arrays.stream(hand.split("")).map(CardPart2::new).toList();
    }

    public boolean hasJoker() {
      return cards.stream().anyMatch(CardPart2::isJoker);
    }

    public long jokerCount() {
      return cards.stream().filter(CardPart2::isJoker).count();
    }

    public boolean isFiveOfAKind() {
      return cards.stream().distinct().count() == 1L
          || (hasJoker() && cards.stream().distinct().count() == 2L);
    }

    public boolean isFourOfAKind() {
      return (!hasJoker()
              && cards.stream().distinct().count() == 2L
              && cards.stream()
                      .distinct()
                      .mapToLong(c -> cards.stream().filter(c::equals).count())
                      .max()
                      .getAsLong()
                  == 4L)
          || (hasJoker()
              && cards.stream().distinct().count() == 3L
              && cards.stream()
                          .distinct()
                          .filter(c -> !c.isJoker())
                          .mapToLong(c -> cards.stream().filter(c::equals).count())
                          .max()
                          .getAsLong()
                      + jokerCount()
                  == 4L);
    }

    public boolean isFullHouse() {
      return (!hasJoker()
              && cards.stream().distinct().count() == 2L
              && cards.stream()
                      .distinct()
                      .mapToLong(c -> cards.stream().filter(c::equals).count())
                      .max()
                      .getAsLong()
                  == 3L)
          || (hasJoker()
              && cards.stream().distinct().count() == 3L
              && cards.stream()
                          .distinct()
                          .filter(c -> !c.isJoker())
                          .mapToLong(c -> cards.stream().filter(c::equals).count())
                          .max()
                          .getAsLong()
                      + jokerCount()
                  == 3L);
    }

    public boolean isThreeOfAKind() {
      return (!hasJoker()
              && cards.stream().distinct().count() == 3L
              && cards.stream()
                      .distinct()
                      .mapToLong(c -> cards.stream().filter(c::equals).count())
                      .max()
                      .getAsLong()
                  == 3L)
          || (hasJoker() && cards.stream().distinct().count() == 4L);
    }

    public boolean isTwoPair() {
      return !hasJoker()
          && cards.stream().distinct().count() == 3L
          && cards.stream()
                  .distinct()
                  .filter(c -> cards.stream().filter(c::equals).count() == 2)
                  .count()
              == 2L;
    }

    public boolean isPair() {
      return (!hasJoker() && cards.stream().distinct().count() == 4L)
          || (hasJoker() && cards.stream().distinct().count() == 5L);
    }

    public boolean isHighCard() {
      return !hasJoker() && cards.stream().distinct().count() == 5L;
    }

    public CardPart2 getCardAt(int index) {
      if (index >= cards.size()) {
        throw new IllegalArgumentException(
            String.format("Card Index '%d' too high only %d cards available", index, cards.size()));
      }
      return cards.get(index);
    }

    @Override
    public int compareTo(HandPart2 o) {
      if (isFiveOfAKind() && !o.isFiveOfAKind()) {
        return -1;
      } else if (isFourOfAKind() && o.isFiveOfAKind()) {
        return 1;
      } else if (isFourOfAKind() && !o.isFourOfAKind()) {
        return -1;
      } else if (isFullHouse() && (o.isFiveOfAKind() || o.isFourOfAKind())) {
        return 1;
      } else if (isFullHouse() && !o.isFullHouse()) {
        return -1;
      } else if (isThreeOfAKind() && (o.isFiveOfAKind() || o.isFourOfAKind() || o.isFullHouse())) {
        return 1;
      } else if (isThreeOfAKind() && !o.isThreeOfAKind()) {
        return -1;
      } else if (isTwoPair()
          && (o.isFiveOfAKind() || o.isFourOfAKind() || o.isFullHouse() || o.isThreeOfAKind())) {
        return 1;
      } else if (isTwoPair() && !o.isTwoPair()) {
        return -1;
      } else if (isPair()
          && (o.isFiveOfAKind()
              || o.isFourOfAKind()
              || o.isFullHouse()
              || o.isThreeOfAKind()
              || o.isTwoPair())) {
        return 1;
      } else if (isPair() && !o.isPair()) {
        return -1;
      } else if (isHighCard()
          && (o.isFiveOfAKind()
              || o.isFourOfAKind()
              || o.isFullHouse()
              || o.isThreeOfAKind()
              || o.isTwoPair()
              || o.isPair())) {
        return 1;
      }
      for (int i = 0; i < cards.size(); i++) {
        int comp = getCardAt(i).compareTo(o.getCardAt(i));
        if (comp != 0) {
          return comp;
        }
      }
      return 0;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      HandPart2 hand = (HandPart2) o;
      return Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
      return Objects.hash(cards);
    }
  }
}
