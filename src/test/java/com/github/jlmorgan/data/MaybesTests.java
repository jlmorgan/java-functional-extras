package com.github.jlmorgan.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Maybes")
class MaybesTests {
  @Nested
  class DescribeFilter {
    private final Predicate<Integer> _testPredicate = value -> value % 2 == 0;

    @Test
    void shouldThrowExceptionForNullPredicate() {
      final Predicate<Integer> testPredicate = null;
      final Maybe<Integer> testMaybe = Maybe.just(0);

      // noinspection ConstantConditions
      assertThrows(NullPointerException.class, () -> Maybes.filter(testPredicate, testMaybe));
    }

    @Test
    void shouldThrowExceptionForNullMaybe() {
      final Maybe<Integer> testMaybe = null;

      // noinspection ConstantConditions
      assertThrows(NullPointerException.class, () -> Maybes.filter(_testPredicate, testMaybe));
    }

    @Test
    void shouldReturnJustForTruePredicate() {
      final Maybe<Integer> testMaybe = Maybe.just(0);
      // noinspection UnnecessaryLocalVariable
      final Maybe<Integer> expectedResult = testMaybe;
      final Maybe<Integer> actualResult = Maybes.filter(_testPredicate).apply(testMaybe);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnNothingForFalsePredicate() {
      final Maybe<Integer> testMaybe = Maybe.just(1);
      final Maybe<Integer> expectedResult = Maybe.nothing();
      final Maybe<Integer> actualResult = Maybes.filter(_testPredicate).apply(testMaybe);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnNothingForNothing() {
      final Maybe<Integer> testMaybe = Maybe.nothing();
      // noinspection UnnecessaryLocalVariable
      final Maybe<Integer> expectedResult = testMaybe;
      final Maybe<Integer> actualResult = Maybes.filter(_testPredicate).apply(testMaybe);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeFmap {
    private final Function<List<String>, String> _testMorphism = list -> list.get(0);

    @Test
    void shouldThrowExceptionForNullMorphism() {
      final Function<List<String>, String> testMorphism = null;
      final Maybe<List<String>> testMaybe = Maybe.just(Collections.singletonList(UUID.randomUUID().toString()));

      // noinspection ConstantConditions
      assertThrows(NullPointerException.class, () -> Maybes.fmap(testMorphism, testMaybe));
    }

    @Test
    void shouldThrowExceptionForNullMaybe() {
      final Maybe<List<String>> testMaybe = null;

      // noinspection ConstantConditions
      assertThrows(NullPointerException.class, () -> Maybes.fmap(_testMorphism, testMaybe));
    }

    @Test
    void shouldMapTheUnderlyingValue() {
      final List<String> testValue = Collections.singletonList(UUID.randomUUID().toString());
      final Maybe<List<String>> testMaybe = Maybe.just(testValue);
      final Maybe<String> expectedResult = Maybe.just(testValue.get(0));
      final Maybe<String> actualResult = Maybes.fmap(_testMorphism).apply(testMaybe);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnNothingForNullReturnValue() {
      final Maybe<List<String>> testMaybe = Maybe.just(Collections.singletonList(null));
      final Maybe<String> expectedResult = Maybe.nothing();
      final Maybe<String> actualResult = Maybes.fmap(_testMorphism).apply(testMaybe);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnNothingForNothing() {
      final Maybe<List<String>> testMaybe = Maybe.nothing();
      final Maybe<String> expectedResult = Maybe.nothing();
      final Maybe<String> actualResult = Maybes.fmap(_testMorphism).apply(testMaybe);

      assertEquals(expectedResult, actualResult);
    }
  }
}
