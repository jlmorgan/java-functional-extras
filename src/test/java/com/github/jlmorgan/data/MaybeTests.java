package com.github.jlmorgan.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Maybe")
class MaybeTests {
  @Nested
  class DescribeStaticMethods {
    @Nested
    class DescribeCatMaybes {
      @Test
      void shouldReturnEmptyListForNullList() {
        final List<Maybe<UUID>> testList = null;
        final List<UUID> expectedResult = Collections.emptyList();
        // noinspection ConstantConditions
        final List<UUID> actualResult = Maybe.catMaybes(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForEmptyList() {
        final List<Maybe<UUID>> testList = new ArrayList<>();
        final List<UUID> expectedResult = Collections.emptyList();
        final List<UUID> actualResult = Maybe.catMaybes(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnListOfNonNullValuesForMixedList() {
        final UUID testValue1 = UUID.randomUUID();
        final UUID testValue2 = UUID.randomUUID();
        final List<Maybe<UUID>> testList = Arrays.asList(
          Maybe.just(testValue1),
          Maybe.nothing(),
          Maybe.just(testValue2),
          Maybe.nothing()
        );
        final List<UUID> expectedResult = Arrays.asList(testValue1, testValue2);
        final List<UUID> actualResult = Maybe.catMaybes(testList);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromJust {
      @Test
      void shouldThrowExceptionForNull() {
        assertThrows(IllegalArgumentException.class, () -> Maybe.fromJust(null));
      }

      @Test
      void shouldThrowExceptionForNothing() {
        assertThrows(IllegalArgumentException.class, () -> Maybe.fromJust(Maybe.nothing()));
      }

      @Test
      void shouldReturnValueForJust() {
        final UUID testValue = UUID.randomUUID();
        final Maybe<UUID> testMaybe = Maybe.just(testValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = testValue;
        final UUID actualResult = Maybe.fromJust(testMaybe);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromMaybe {
      private final UUID _testDefaultValue = UUID.randomUUID();

      @Test
      void shouldReturnDefaultValueForNull() {
        final Maybe<UUID> testMaybe = null;
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        final UUID actualResult = Maybe.fromMaybe(_testDefaultValue).apply(testMaybe);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForNothing() {
        final Maybe<UUID> testMaybe = Maybe.nothing();
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        final UUID actualResult = Maybe.fromMaybe(_testDefaultValue).apply(testMaybe);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldValueForJust() {
        final UUID testValue = UUID.randomUUID();
        final Maybe<UUID> testMaybe = Maybe.just(testValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = testValue;
        final UUID actualResult = Maybe.fromMaybe(_testDefaultValue).apply(testMaybe);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeJust {
      @Test
      void shouldThrowExceptionForNull() {
        assertThrows(NullPointerException.class, () -> Maybe.just(null));
      }
    }

    @Nested
    class DescribeListToMaybe {
      @Test
      void shouldReturnNothingForNullList() {
        final List<UUID> testList = null;
        final Maybe<UUID> expectedResult = Maybe.nothing();
        final Maybe<UUID> actualResult = Maybe.listToMaybe(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnNothingForEmptyList() {
        final List<UUID> testList = Collections.emptyList();
        final Maybe<UUID> expectedResult = Maybe.nothing();
        final Maybe<UUID> actualResult = Maybe.listToMaybe(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnNothingForBlankList() {
        final List<UUID> testList = Collections.singletonList(null);
        final Maybe<UUID> expectedResult = Maybe.nothing();
        final Maybe<UUID> actualResult = Maybe.listToMaybe(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnJustForFirstInList() {
        final UUID testValue = UUID.randomUUID();
        final List<UUID> testList = Arrays.asList(null, testValue, UUID.randomUUID());
        final Maybe<UUID> expectedResult = Maybe.just(testValue);
        final Maybe<UUID> actualResult = Maybe.listToMaybe(testList);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeMapMaybe {
      private final Function<Integer, Maybe<Integer>> _testMorphism = value -> value != null && value % 2 == 0
        ? Maybe.just(value)
        : Maybe.nothing();

      @Test
      void shouldReturnEmptyListForNullList() {
        final List<Integer> testList = null;
        final List<Integer> expectedResult = Collections.emptyList();
        final List<Integer> actualResult = Maybe.mapMaybe(_testMorphism).apply(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForEmptyList() {
        final List<Integer> testList = Collections.emptyList();
        final List<Integer> expectedResult = Collections.emptyList();
        final List<Integer> actualResult = Maybe.mapMaybe(_testMorphism).apply(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForBlankList() {
        final List<Integer> testList = Collections.singletonList(null);
        final List<Integer> expectedResult = Collections.emptyList();
        final List<Integer> actualResult = Maybe.mapMaybe(_testMorphism).apply(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnListOfMappedValuesForList() {
        final List<Integer> testList = Arrays.asList(0, 1, 2, 3);
        final List<Integer> expectedResult = Arrays.asList(0, 2);
        final List<Integer> actualResult = Maybe.mapMaybe(_testMorphism).apply(testList);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeMaybeMap {
      private final String _testDefaultValue = UUID.randomUUID().toString();
      private final Function<UUID, String> _testMorphism = UUID::toString;

      @Test
      void shouldThrowExceptionForNullMorphism() {
        final Function<UUID, String> testMorphism = null;
        final Maybe<UUID> testMaybe = Maybe.just(UUID.randomUUID());

        assertThrows(
          NullPointerException.class,
          () -> Maybe.<UUID, String>maybeMap(_testDefaultValue)
            .apply(testMorphism)
            .apply(testMaybe)
        );
      }

      @Test
      void shouldReturnDefaultValueForNullMaybe() {
        final Maybe<UUID> testMaybe = null;
        // noinspection UnnecessaryLocalVariable
        final String expectedResult = _testDefaultValue;
        final String actualResult = Maybe.<UUID, String>maybeMap(_testDefaultValue)
          .apply(_testMorphism)
          .apply(testMaybe);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForNothing() {
        final Maybe<UUID> testMaybe = Maybe.nothing();
        // noinspection UnnecessaryLocalVariable
        final String expectedResult = _testDefaultValue;
        final String actualResult = Maybe.<UUID, String>maybeMap(_testDefaultValue)
          .apply(_testMorphism)
          .apply(testMaybe);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnValueForJust() {
        final UUID testValue = UUID.randomUUID();
        final Maybe<UUID> testMaybe = Maybe.just(testValue);
        final String expectedResult = testValue.toString();
        final String actualResult = Maybe.<UUID, String>maybeMap(_testDefaultValue)
          .apply(_testMorphism)
          .apply(testMaybe);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeMaybeToList {
      @Test
      void shouldReturnEmptyListForNullMaybe() {
        final Maybe<UUID> testMaybe = null;
        final List<UUID> expectedResult = Collections.emptyList();
        // noinspection ConstantConditions
        final List<UUID> actualResult = Maybe.maybeToList(testMaybe);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForNothing() {
        final Maybe<UUID> testMaybe = Maybe.nothing();
        final List<UUID> expectedResult = Collections.emptyList();
        final List<UUID> actualResult = Maybe.maybeToList(testMaybe);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnListOfValueForJust() {
        final UUID testValue = UUID.randomUUID();
        final Maybe<UUID> testMaybe = Maybe.just(testValue);
        final List<UUID> expectedResult = Collections.singletonList(testValue);
        final List<UUID> actualResult = Maybe.maybeToList(testMaybe);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeOf {
      @Test
      void shouldReturnNothingForNull() {
        final Object testValue = null;
        final Maybe<Object> expectedResult = Maybe.nothing();
        final Maybe<Object> actualResult = Maybe.of(testValue);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnJustOfValueForSomeValue() {
        final int testValue = 0;
        final Maybe<Integer> expectedResult = Maybe.just(testValue);
        final Maybe<Integer> actualResult = Maybe.of(testValue);

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeJust {
    private final UUID _testValue = UUID.randomUUID();
    private final Maybe<UUID> _testMaybe = Maybe.just(_testValue);

    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForNull() {
        final Maybe<UUID> testOther = null;

        // noinspection SimplifiableJUnitAssertion, ConstantConditions
        assertFalse(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnFalseForNothing() {
        final Maybe<UUID> testOther = Maybe.nothing();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingValues() {
        final Maybe<UUID> testOther = Maybe.just(UUID.randomUUID());

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        // noinspection EqualsWithItself,SimplifiableJUnitAssertion
        assertTrue(_testMaybe.equals(_testMaybe));
      }

      @Test
      void shouldReturnTrueForSameValue() {
        final Maybe<UUID> testOther = Maybe.just(_testValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testMaybe.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Maybe<UUID> testOther = Maybe.just(UUID.randomUUID());

        assertNotEquals(_testMaybe.hashCode(), testOther.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Maybe<UUID> testOther = Maybe.just(_testValue);

        assertEquals(_testMaybe.hashCode(), testOther.hashCode());
      }
    }

    @Nested
    class DescribeIsJust {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testMaybe.isJust());
      }
    }

    @Nested
    class DescribeIsNothing {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testMaybe.isNothing());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final String expectedResult = String.format("Just(%s)", _testValue.toString());
        final String actualResult = _testMaybe.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeNothing {
    private final Maybe<UUID> _testMaybe = Maybe.nothing();

    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForNull() {
        final Maybe<UUID> testOther = null;

        // noinspection SimplifiableJUnitAssertion, ConstantConditions
        assertFalse(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnTrueForNothing() {
        final Maybe<UUID> testOther = Maybe.nothing();

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testMaybe.equals(testOther));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        // noinspection SimplifiableJUnitAssertion,EqualsWithItself
        assertTrue(_testMaybe.equals(_testMaybe));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnSameHashCode() {
        final Maybe<UUID> testMaybe2 = Maybe.nothing();

        assertEquals(_testMaybe.hashCode(), testMaybe2.hashCode());
      }
    }

    @Nested
    class DescribeIsJust {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testMaybe.isJust());
      }
    }

    @Nested
    class DescribeIsNothing {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testMaybe.isNothing());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final Maybe<UUID> testMaybe = Maybe.nothing();
        final String expectedResult = "Nothing()";
        final String actualResult = testMaybe.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }
}
