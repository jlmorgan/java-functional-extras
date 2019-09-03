package com.github.jlmorgan.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tuple")
class TupleTests {
  private static final UUID _testFirstValue = UUID.randomUUID();
  private static final UUID _testSecondValue = UUID.randomUUID();

  @Nested
  class DescribeStaticMethods {
    @Nested
    class DescribeCurriedOf {
      @Test
      void shouldMatchUnCurriedResult() {
        final Tuple<UUID, UUID> expectedResult = Tuple.of(_testFirstValue, _testSecondValue);
        final Tuple<UUID, UUID> actualResult = Tuple.<UUID, UUID>of(_testFirstValue).apply(_testSecondValue);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeTupleMap {
      private final Supplier<Integer> _randomInt = () -> new Random().nextInt(11); // 0..10

      @Test
      void shouldThrowAnExceptionForNullFirstMorphism() {
        final Function<Integer, Integer> testFirstMorphism = null;
        final Function<Integer, Integer> testSecondMorphism = value -> value + 2;
        final int testValue = _randomInt.get();

        // noinspection ConstantConditions
        assertThrows(
          NullPointerException.class,
          () -> Tuple.<Integer, Integer, Integer>tupleMap(testFirstMorphism)
            .apply(testSecondMorphism)
            .apply(testValue)
        );
      }

      @Test
      void shouldThrowAnExceptionForNullSecondMorphism() {
        final Function<Integer, Integer> testFirstMorphism = value -> value + 1;
        final Function<Integer, Integer> testSecondMorphism = null;
        final int testValue = _randomInt.get();

        // noinspection ConstantConditions
        assertThrows(
          NullPointerException.class,
          () -> Tuple.<Integer, Integer, Integer>tupleMap(testFirstMorphism)
            .apply(testSecondMorphism)
            .apply(testValue)
        );
      }

      @Test
      void shouldReturnTupleOfMappedValues() {
        final Function<Integer, Integer> testFirstMorphism = value -> value + 1;
        final Function<Integer, Integer> testSecondMorphism = value -> value + 2;
        final int testValue = _randomInt.get();
        final Tuple<Integer, Integer> expectedResult = Tuple.of(testValue + 1, testValue + 2);
        final Tuple<Integer, Integer> actualResult = Tuple.<Integer, Integer, Integer>tupleMap(testFirstMorphism)
          .apply(testSecondMorphism)
          .apply(testValue);

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeInstanceMethods {
    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForDifferingFirstValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(UUID.randomUUID(), _testSecondValue);
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(UUID.randomUUID(), _testSecondValue);

        // noinspection SimplifiableJUnitAssertion
        assertFalse(testTuple1.equals(testTuple2));
      }

      @Test
      void shouldReturnFalseForDifferingSecondValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(_testFirstValue, UUID.randomUUID());
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(_testFirstValue, UUID.randomUUID());

        // noinspection SimplifiableJUnitAssertion
        assertFalse(testTuple1.equals(testTuple2));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Tuple<UUID, UUID> testTuple = Tuple.of(_testFirstValue, UUID.randomUUID());
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(testTuple.equals(testOther));
      }

      @Test
      void shouldReturnFalseForNull() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(_testFirstValue, UUID.randomUUID());
        final Tuple<UUID, UUID> testTuple2 = null;

        // noinspection SimplifiableJUnitAssertion, ConstantConditions
        assertFalse(testTuple1.equals(testTuple2));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        final Tuple<UUID, UUID> testTuple = Tuple.of(_testFirstValue, _testSecondValue);

        // noinspection SimplifiableJUnitAssertion, EqualsWithItself
        assertTrue(testTuple.equals(testTuple));
      }

      @Test
      void shouldReturnTrueForSameValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(_testFirstValue, _testSecondValue);
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(_testFirstValue, _testSecondValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(testTuple1.equals(testTuple2));
      }
    }

    @Nested
    class DescribeFirst {
      @Test
      void shouldReturnFirstElement() {
        final Tuple<UUID, UUID> testTuple = Tuple.of(_testFirstValue, _testSecondValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testFirstValue;
        final UUID actualResult = testTuple.first();

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingFirstValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(UUID.randomUUID(), _testSecondValue);
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(UUID.randomUUID(), _testSecondValue);

        assertNotEquals(testTuple1.hashCode(), testTuple2.hashCode());
      }

      @Test
      void shouldReturnDifferingHashCodeForDifferingSecondValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(_testFirstValue, UUID.randomUUID());
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(_testFirstValue, UUID.randomUUID());

        assertNotEquals(testTuple1.hashCode(), testTuple2.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(_testFirstValue, _testSecondValue);
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(_testFirstValue, _testSecondValue);

        assertEquals(testTuple1.hashCode(), testTuple2.hashCode());
      }
    }

    @Nested
    class DescribeSecond {
      @Test
      void shouldReturnSecondElement() {
        final Tuple<UUID, UUID> testTuple = Tuple.of(_testFirstValue, _testSecondValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testSecondValue;
        final UUID actualResult = testTuple.second();

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeSwap {
      @Test
      void shouldReturnSecondValue() {
        final Tuple<UUID, UUID> testTuple = Tuple.of(_testFirstValue, _testSecondValue);
        final Tuple<UUID, UUID> expectedResult = Tuple.of(_testSecondValue, _testFirstValue);
        final Tuple<UUID, UUID> actualResult = testTuple.swap();

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final Tuple<UUID, UUID> testTuple = Tuple.of(_testFirstValue, _testSecondValue);
        final String expectedResult = String.format("Tuple(%s, %s)", _testFirstValue, _testSecondValue);
        final String actualResult = testTuple.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }
}
