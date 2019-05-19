package com.github.jlmorgan.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

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
      void shouldReturnFalseForDifferingFirstValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(UUID.randomUUID(), _testSecondValue);
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(UUID.randomUUID(), _testSecondValue);

        assertNotEquals(testTuple1.hashCode(), testTuple2.hashCode());
      }

      @Test
      void shouldReturnFalseForDifferingSecondValues() {
        final Tuple<UUID, UUID> testTuple1 = Tuple.of(_testFirstValue, UUID.randomUUID());
        final Tuple<UUID, UUID> testTuple2 = Tuple.of(_testFirstValue, UUID.randomUUID());

        assertNotEquals(testTuple1.hashCode(), testTuple2.hashCode());
      }

      @Test
      void shouldReturnTrueForSameValues() {
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
