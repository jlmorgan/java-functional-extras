package com.github.jlmorgan.data;

import static org.junit.jupiter.api.Assertions.*;

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
      final Maybe<Integer> testMaybe = Maybe.just(0);

      assertThrows(NullPointerException.class, () -> Maybes.filter(null, testMaybe));
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
}
